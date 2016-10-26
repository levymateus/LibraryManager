package manager;

import obj.Cliente;
import obj.Livro;

public abstract class AbstractValidadorDadosBiblioteca implements ValidadorDadosBiblioteca {

	public boolean ValidarCliente(Cliente c) {
		
		if(c.getNome().isEmpty() || c.getCPF().isEmpty() || c.getEmail().isEmpty() || c.getTelefone().isEmpty() || c.getEndereco().isEmpty())
			return false;
		
		return true;
	}

	@Override
	public boolean ValidarLivro(Livro l) {

		if(l.getAutor().isEmpty() || l.getCodISBN().isEmpty() || l.getEditora().isEmpty() || l.getTitulo().isEmpty())
			return false;
		
		return true;
	}

	@Override
	public boolean ValidarDevolucao(Livro l) {
	
		if(l.getCodISBN().isEmpty())
			return false;
		
		return true;
	}

	@Override
	public boolean ValidarEmprestimo(Livro l, Cliente c) {
		
		if(c.getCPF().isEmpty() && l.getCodISBN().isEmpty() && l.getTitulo().isEmpty())
			return false;
		
		return true;
	}

}
