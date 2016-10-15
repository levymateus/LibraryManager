package obj;

public class Cliente {
	
	private String endereco;
	
	private String email;
	
	private String telefone;
	
	private String nome;
	
	private String CPF;

	public Cliente(String endereco, String email, String telefone, String nome, String cpf) {
		super();
		this.endereco = endereco;
		this.email = email;
		this.telefone = telefone;
		this.nome = nome;
		this.CPF = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}
	
	public Cliente(String cpf) {
		CPF = cpf;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getNome() {
		return nome;
	}

	public String getCPF() {
		return CPF;
	}
}
