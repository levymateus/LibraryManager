package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;

import manager.AbstractValidadorDadosBiblioteca;
import obj.Livro;
import obj.SistemaBiblioteca;

import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class GUIDevolucaoLivro extends AbstractValidadorDadosBiblioteca {

	private String titulo;
	
	private JFrame frame;
	
	private JTextField textISBNCode;
	
	private final Action actionEmprestar = new ActionDevolver();
	
	private final Action actionCancelar = new ActionCancelar();

	public GUIDevolucaoLivro(String titulo) {
		this.titulo = titulo;
		this.init();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 208);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDevolucao = new JLabel("Devolu\u00E7\u00E3o");
		lblDevolucao.setBounds(10, 11, 171, 23);
		lblDevolucao.setFont(new Font("Century Gothic", Font.BOLD, 16));
		frame.getContentPane().add(lblDevolucao);
		
		JButton btnDevolver = new JButton(this.titulo);
		btnDevolver.setAction(actionEmprestar);
		btnDevolver.setBounds(224, 137, 95, 23);
		frame.getContentPane().add(btnDevolver);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setAction(actionCancelar);
		btnCancelar.setBounds(329, 137, 95, 23);
		frame.getContentPane().add(btnCancelar);
		
		textISBNCode = new JTextField();
		textISBNCode.setForeground(Color.GRAY);
		textISBNCode.setColumns(10);
		textISBNCode.setBounds(97, 72, 327, 23);
		frame.getContentPane().add(textISBNCode);
		
		JLabel lblISBNCode = new JLabel("C\u00F3digo ISBN");
		lblISBNCode.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblISBNCode.setBounds(15, 71, 74, 23);
		frame.getContentPane().add(lblISBNCode);
		
	}
	@SuppressWarnings("serial")
	private class ActionDevolver extends AbstractAction {
		
		private Livro livro;
		
		public ActionDevolver() {
			putValue(NAME, "Devolver");
			putValue(SHORT_DESCRIPTION, "Devolve o livro");
		}
		public void actionPerformed(ActionEvent e) {
			
			livro = new Livro(null, null, null, textISBNCode.getText());
			
			if(!ValidarDevolucao(livro)){
				JOptionPane.showMessageDialog(null,"Todos os campos devem ser preenchidos ! ", "ERRO !", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int valorMulta = SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().RealizarDevolucaoLivro(livro);
			
			if(valorMulta >= 0)
				JOptionPane.showMessageDialog(null,"S� ser� permitida um novo emprestimo daqui h�: "+valorMulta+" dias !","Aten��o !", JOptionPane.INFORMATION_MESSAGE);
			else 
				JOptionPane.showMessageDialog(null,"Erro ao realizar devolu��o\n Talvez esse livro nao tenha sido emprestado.","INFORMACAO !", JOptionPane.INFORMATION_MESSAGE);
			
			frame.dispose();	
		}
	}
	@SuppressWarnings("serial")
	private class ActionCancelar extends AbstractAction {
		public ActionCancelar() {
			putValue(NAME, "Cancelar");
			putValue(SHORT_DESCRIPTION, "Cancela devolu��o");
		}
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
	}
}
