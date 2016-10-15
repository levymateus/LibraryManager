package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;

import obj.Livro;
import obj.SistemaBiblioteca;

import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class GUIDevolucao {

	private String titulo;
	
	private JFrame frame;
	
	private JTextField textISBNCode;
	
	private final Action actionEmprestar = new ActionDevolver();
	
	private final Action actionCancelar = new ActionCancelar();

	public GUIDevolucao(String titulo) {
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
	private class ActionDevolver extends AbstractAction {
		public ActionDevolver() {
			putValue(NAME, "Devolver");
			putValue(SHORT_DESCRIPTION, "Devolve o livro");
		}
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Devolvendo");
			int valorMulta = SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().RealizarDevolucao(
					new Livro(textISBNCode.getText()));
			JOptionPane.showMessageDialog(null,"Valor da multa "+valorMulta,"INFORMACAO !", JOptionPane.INFORMATION_MESSAGE);
			frame.dispose();
		}
	}
	private class ActionCancelar extends AbstractAction {
		public ActionCancelar() {
			putValue(NAME, "Cancelar");
			putValue(SHORT_DESCRIPTION, "Cancela devolução");
		}
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Cancelado");
			frame.dispose();
		}
	}
}
