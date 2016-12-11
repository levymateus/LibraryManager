
CREATE DATABASE sistema-biblioteca

CREATE TABLE Pessoa (
Nome VARCHAR(128),
Endereco VARCHAR(128),
Telefone VARCHAR(128),
Email VARCHAR(128),
CPF VARCHAR(128) PRIMARY KEY,
Senha VARCHAR(128)
)

CREATE TABLE Exemplar (
codISBN VARCHAR(128) PRIMARY KEY,
Titulo VARCHAR(128),
Autor VARCHAR(128),
Editora VARCHAR(128),
Disponivel BOOLEAN
)

CREATE TABLE Emprestimo (
DataEmprest DATE,
Penalizacao DATE,
IDEmprest SERIAL PRIMARY KEY
)

CREATE TABLE pessoa_emp (
IDEmprest INTEGER,
CPF VARCHAR(128),
FOREIGN KEY(CPF) REFERENCES Pessoa (CPF) ON DELETE CASCADE,
FOREIGN KEY(IDEmprest) REFERENCES Emprestimo (IDEmprest) ON DELETE CASCADE
)

CREATE TABLE emprest_exemplar (
IDEmprest INTEGER,
codISBN VARCHAR(128),
FOREIGN KEY(IDEmprest) REFERENCES Emprestimo (IDEmprest) ON DELETE CASCADE,
FOREIGN KEY(codISBN) REFERENCES Exemplar (codISBN) ON DELETE CASCADE
)

CREATE TABLE Funcionario(
IDFuncionario SERIAL PRIMARY KEY,
Login VARCHAR(128),
Senha VARCHAR(128)
)

CREATE TABLE Revista(
IDRevista SERIAL PRIMARY KEY,
Titulo VARCHAR(128),
DataEdicao VARCHAR(128),
NroEdicao INTEGER,
Disponivel BOOLEAN
)

CREATE TABLE emprestimo_revista(
IDRevista INTEGER,
IDEmprest INTEGER,
FOREIGN KEY(IDEmprest) REFERENCES Emprestimo (IDEmprest) ON DELETE CASCADE,
FOREIGN KEY(IDRevista) REFERENCES Revista (IDRevista) ON DELETE CASCADE
)

----------------------------INSERÇÕES-------------------------------------------------------------

INSERT INTO FUNCIONARIO (Login, Senha) VALUES ('tathi', '123');
INSERT INTO FUNCIONARIO (Login, Senha) VALUES ('levy', '123');