
CREATE OR REPLACE FUNCTION devolverLivro(pCodISBN exemplar.codISBN%TYPE)
RETURNS INTEGER AS $$
DECLARE
	multa INTEGER default 0;
	dataInicial DATE;
	inter INTERVAL;
	dias INTEGER;
	meses INTEGER;
	anos INTEGER;
BEGIN 

	SELECT INTO dataInicial DataEmprest FROM emprestimo WHERE emprestimo.IDEmprest = (SELECT IDEmprest FROM emprest_exemplar WHERE emprest_exemplar.codISBN = pCodISBN);

	inter := age(dataInicial);

	SELECT INTO dias EXTRACT(DAY FROM inter);
	SELECT INTO meses EXTRACT(MONTH FROM inter);
	SELECT INTO anos EXTRACT(YEAR FROM inter);

	multa := multa + dias;
	multa := multa + (meses * 30); -- desconsiderando meses com 31 dias;
	multa := multa + (anos * 365); -- desconsiderando ano bissexto;

	RETURN multa;

END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION devolverRevista
(
	pTitulo Revista.Titulo%TYPE,
	pNroEdicao Revista.NroEdicao%TYPE
)
RETURNS INTEGER AS $$
DECLARE
	multa INTEGER default 0;
	dataInicial DATE;
	inter INTERVAL;
	dias INTEGER;
	meses INTEGER;
	anos INTEGER;
BEGIN 

	SELECT INTO dataInicial DataEmprest FROM emprestimo 
	WHERE emprestimo.IDEmprest = (SELECT IDEmprest FROM emprestimo_revista 
		WHERE emprestimo_revista.IDRevista = 
		(SELECT IDRevista FROM Revista WHERE Revista.NroEdicao = pNroEdicao AND Revista.Titulo = pTitulo));

	inter := age(dataInicial);
	
	SELECT INTO dias EXTRACT(DAY FROM inter);
	SELECT INTO meses EXTRACT(MONTH FROM inter);
	SELECT INTO anos EXTRACT(YEAR FROM inter);

	multa := multa + dias;
	multa := multa + (meses * 30);
	multa := multa + (anos * 365);

	raise notice '%', inter;

	IF multa > 0 THEN
		UPDATE emprestimo SET Penalizacao = now() + inter WHERE emprestimo.IDEmprest = 
		(SELECT IDEmprest FROM emprestimo_revista WHERE emprestimo_revista.IDRevista = 
		(SELECT IDRevista FROM Revista WHERE Revista.NroEdicao = pNroEdicao AND Revista.Titulo = pTitulo));
	END IF;

	RETURN multa;

END;
$$ LANGUAGE 'plpgsql';



-- Se emprestimo realizado com sucesso retorna true. Caso contrário retorna false
CREATE OR REPLACE FUNCTION emprestarRevista
(
	pCPF Pessoa.CPF%TYPE, 
	pTitulo Revista.Titulo%TYPE,
	pNroEdicao Revista.NroEdicao%TYPE
)
RETURNS BOOLEAN AS $$
DECLARE
	numEmprestimos INTEGER default 0;
	dataPenalizacao DATE;
	DataEmprestimo DATE;
	disp BOOLEAN default false;
	codRevista INTEGER;
	codEmprest INTEGER;

BEGIN

	SELECT INTO disp EXISTS( SELECT IDRevista FROM Revista WHERE Revista.NroEdicao = pNroEdicao AND Revista.Titulo = pTitulo);

	IF disp = FALSE THEN
		RETURN false;
	END IF;

	SELECT INTO disp Disponivel FROM Revista WHERE revista.NroEdicao = pNroEdicao AND revista.Titulo = pTitulo;

	IF disp = FALSE THEN
		RETURN false;
	END IF;
	
	SELECT INTO codRevista IDRevista FROM Revista WHERE Revista.NroEdicao = pNroEdicao AND Revista.Titulo = pTitulo;


	IF codRevista = NULL THEN 
		RETURN false;
	END IF;

	-- verifica se nao existe penalizacao

	select into dataPenalizacao penalizacao FROM Emprestimo 
	natural join emprestimo_revista where emprestimo_revista.idrevista = codRevista;

	IF dataPenalizacao != NULL THEN -- SE EXISTIR PENALIZACAO RETORNA FALSE
		RETURN FALSE;
	END IF;



	INSERT INTO Emprestimo (DataEmprest) VALUES (now());
	SELECT INTO codEmprest IDEmprest FROM Emprestimo ORDER BY IDEmprest DESC LIMIT 1;
	INSERT INTO emprestimo_revista (IDEmprest, IDRevista) values (codEmprest, codRevista);
	INSERT INTO pessoa_emp (IDEmprest, CPF) values (codEmprest, pCpf);
	UPDATE Revista SET disponivel = false WHERE Revista.IDRevista = codRevista;
	UPDATE Revista SET DataEmprest = now() WHERE Revista.IDRevista = codRevista;

	RETURN true;

