package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import manager.AbstractValidadorDadosBiblioteca;
import obj.Livro;
import obj.SistemaBiblioteca;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;

public class GUICadastroLivro extends AbstractValidadorDadosBiblioteca {
	
	private String titulo;
	
	private JFrame frame;
	
	private JTextField textTitulo;
	
	private JTextField textISBNCode;
	
	private JTextField textAutor;
	
	private JTextField textEditora;
	
	private final Action actionCadastrar = new ActionCadastrar();
	
	private final Action actionCancelar = new ActionCancelar();

	/**
	 * Create the application.
	 */
	public GUICadastroLivro(String titulo) {
		this.titulo = titulo;
		this.init();
		this.frame.setVisible(true);
	}
	
	public GUICadastroLivro(String titulo, Livro l){
		this.titulo = titulo;
		this.init();
		textAutor.setText(l.getAutor());
		textEditora.setText(l.getEditora());
		textISBNCode.setText(l.getCodISBN());
		textTitulo.setText(l.getTitulo());
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCadastroDeLivro = new JLabel(this.titulo);
		lblCadastroDeLivro.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblCadastroDeLivro.setBounds(10, 11, 171, 23);
		frame.getContentPane().add(lblCadastroDeLivro);
		
		JLabel lblTitulo = new JLabel("T\u00EDtulo");
		lblTitulo.setBounds(43, 68, 42, 23);
		frame.getContentPane().add(lblTitulo);
		
		textTitulo = new JTextField();
		textTitulo.setForeground(Color.GRAY);
		textTitulo.setColumns(10);
		textTitulo.setBounds(95, 68, 327, 23);
		frame.getContentPane().add(textTitulo);
		
		JLabel lblISBNCode = new JLabel("C\u00F3digo ISBN");
		lblISBNCode.setBounds(11, 106, 74, 23);
		frame.getContentPane().add(lblISBNCode);
		
		textISBNCode = new JTextField();
		textISBNCode.setForeground(Color.GRAY);
		textISBNCode.setColumns(10);
		textISBNCode.setBounds(95, 106, 327, 23);
		frame.getContentPane().add(textISBNCode);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setBounds(43, 140, 42, 23);
		frame.getContentPane().add(lblAutor);
		
		textAutor = new JTextField();
		textAutor.setForeground(Color.GRAY);
		textAutor.setColumns(10);
		textAutor.setBounds(95, 140, 327, 23);
		frame.getContentPane().add(textAutor);
		
		JLabel lblEditora = new JLabel("Editora");
		lblEditora.setBounds(39, 174, 46, 23);
		frame.getContentPane().add(lblEditora);
		
		textEditora = new JTextField();
		textEditora.setForeground(Color.GRAY);
		textEditora.setColumns(10);
		textEditora.setBounds(95, 174, 327, 23);
		frame.getContentPane().add(textEditora);
		
		JButton Cadastrar = new JButton("Cadastrar");
		Cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		Cadastrar.setAction(actionCadastrar);
		Cadastrar.setBounds(222, 247, 95, 23);
		frame.getContentPane().add(Cadastrar);
		
		JButton Cancelar = new JButton("Cancelar");
		Cancelar.setAction(actionCancelar);
		Cancelar.setBounds(327, 247, 95, 23);
		frame.getContentPane().add(Cancelar);
		
	}
	private class ActionCadastrar extends AbstractAction {
		
		private Livro l;
		
		public ActionCadastrar() {
			putValue(NAME, "Cadastrando");
			putValue(SHORT_DESCRIPTION, "Cadastrar livro");
		}
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Cadastrando");
			
			l = new Livro(textAutor.getText(), 
					textEditora.getText(),
					textISBNCode.getText(), 
					textTitulo.getText());
			
			if(!ValidarLivro(l)){
				JOptionPane.showMessageDialog(null,"Todos os campos devem ser preenchidos ! ", "ERRO !", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().CadastrarLivro(l)){
				JOptionPane.showMessageDialog(null,"Cadastro realizado com sucesso !", "SUCESSO !", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}else{
				JOptionPane.showMessageDialog(null,"Erro ao realizar cadastro !", "ERRO !", JOptionPane.ERROR_MESSAGE);	
			}
			
		}// end actionPerformed
	}
	private class ActionCancelar extends AbstractAction {
		public ActionCancelar() {
			putValue(NAME, "Cancelado");
			putValue(SHORT_DESCRIPTION, "Cancelar cadastro");
		}
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
	}

}
