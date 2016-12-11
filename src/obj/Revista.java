package obj;

public class Revista extends Exemplar{
	
	private int IDRevista;
	
	private String dataEdicao;
	
	private int NroEdicao;
	
	public void setNroEdicao(int nroEdicao) {
		NroEdicao = nroEdicao;
	}

	public int getIDRevista() {
		return IDRevista;
	}

	public void setIDRevista(int iDRevista) {
		IDRevista = iDRevista;
	}

	public int getNroEdicao() {
		return NroEdicao;
	}

	public Revista(String titulo, String autor, String editora, String dataEdicao, int nroedicao, int idrevista) {
		super(titulo, autor, editora);
		this.dataEdicao = dataEdicao;
		this.NroEdicao = nroedicao;
		this.IDRevista = idrevista;
	}

	public String getDataEdicao() {
		return dataEdicao;
	}

	public void setDataEdicao(String dataEdicao) {
		this.dataEdicao = dataEdicao;
	}

}
