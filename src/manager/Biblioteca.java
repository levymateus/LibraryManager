package manager;

import obj.Cliente;
import obj.Livro;

public interface Biblioteca {
	
	public Livro ConsultarLivro(Livro l);
	
	public boolean CadastrarLivro(Livro l);
	
	public boolean EditarLivro(Livro l);
	
	public boolean ExcluirLivro(Livro l);
	
	public boolean CadastrarCliente(Cliente c);
	
	public Cliente ConsultarCliente(Cliente c);
	
	public boolean EditarCliente(Cliente c);
	
	public boolean RealizarEmprestimo(Livro l, Cliente c);
	
	public int RealizarDevolucao(Livro l);
	
	public int EmitirMulta();

}
