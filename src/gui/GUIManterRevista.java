package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import manager.GerenciadorBiblioteca;
import obj.Revista;
import obj.SistemaBiblioteca;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class GUIManterRevista {
	
	private String titulo;
	
	private JFrame frame;
	
	private JTextField textTitulo;
	
	private JTextField textNroEdicao;
	
	private JTextField textAutor;
	
	private JTextField textEditora;
	
	private final Action actionEditar = new ActionEditar();
	
	private final Action actionCancelar = new ActionCancelar();
	
	private final Action actionExcluir = new ActionExcluir();
	
	private JTextField textFieldDataEdicao;
	
	private int IDRevista;
	
	public GUIManterRevista(String titulo, Revista r){
		this.titulo = titulo;
		this.init();
		textAutor.setText(r.getAutor());
		textEditora.setText(r.getEditora());
		textNroEdicao.setText(Integer.toString(r.getNroEdicao()));
		textTitulo.setText(r.getTitulo());
		textAutor.setText(r.getAutor());
		textEditora.setText(r.getEditora());
		IDRevista = r.getIDRevista();
		textFieldDataEdicao.setText(r.getDataEdicao());
				
		JLabel lblDisponibilidade = new JLabel("Disponibilidade");
		
		if(r.isDisponibilidade()){
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
		frame = new JFrame(titulo);
		frame.setBounds(100, 100, 467, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCadastroDeRevista = new JLabel("Consulta: Revista");
		lblCadastroDeRevista.setBackground(Color.WHITE);
		lblCadastroDeRevista.setForeground(Color.BLACK);
		lblCadastroDeRevista.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblCadastroDeRevista.setBounds(11, 13, 171, 23);
		frame.getContentPane().add(lblCadastroDeRevista);
		
		JLabel lblTitulo = new JLabel("T\u00EDtulo");
		lblTitulo.setBounds(43, 68, 42, 23);
		frame.getContentPane().add(lblTitulo);
		
		textTitulo = new JTextField();
		textTitulo.setForeground(Color.GRAY);
		textTitulo.setColumns(10);
		textTitulo.setBounds(95, 68, 327, 23);
		frame.getContentPane().add(textTitulo);
		
		JLabel lblNroEdicao = new JLabel("N\u00BA Edi\u00E7\u00E3o");
		lblNroEdicao.setBounds(11, 106, 74, 23);
		frame.getContentPane().add(lblNroEdicao);
		
		textNroEdicao = new JTextField();
		textNroEdicao.setForeground(Color.GRAY);
		textNroEdicao.setColumns(10);
		textNroEdicao.setBounds(95, 106, 327, 23);
		frame.getContentPane().add(textNroEdicao);
		
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
		
		JLabel lblDataEdicao = new JLabel("Data Edi\u00E7\u00E3o");
		lblDataEdicao.setBounds(11, 209, 74, 16);
		frame.getContentPane().add(lblDataEdicao);
		
		textFieldDataEdicao = new JTextField();
		textFieldDataEdicao.setBounds(96, 211, 114, 20);
		frame.getContentPane().add(textFieldDataEdicao);
		textFieldDataEdicao.setColumns(10);
		
	}
	@SuppressWarnings("serial")
	private class ActionEditar extends AbstractAction {
				
		public ActionEditar() {
			putValue(NAME, "Editar");
			putValue(SHORT_DESCRIPTION, "Atualizar dados do livro");
		}
		public void actionPerformed(ActionEvent e) {

			Revista revista = new Revista(textTitulo.getText(),
					textAutor.getText(), 
					textEditora.getText(),
					textFieldDataEdicao.getText(), 
					new Integer(textNroEdicao.getText()), IDRevista);
			
			if(SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().EditarRevista(revista)){
				JOptionPane.showMessageDialog(null,"Ediçao realizada com sucesso !","SUCESSO !", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}else{
				JOptionPane.showMessageDialog(null,"Erro ao realizar edição !","ERRO !", JOptionPane.ERROR_MESSAGE);
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
	@SuppressWarnings("serial")
	private class ActionExcluir extends AbstractAction {
				
		public ActionExcluir() {
			putValue(NAME, "Excluir");
			putValue(SHORT_DESCRIPTION, "Exclui os dados do livro");
		}
		public void actionPerformed(ActionEvent e) {
			
			GerenciadorBiblioteca manager = SistemaBiblioteca.getInstance().getGerenciadorBiblioteca();
			Revista r = new Revista(null, null, null, null, 0, IDRevista);
			
			if(manager.ExcluirRevista(r)){
				JOptionPane.showMessageDialog(null,"Deleção realizada com sucesso !","SUCESSO !", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}else{
				JOptionPane.showMessageDialog(null,"Erro ao realizar deleção !","ERRO !", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
