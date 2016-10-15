package obj;

import manager.GerenciadorBiblioteca;

public class SistemaBiblioteca{
		
	private GerenciadorBiblioteca gerenciadorBiblioteca = null;
	
	private static SistemaBiblioteca INSTANCE = null;

	private SistemaBiblioteca() {
	}
	
	public static SistemaBiblioteca getInstance(){
		if(INSTANCE == null)
			INSTANCE =  new SistemaBiblioteca();
		return INSTANCE;
	}
	
	/**
	 * Abre o Gerenciador de dados da biblioteca
	 * 
	 */
	public boolean abrirBiblioteca(String url, String user, String pass){
		this.gerenciadorBiblioteca = new GerenciadorBiblioteca(url, user, pass);
		return gerenciadorBiblioteca.openConnection();
	}
	

	public GerenciadorBiblioteca getGerenciadorBiblioteca() {
		return this.gerenciadorBiblioteca;
	}

	
}
