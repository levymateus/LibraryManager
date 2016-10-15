package manager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import obj.Cliente;
import obj.Livro;

public class GerenciadorBiblioteca implements Biblioteca {

	private Connection conexao = null;
	
	private String url;
	
	private String user;
	
	private String password;

	public GerenciadorBiblioteca(String url, String user, String password) {
		super();
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public boolean openConnection(){
		
		try {
			conexao = DriverManager.getConnection(this.url, this.user, this.password);
			if(conexao != null){
				//System.out.println("Conxao estabeleciada");
				return true;
			}
			
			
			
		} catch (Exception e) {
			//System.err.println("Conexao nao foi realizada");
			return false;
		}
		
		return true;
		
	}
	
	public boolean isConnection(){
		return conexao != null;
	}
	
	/**
	 * Retorna um objeto Livro;
	 * Caso nao exista, retorna null
	 */
	@Override
	public Livro ConsultarLivro(Livro l) {
		
		if(!this.isConnection())
			return null;
		
		final String query = "SELECT * FROM EXEMPLAR WHERE EXEMPLAR.codISBN = ?";
		final int codisbn = 1;
		final int titulo = 2;
		final int autor = 3;
		final int disponivel = 4;
		final int editora = 5;
		
		
		try {
			
			PreparedStatement ps = this.conexao.prepareStatement(query);
			ps.setString(1, l.getCodISBN());
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next())
				return null;
			
			l.setCodISBN(rs.getString(codisbn));
			l.setTitulo(rs.getString(titulo));
			l.setAutor(rs.getString(autor));
			l.setDisponibilidade(rs.getBoolean(disponivel));
			l.setEditora(rs.getString(editora));
			
			System.out.println(l.getTitulo()+l.getAutor()+l.getCodISBN());
			System.out.println("Consulta");
			
			rs.close();
			ps.close();
			
		} catch (Exception e) {
			//CHAMA CLASSE DE POPUP E PASSA COD DE ERRO
			// GerenciadorErro(String mesangem, int id, ...);
			//System.err.println("Algum erro ocorreu");
			return l;
		}
		
		return l;
		
	}

	@Override
	public boolean CadastrarLivro(Livro l) {
		
		if(!this.isConnection())
			return false;
		
		try {
			
			final String query = "INSERT INTO EXEMPLAR (codisbn, titulo, autor, editora, disponivel) values (?, ?, ?, ?, ?)";
			
			PreparedStatement ps = this.conexao.prepareStatement(query);
			ps.setString(1, l.getCodISBN());
			ps.setString(2, l.getTitulo());
			ps.setString(3, l.getAutor());
			ps.setBoolean(5, true);
			ps.setString(4, l.getEditora());
			ps.executeUpdate();
			//System.out.println("Insercao feita");
			
		} catch (Exception e) {
			//System.err.println("Algum erro ocorreu");
			//e.printStackTrace();
			return false;
		}
		
		return true;

	}

