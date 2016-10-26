package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import obj.Livro;
import obj.SistemaBiblioteca;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;

public class GUIManterLivro {
	
	private String titulo;
	
	private JFrame frame;
	
	private JTextField textTitulo;
	
	private JTextField textISBNCode;
	
	private JTextField textAutor;
	
	private JTextField textEditora;
	
	private final Action actionEditar = new ActionEditar();
	
	private final Action actionCancelar = new ActionCancelar();
	
	private final Action actionExcluir = new ActionExcluir();
	
	public GUIManterLivro(String titulo, Livro l){
		this.titulo = titulo;
		this.init();
		textAutor.setText(l.getAutor());
		textEditora.setText(l.getEditora());
		textISBNCode.setText(l.getCodISBN());
		textISBNCode.setEnabled(false);
		textTitulo.setText(l.getTitulo());
		
		JLabel lblDisponibilidade = new JLabel("Disponibilidade");
		
		if(l.isDisponibilidade()){
			lblDisponibilidade.setForeground(new Color(50, 205, 50));
			lblDisponibilidade.setBounds(336, 18, 86, 16);
			lblDisponibilidade.setText("Disponivel");
		}else{
			lblDisponibilidade.setForeground(new Color(50, 10, 10));
			lblDisponibilidade.setBounds(336, 18, 86, 16);
			lblDisponibilidade.setText("Indisponivel");
		}
		frame.getContentPane().add(lblDisponibilidade);
		
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		frame = new JFrame();
		frame.setBounds(100, 100, 467, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCadastroDeLivro = new JLabel("Consulta: livro");
		lblCadastroDeLivro.setBackground(Color.WHITE);
		lblCadastroDeLivro.setForeground(Color.BLACK);
		lblCadastroDeLivro.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblCadastroDeLivro.setBounds(11, 13, 171, 23);
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
		textEditora.setBounds(95, 176, 327, 23);
		frame.getContentPane().add(textEditora);
		
		JButton Cadastrar = new JButton("Cadastrar");
		Cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		Cadastrar.setAction(actionEditar);
		Cadastrar.setBounds(222, 247, 95, 23);
		frame.getContentPane().add(Cadastrar);
		
		JButton Cancelar = new JButton("Cancelar");
		Cancelar.setAction(actionCancelar);
		Cancelar.setBounds(327, 247, 95, 23);
		frame.getContentPane().add(Cancelar);
		

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setAction(actionExcluir);
		btnExcluir.setBounds(112, 245, 98, 26);
		frame.getContentPane().add(btnExcluir);
		
	}
	private class ActionEditar extends AbstractAction {
		public ActionEditar() {
			putValue(NAME, "Editar");
			putValue(SHORT_DESCRIPTION, "Atualizar dados do livro");
		}
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Cadastrando");
			if(SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().EditarLivro(
					new Livro(textAutor.getText(), 
							textEditora.getText(),
							textISBNCode.getText(), 
							textTitulo.getText())
					)){
				JOptionPane.showMessageDialog(null,"Ediçao realizada com sucesso !","SUCESSO !", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}else{
				JOptionPane.showMessageDialog(null,"Erro ao realizar edição !","ERRO !", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private class ActionCancelar extends AbstractAction {
		public ActionCancelar() {
			putValue(NAME, "Cancelado");
			putValue(SHORT_DESCRIPTION, "Cancelar cadastro");
		}
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Cancelado");
			frame.dispose();
		}
	}
	private class ActionExcluir extends AbstractAction {
		public ActionExcluir() {
			putValue(NAME, "Excluir");
			putValue(SHORT_DESCRIPTION, "Exclui os dados do livro");
		}
		public void actionPerformed(ActionEvent e) {
			if(SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().ExcluirLivro(
					new Livro(textISBNCode.getText())
					)){
				JOptionPane.showMessageDialog(null,"Deleção realizada com sucesso !","SUCESSO !", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}else{
				JOptionPane.showMessageDialog(null,"Erro ao realizar deleção !","ERRO !", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
