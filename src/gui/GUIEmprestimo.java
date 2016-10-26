package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;

import manager.AbstractValidadorDadosBiblioteca;
import obj.Cliente;
import obj.Livro;
import obj.SistemaBiblioteca;

import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class GUIEmprestimo extends AbstractValidadorDadosBiblioteca {
	
	private String titulo;
	
	private JFrame frame;
	
	private JTextField textTitulo;
	
	private JTextField textISBNCode;
	
	private JTextField textCpf;
	
	private final Action actionEmprestar = new ActionEmprestar();
	
	private final Action actionCancelar = new ActionCancelar();

	/**
	 * Create the application.
	 */
	public GUIEmprestimo(String titulo) {
		this.titulo = titulo;
		this.init();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEmprestimo = new JLabel("Empr\u00E9stimo");
		lblEmprestimo.setBounds(10, 11, 171, 23);
		lblEmprestimo.setFont(new Font("Century Gothic", Font.BOLD, 16));
		frame.getContentPane().add(lblEmprestimo);
		
		JButton btnEmprestar = new JButton("Emprestar");
		btnEmprestar.setAction(actionEmprestar);
		btnEmprestar.setBounds(217, 257, 95, 23);
		frame.getContentPane().add(btnEmprestar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setAction(actionCancelar);
		btnCancelar.setBounds(322, 257, 95, 23);
		frame.getContentPane().add(btnCancelar);
		
		JLabel lblTitulo = new JLabel("T\u00EDtulo");
		lblTitulo.setBounds(43, 80, 42, 23);
		frame.getContentPane().add(lblTitulo);
		
		JLabel lblDadosLivro = new JLabel("Dados do Livro");
		lblDadosLivro.setFont(new Font("Dialog", Font.BOLD, 13));
		lblDadosLivro.setBounds(10, 56, 128, 23);
		frame.getContentPane().add(lblDadosLivro);
		
		textTitulo = new JTextField();
		textTitulo.setForeground(Color.GRAY);
		textTitulo.setColumns(10);
		textTitulo.setBounds(95, 81, 327, 23);
		frame.getContentPane().add(textTitulo);
		
		JLabel lblISBNCode = new JLabel("C\u00F3digo ISBN");
		lblISBNCode.setBounds(11, 112, 74, 23);
		frame.getContentPane().add(lblISBNCode);
		
		textISBNCode = new JTextField();
		textISBNCode.setForeground(Color.GRAY);
		textISBNCode.setColumns(10);
		textISBNCode.setBounds(95, 112, 327, 23);
		frame.getContentPane().add(textISBNCode);
		
		JLabel lblDadosDoUsurio = new JLabel("Dados do S\u00F3cio");
		lblDadosDoUsurio.setFont(new Font("Dialog", Font.BOLD, 13));
		lblDadosDoUsurio.setBounds(10, 167, 128, 23);
		frame.getContentPane().add(lblDadosDoUsurio);
		
		textCpf = new JTextField();
		textCpf.setForeground(Color.GRAY);
		textCpf.setColumns(10);
		textCpf.setBounds(95, 193, 327, 23);
		frame.getContentPane().add(textCpf);
		
		JLabel lblNome = new JLabel("CPF");
		lblNome.setBounds(43, 193, 42, 23);
		frame.getContentPane().add(lblNome);
		
		JLabel lblsomenteNmeros = new JLabel("*Somente n\u00FAmeros");
		lblsomenteNmeros.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblsomenteNmeros.setForeground(Color.RED);
		lblsomenteNmeros.setBounds(95, 215, 110, 16);
		frame.getContentPane().add(lblsomenteNmeros);
		
		this.frame.setVisible(true);
		
	}
	private class ActionEmprestar extends AbstractAction {
		
		private Cliente c;
		private Livro l;
		
		public ActionEmprestar() {
			putValue(NAME, "Emprestar");
			putValue(SHORT_DESCRIPTION, "Empresta livro para o usuário");
		}
		public void actionPerformed(ActionEvent e) {
			
			//System.out.println("Emprestando");
			c = new Cliente(textCpf.getText());
			l = new Livro(textISBNCode.getText(), textTitulo.getText());
			
			if(!ValidarEmprestimo(l, c)){
				JOptionPane.showMessageDialog(null,"Todos os campos devem ser preenchidos !","ERRO !", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().RealizarEmprestimo(l, c)){
				JOptionPane.showMessageDialog(null,"Emprestimo realizado com sucesso !","SUCESSO !", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}else{
				JOptionPane.showMessageDialog(null,"Erro ao realizar emprestimo !\nO livro pode nao estar disponivel\nOu o numero de imprestimo para este socio é superior ao permitido","ERRO !", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	private class ActionCancelar extends AbstractAction {
		public ActionCancelar() {
			putValue(NAME, "Cancelar");
			putValue(SHORT_DESCRIPTION, "Cancela empréstimo do livro");
		}
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Cancelado");
			frame.dispose();
		}
	}
	
}
