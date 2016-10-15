package obj;

public class Livro {
	
	private String autor;
	
	private String editora;
	
	private String codISBN;
	
	private String titulo;

	public Livro(String codISBN) {
		this.codISBN = codISBN;
	}

	public Livro(String codISBN, String titulo) {
		super();
		this.codISBN = codISBN;
		this.titulo = titulo;
	}
	
	private boolean disponibilidade = true;

	public Livro(String autor, String editora, String codISBN, String titulo) {
		super();
		this.autor = autor;
		this.editora = editora;
		this.codISBN = codISBN;
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getCodISBN() {
		return codISBN;
	}

	public void setCodISBN(String codISBN) {
		this.codISBN = codISBN;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(boolean disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

}
