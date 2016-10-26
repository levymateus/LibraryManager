package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
				return true;
			}
		} catch (Exception e) {
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
		final int disponivel = 5;
		final int editora = 4;
		
		
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
			
			rs.close();
			ps.close();
			
			return l;
			
		} catch (Exception e) {
			return l;
		}
				
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
			ps.setString(4, l.getEditora());
			ps.setBoolean(5, true);
			ps.executeUpdate();
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
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
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
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
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean RealizarEmprestimo(Livro l, Cliente c) {
		
		int IDEmprestimo;
		
		final String numEmprestimos = "SELECT COUNT(IDEmprest) FROM pessoa_emp WHERE pessoa_emp.CPF = ?";
		
		final String disp = "SELECT disponivel FROM exemplar WHERE exemplar.codISBN = ?";

		final String queryEmprestimo = "INSERT INTO Emprestimo (DataEmprest) VALUES (now())";
		
		final String empEx = "INSERT INTO Emprest_exemplar (IDEmprest, codISBN) values (?, ?)";
		
		final String empPessoa = "INSERT INTO Pessoa_emp (IDEmprest, CPF) values (?, ?)";
		
		final String selectIDEmprestimo = "SELECT IDEmprest FROM Emprestimo ORDER BY IDEmprest DESC LIMIT 1";
		
		final String upLivro = "UPDATE Exemplar SET disponivel = false WHERE Exemplar.codISBN = ?";
			
		try {
			
			PreparedStatement ps = this.conexao.prepareStatement(numEmprestimos);
			ps.setString(1, c.getCPF());
			ResultSet rs = ps.executeQuery();
			rs.next();			
			int num = rs.getInt(1);
			
			if(num >= 3)
				return false;
			
			ps = this.conexao.prepareStatement(disp);
			
			ps.setString(1, l.getCodISBN());
			rs = ps.executeQuery();
			rs.next();
			
			if(rs.getBoolean(1) == false)
				return false;
			
			// insere um novo emprestimo
			ps = conexao.prepareStatement(queryEmprestimo);
			ps.executeUpdate();
			
			// captura o id do ultimo emprestimo inserido
			ps = conexao.prepareStatement(selectIDEmprestimo);
			rs = ps.executeQuery();
			rs.next();
			IDEmprestimo = rs.getInt(1);
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
			
			return true;
						
		} catch (SQLException e) {
			return false;
		}
		
	}

	@Override
	public int RealizarDevolucao(Livro l) {
		
		if(!this.isConnection())
			return -1;
		
		int IDEmprest = 0;
						
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
						
			// exclui pessoa com relação ao emprestimo
			ps = this.conexao.prepareStatement(delPessoa_emp);
			ps.setInt(1, IDEmprest);
			ps.executeUpdate();
			
			// exclui livro com relação ao emprestimo
			ps = this.conexao.prepareStatement(delEmprestExemplar);
			ps.setInt(1, IDEmprest);
			ps.executeUpdate();
									
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
						
			return this.CalcularMulta(l.getCodISBN());
			
			
		} catch (Exception e) {
			return -1;
		}
				
	}
		
	/*
	 * @return Retorna o valor da multa. Em caso de erro retorna -1;
	 */
	private int CalcularMulta(String codISBN){
		
		final String query = "select devolverLivro(?)";
		
		try {
			
			PreparedStatement ps = this.conexao.prepareStatement(query);
			ps.setString(1, codISBN);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
			
		} catch (Exception e) {
			return -1;
		}
		
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
			
			PreparedStatement ps = this.conexao.prepareStatement(query);
			ps.setString(1, c.getCPF());
			ps.setString(2, c.getNome());
			ps.setString(3, c.getEndereco());
			ps.setString(4, c.getTelefone());
			ps.setString(5, c.getEmail());
			ps.executeUpdate();
			return true;
			
		} catch (Exception e) {
			return false;
		}		
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
			
			return c;
			
		} catch (Exception e) {
			return null;
			
		}
		
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
			
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
	}

}
