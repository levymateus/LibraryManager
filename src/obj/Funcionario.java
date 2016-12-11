package obj;

public class Funcionario {

	private String login;
	
	private char[] senha;
	
	public Funcionario() {
		super();
	}

	public Funcionario(String login, char[] senha) {
		super();
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public char[] getSenha() {
		return senha;
	}

	public void setSenha(char[] senha) {
		this.senha = senha;
	}
	

}
