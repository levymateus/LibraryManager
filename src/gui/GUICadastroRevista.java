package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import manager.AbstractValidadorDadosBiblioteca;
import obj.Revista;
import obj.SistemaBiblioteca;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;

public class GUICadastroRevista extends AbstractValidadorDadosBiblioteca {
	
	private String titulo;
	
	private JFrame frame;
	
	private JTextField textTitulo;
	
	private JTextField textDataEdicao;
	
	private JTextField textAutor;
	
	private JTextField textEditora;
	
	private final Action actionCadastrar = new ActionCadastrar();
	
	private final Action actionCancelar = new ActionCancelar();
	
	private JTextField textFieldNumEdicao;

	public GUICadastroRevista(String titulo) {
		this.titulo = titulo;
		this.init();
		this.frame.setVisible(true);
	}
	
//	public GUICadastroRevista(String titulo, Livro l){
//		this.titulo = titulo;
//		this.init();
//		textAutor.setText(l.getAutor());
//		textEditora.setText(l.getEditora());
//		textDataEdicao.setText(l.getCodISBN());
//		textTitulo.setText(l.getTitulo());
//		this.frame.setVisible(true);
//	}

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
		lblTitulo.setBounds(10, 68, 75, 23);
		frame.getContentPane().add(lblTitulo);
		
		textTitulo = new JTextField();
		textTitulo.setForeground(Color.GRAY);
		textTitulo.setColumns(10);
		textTitulo.setBounds(95, 68, 327, 23);
		frame.getContentPane().add(textTitulo);
		
		JLabel lblDataEdicao = new JLabel("Data edi\u00E7\u00E3o");
		lblDataEdicao.setBounds(11, 106, 74, 23);
		frame.getContentPane().add(lblDataEdicao);
		
		textDataEdicao = new JTextField();
		textDataEdicao.setForeground(Color.GRAY);
		textDataEdicao.setColumns(10);
		textDataEdicao.setBounds(95, 106, 327, 23);
		frame.getContentPane().add(textDataEdicao);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setBounds(10, 178, 75, 23);
		frame.getContentPane().add(lblAutor);
		
		textAutor = new JTextField();
		textAutor.setForeground(Color.GRAY);
		textAutor.setColumns(10);
		textAutor.setBounds(95, 178, 327, 23);
		frame.getContentPane().add(textAutor);
		
		JLabel lblEditora = new JLabel("Editora");
		lblEditora.setBounds(10, 212, 75, 23);
		frame.getContentPane().add(lblEditora);
		
		textEditora = new JTextField();
		textEditora.setForeground(Color.GRAY);
		textEditora.setColumns(10);
		textEditora.setBounds(95, 212, 327, 23);
		frame.getContentPane().add(textEditora);
		
		JButton Cadastrar = new JButton("Cadastrar");
		Cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		Cadastrar.setAction(actionCadastrar);
		Cadastrar.setBounds(192, 247, 125, 23);
		frame.getContentPane().add(Cadastrar);
		
		JButton Cancelar = new JButton("Cancelar");
		Cancelar.setAction(actionCancelar);
		Cancelar.setBounds(327, 247, 95, 23);
		frame.getContentPane().add(Cancelar);
		
		textFieldNumEdicao = new JTextField();
		textFieldNumEdicao.setForeground(Color.GRAY);
		textFieldNumEdicao.setColumns(10);
		textFieldNumEdicao.setBounds(95, 143, 327, 23);
		frame.getContentPane().add(textFieldNumEdicao);
		
		JLabel lblNEdio = new JLabel("N\u00BA Edi\u00E7\u00E3o");
		lblNEdio.setBounds(10, 143, 75, 23);
		frame.getContentPane().add(lblNEdio);
		
	}
	@SuppressWarnings("serial")
	private class ActionCadastrar extends AbstractAction {
		
		private Revista revista;
		
		public ActionCadastrar() {
			putValue(NAME, "Cadastrando");
			putValue(SHORT_DESCRIPTION, "Cadastrar livro");
		}
		
		public void actionPerformed(ActionEvent e) {
			
			if(textDataEdicao.getText().length() < 10){
				JOptionPane.showMessageDialog(null,"Campos: Data de edição preenchido icorretamente ! ", "ERRO !", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int nroEdicao = new Integer(textFieldNumEdicao.getText());
			revista = new Revista(textTitulo.getText(),
					textAutor.getText(), textEditora.getText(),
					textDataEdicao.getText(), nroEdicao, 0);
			
			if(!ValidarRevista(revista)){
				JOptionPane.showMessageDialog(null,"Campos: Data de edição são obrigatórios ! ", "ERRO !", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().CadastrarRevista(revista)){
				JOptionPane.showMessageDialog(null,"Cadastro realizado com sucesso !", "SUCESSO !", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}else{
				JOptionPane.showMessageDialog(null,"Erro ao realizar cadastro !", "ERRO !", JOptionPane.ERROR_MESSAGE);	
			}	
		}
	}
	@SuppressWarnings("serial")
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