	@Override
	public boolean EditarLivro(Livro l) {
		
		if(!this.isConnection())
			return false;
				
		final String query = "UPDATE Exemplar SET titulo = ?, autor = ?, editora = ? WHERE Exemplar.codISBN = ?";
		
		try {
			
			PreparedStatement ps = this.conexao.prepareStatement(query);
			ps.setString(1, l.getTitulo());
			ps.setString(2, l.getAutor());
			ps.setString(3, l.getEditora());
			ps.setString(4, l.getCodISBN());
			ps.executeUpdate();
			
			System.out.println("Edicao executada");
			
		} catch (Exception e) {
			//System.err.println("Algum erro ocorreu");
			//e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public boolean ExcluirLivro(Livro l) {
		
		if(!this.isConnection())
			return false;
		
		final String query = "DELETE FROM Exemplar WHERE Exemplar.codISBN = ?";
		
		try {
			PreparedStatement ps = conexao.prepareStatement(query);
			ps.setString(1, l.getCodISBN());
			ps.executeUpdate();
			//System.out.println("Livro excluido");
		} catch (Exception e) {
			//System.err.println("Ocorreu um erro");
			return false;
		}
		
		return true;

	}

	@Override
	public boolean RealizarEmprestimo(Livro l, Cliente c) {
		
		int IDEmprestimo;

		final String queryEmprestimo = "INSERT INTO Emprestimo (DataEmprest) VALUES (?)";
		
		final String empEx = "INSERT INTO Emprest_exemplar (IDEmprest, codISBN) values (?, ?)";
		
		final String empPessoa = "INSERT INTO Pessoa_emp (IDEmprest, CPF) values (?, ?)";
		
		final String selectIDEmprestimo = "SELECT IDEmprest FROM Emprestimo ORDER BY IDEmprest DESC LIMIT 1";
		
		final String upLivro = "UPDATE Exemplar SET disponivel = false WHERE Exemplar.codISBN = ?";
		
		Date date = new Date( System.currentTimeMillis() );
		
		try {
			
			// cria um novo emprestimo
			PreparedStatement ps = conexao.prepareStatement(queryEmprestimo);
			ps.setDate(1, date);
			ps.executeUpdate();
			
			// captura o id do ultimo emprestimo inserido
			ps = conexao.prepareStatement(selectIDEmprestimo);
			ResultSet rs = ps.executeQuery();
			rs.next();// aponta para o primeiro valor retornado da consulta
			IDEmprestimo = rs.getInt(1);
			System.out.println(IDEmprestimo);
			rs.close();
			
			// insere na relação de emprestimo e livro relacionado ao emprestimo
			ps = conexao.prepareStatement(empEx);
			ps.setInt(1, IDEmprestimo);
			ps.setString(2, l.getCodISBN());
			ps.executeUpdate();
			
			// insere relação de pessoa com o emprestimo
			ps = conexao.prepareStatement(empPessoa);
			ps.setInt(1, IDEmprestimo);
			ps.setString(2, c.getCPF());
			ps.executeUpdate();
			
			ps = this.conexao.prepareStatement(upLivro);
			ps.setString(1, l.getCodISBN());
			ps.executeUpdate();
			
			//System.out.println("Emprestimo realizado com sucesso");
						
		} catch (SQLException e) {
			
			//System.err.println("Erro ao emprestar livro");
			//e.printStackTrace();
			return false;
			
		}
		return true;

	}

	@Override
	public int RealizarDevolucao(Livro l) {
		
		if(!this.isConnection())
			return -1;
		
		int IDEmprest = 0;
		
		Timestamp  dataEmprestimo;
		
		int valorMulta = 0;
		
		final String selEmprestimo = "SELECT * FROM emprest_exemplar WHERE emprest_exemplar.codISBN = ?";
		
		final String delPessoa_emp = "DELETE FROM pessoa_emp WHERE pessoa_emp.IDEmprest = ?";
		
		final String delEmprestExemplar = "DELETE FROM emprest_exemplar WHERE emprest_exemplar.IDEmprest = ?";
		
		final String upExemplar = "UPDATE Exemplar SET disponivel = true WHERE Exemplar.codISBN = ?";
		
		final String delEmprestimo = "DELETE FROM Emprestimo WHERE Emprestimo.IDEmprest = ?";
		
		try {
			
			// selecionar id do emprestimo
			PreparedStatement ps = this.conexao.prepareStatement(selEmprestimo);
			ps.setString(1, l.getCodISBN());
			ResultSet rs = ps.executeQuery();
			rs.next();
			IDEmprest = rs.getInt(1);
			//dataEmprestimo = rs.getTimestamp(2);
			//System.out.println(IDEmprest);
			//System.out.println(dataEmprestimo.getTime());
			
			// exclui pessoa com relação ao emprestimo
			ps = this.conexao.prepareStatement(delPessoa_emp);
			ps.setInt(1, IDEmprest);
			ps.executeUpdate();
			
			// exclui livro com relação ao emprestimo
			ps = this.conexao.prepareStatement(delEmprestExemplar);
			ps.setInt(1, IDEmprest);
			ps.executeUpdate();
			
			//Date atual = new Date(System.currentTimeMillis());
			
			//valorMulta = CalcularMulta(atual, dataEmprestimo);
			
			// deleta emprestimo
			ps = this.conexao.prepareStatement(delEmprestimo);
			ps.setInt(1, IDEmprest);
			ps.executeUpdate();
			
			// disponibiliza exemplar
			ps = this.conexao.prepareStatement(upExemplar);
			ps.setString(1, l.getCodISBN());
			ps.executeUpdate();
			
			rs.close();
			ps.close();
			
			//System.out.println("Devolução realizada");
			
			
		} catch (Exception e) {
			//System.err.println("Um erro ocorreu ao fazer devolução");
			//e.printStackTrace();
			return -1;
		}
		
		return valorMulta;
		
	}
		
	/*
	 * @return Retorna o valor da multa
	 */
	private int CalcularMulta(Date atual, Date emprestimo){
				
		//System.out.println(atual.getTime());
		//System.out.println(emprestimo.getTime());
		
		return 0;
	}

	@Override
	public int EmitirMulta() {
		
		return 0;

	}

	@Override
	public boolean CadastrarCliente(Cliente c) {
		
		if(!this.isConnection())
			return false;
		
		final String query = "INSERT INTO Pessoa (cpf, nome, endereco, telefone, email) values (?, ?, ?, ?, ?)";
		
		try {
			
			System.out.println(c.getNome());
			PreparedStatement ps = this.conexao.prepareStatement(query);
			ps.setString(1, c.getCPF());
			ps.setString(2, c.getNome());
			ps.setString(3, c.getEndereco());
			ps.setString(4, c.getTelefone());
			ps.setString(5, c.getEmail());
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			//System.err.println(e.getMessage().toString());
			return false;
		}
		return true;
		
	}

	/**
	 * Consulta um cliente e retorna um /see Cliente. Caso nao seja encontrado nenhum cliente
	 * com os atributos especificados retorna null
	 */
	@Override
	public Cliente ConsultarCliente(Cliente c) {
		
		if(!this.isConnection())
			return null;
		
		final String query = "SELECT * FROM PESSOA WHERE PESSOA.CPF = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(query);
			ps.setString(1, c.getCPF());
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next())
				return null;
			
			c.setNome(rs.getString(1));
			c.setEndereco(rs.getString(2));
			c.setTelefone(rs.getString(3));
			c.setEmail(rs.getString(4));
			c.setCPF(rs.getString(5));
			
			rs.close();
			ps.close();
			
		} catch (Exception e) {
			
			//System.err.println("Erro ao consultar socio");
			//e.printStackTrace();
			return null;
			
		}
		
		return c;
		
	}

	@Override
	public boolean EditarCliente(Cliente c) {
		
		if(!this.isConnection())
			return false;
		
		final String query = "UPDATE Pessoa SET nome = ?, endereco = ?, telefone = ?, email = ? WHERE Pessoa.CPF = ?";
		
		try {
						
			PreparedStatement ps = conexao.prepareStatement(query);
			ps.setString(1, c.getNome());
			ps.setString(2, c.getEndereco());
			ps.setString(3, c.getTelefone());
			ps.setString(4, c.getEmail());
			ps.setString(5, c.getCPF());
			ps.executeUpdate();
			
			//System.out.println("Cliente editado com sucesso");
			
		} catch (Exception e) {
			
			//System.err.println("Erro ao editar o cliente");
			//e.printStackTrace();
			return false;
			
		}
		return true;
		
	}

}