END;
$$ LANGUAGE 'plpgsql';

-- retorna quantos dias nao poderao ser realizados novos emprestimos
CREATE OR REPLACE FUNCTION updatePenalizacao
(
	pDias   INTEGER, 			-- numero de dias atrasado
	pIDEmprest INTEGER
)
RETURNS BOOLEAN AS $$
DECLARE
	dataPenalizacao DATE default now() AS date;
	codRevista INTEGER;
	codEmprest INTEGER;
BEGIN
	dataPenalizacao := ( cast(now() AS date) + pDias);
	UPDATE Emprestimo SET Penalizacao = dataPenalizacao WHERE idemprest = pIDEmprest;
	RETURN true;
END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION deleteRevista
(
	pTitulo Revista.Titulo%TYPE,
	pNroEdicao Revista.NroEdicao%TYPE
)
RETURNS BOOLEAN AS $$
DECLARE
	bool BOOLEAN  default false;
BEGIN
	SELECT INTO bool EXISTS( SELECT IDRevista FROM Revista WHERE Revista.Titulo = pTitulo AND Revista.NroEdicao = pNroEdicao);
	if bool = false THEN 
		RETURN false; 
	END IF;
	SELECT INTO bool Disponivel FROM Revista WHERE Revista.Titulo = pTitulo AND Revista.NroEdicao = pNroEdicao; 
	if bool = false THEN 
		RETURN false; 
	END IF;
	DELETE FROM Revista WHERE Revista.Titulo = pTitulo AND Revista.NroEdicao = pNroEdicao;
	RETURN TRUE;
END;
$$ LANGUAGE 'plpgsql';






-- Retorna a diferença em dias entre a data do emprestimo mais antigo e o dia atual
CREATE OR REPLACE FUNCTION numEmprestimos
(
	pCPF Pessoa.CPF%TYPE
)
RETURNS INTEGER AS $$
DECLARE
	inter INTERVAL default 0;
	DataEmprestimo DATE;
	atraso INTEGER default 0;
	dias INTEGER default 0;
	meses INTEGER default 0;
	anos INTEGER default 0;
BEGIN
	
-- seleciona a data do emprestimo mais antigo
SELECT INTO DataEmprestimo DataEmprest FROM pessoa_emp 
inner join emprestimo on pessoa_emp.idemprest = emprestimo.idemprest 
and pessoa_emp.cpf = pCPF
order by dataemprest asc limit 1;

raise notice '%', DataEmprestimo;

inter := age(DataEmprestimo);

SELECT INTO dias EXTRACT(DAY FROM inter);
SELECT INTO meses EXTRACT(MONTH FROM inter);
SELECT INTO anos EXTRACT(YEAR FROM inter);

atraso := atraso + dias;
atraso := atraso + (meses * 30);
atraso := atraso + (anos * 365);

raise notice 'atraso=%', atraso;

RETURN atraso;

END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION devolverRevista
(
	pNroEdicao Revista.NroEdicao%TYPE,
	pTitulo Revista.Titulo%TYPE
)
RETURNS BOOLEAN AS $$
DECLARE
	codEmprestimo INTEGER;
	codEmprest INTEGER;
	codRevista INTEGER;
	DataEmprestimo DATE;
BEGIN
	
	SELECT INTO codRevista IDRevista FROM Revista WHERE Revista.NroEdicao = pNroEdicao AND Revista.Titulo = pTitulo; 

	select into DataEmprestimo DataEmprest FROM Emprestimo 
	natural join emprestimo_revista where emprestimo_revista.idrevista = codRevista;

	IF dataPenalizacao != NULL THEN -- SE EXISTIR PENALIZACAO RETORNA FALSE
		RETURN FALSE;
	END IF;
	
	SELECT INTO codEmprest IDEmprest FROM emprestimo_revista WHERE emprestimo_revista.IDRevista = codRevista; 
	DELETE FROM pessoa_emp WHERE pessoa_emp.IDEmprest = codEmprestimo;
	DELETE IDEmprest FROM emprestimo_revista WHERE emprestimo_revista.IDRevista = codRevista;
	UPDATE Revista SET disponivel = true WHERE Revista.IDRevista = codRevista;
	UPDATE Emprestimo SET Penalizacao = NULL;

	RETURN TRUE; 

END;
$$ LANGUAGE 'plpgsql';