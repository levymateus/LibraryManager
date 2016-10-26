package manager;

import obj.Cliente;
import obj.Livro;

public interface ValidadorDadosBiblioteca {
	
	public boolean ValidarCliente(Cliente c);
	
	public boolean ValidarLivro(Livro l);
	
	public boolean ValidarDevolucao(Livro l);

	public boolean ValidarEmprestimo(Livro l, Cliente c);

}
