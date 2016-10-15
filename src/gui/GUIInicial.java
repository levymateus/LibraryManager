package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import obj.Cliente;
import obj.Livro;
import obj.SistemaBiblioteca;

import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;

public class GUIInicial extends JFrame {

	private JPanel contentPane;
	
	private JButton btnCadastrarSocio;
	
	private JTextField textFieldPesquisarSocio;
	
	private JTextField textFieldPesquisarLivro;
	
	private final Action actionCadastrarLivro = new ActionCadastrarLivro();
	
	private final Action actionEmprestarLivro = new ActionEmprestarLivro();
	
	private final Action actionPesquisarLivro = new ActionPesquisarLivro();
	
	private final Action actionCadastrarSocio = new ActionCadastrarSocio();
	
	private final Action actionPesquisarSocio = new ActionPesquisarSocio();
	
	private final Action actionDevolverLivro = new ActionDevolverLivro();

	public GUIInicial() {
		this.init();
	}
	
	/*
	 * Inicializa o frame
	 * 
	 */
	private void init(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 354);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnCadastrarSocio = new JButton("Cadastrar socio");
		btnCadastrarSocio.setBounds(10, 84, 152, 23);
		btnCadastrarSocio.setAction(actionCadastrarSocio);
		contentPane.add(btnCadastrarSocio);
		
		JButton btnCadastrarLivro = new JButton("Cadastrar Livro");
		btnCadastrarLivro.setBounds(10, 142, 152, 23);
		btnCadastrarLivro.setAction(actionCadastrarLivro);
		contentPane.add(btnCadastrarLivro);
		
		JButton btnEmprestarLivro = new JButton("Emprestar Livro");
		btnEmprestarLivro.setBounds(10, 196, 152, 23);
		btnEmprestarLivro.setAction(actionEmprestarLivro);
		contentPane.add(btnEmprestarLivro);
		
		JLabel lblPesquisarSocio = new JLabel("Pesquisar socio");
		lblPesquisarSocio.setBounds(172, 88, 117, 14);
		contentPane.add(lblPesquisarSocio);
		
		textFieldPesquisarSocio = new JTextField();
		textFieldPesquisarSocio.setBounds(271, 85, 250, 20);
		contentPane.add(textFieldPesquisarSocio);
		textFieldPesquisarSocio.setColumns(10);
		
		JLabel labelPesquisarSocio = new JLabel("Pesquisar livro");
		labelPesquisarSocio.setBounds(172, 146, 104, 14);
		contentPane.add(labelPesquisarSocio);
		
		textFieldPesquisarLivro = new JTextField();
		textFieldPesquisarLivro.setBounds(271, 143, 250, 20);
		textFieldPesquisarLivro.setColumns(10);
		contentPane.add(textFieldPesquisarLivro);
		
		JButton btnPesquisarLivro = new JButton("Pesquisar livro");
		btnPesquisarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		btnPesquisarLivro.setBounds(350, 194, 171, 26);
		btnPesquisarLivro.setAction(this.actionPesquisarLivro);
		contentPane.add(btnPesquisarLivro);
		
		JLabel lblSistemaBiblioteca = new JLabel("Sistema Biblioteca");
		lblSistemaBiblioteca.setBounds(10, 11, 171, 23);
		lblSistemaBiblioteca.setFont(new Font("Century Gothic", Font.BOLD, 16));
		contentPane.add(lblSistemaBiblioteca);
		
		JButton btnPesquisarSocio = new JButton("Pesquisar socio");
		btnPesquisarSocio.setAction(actionPesquisarSocio);
		btnPesquisarSocio.setBounds(350, 244, 171, 26);
		contentPane.add(btnPesquisarSocio);
		
		JButton btnDevolverLivro = new JButton("Devolver livro");
		btnDevolverLivro.setAction(actionDevolverLivro);
		btnDevolverLivro.setBounds(12, 244, 150, 26);
		contentPane.add(btnDevolverLivro);
		
		this.setVisible(true);
		
		this.initSistema();
		
	}
	
	private void initSistema()
	{
		if(!SistemaBiblioteca.getInstance().
		abrirBiblioteca("jdbc:postgresql://localhost:5432/sistema-biblioteca",
				"postgres", "postgres")){
			JOptionPane.showMessageDialog(null,"Conexao com a base de dados não foi realizada !","AVISO !", JOptionPane.INFORMATION_MESSAGE);
		}

	}
	
	/*
	 * Ação de cadastrar um sócio
	 * @details Abre interface para cadastrar sócio
	 * 
	 */
	private class ActionCadastrarSocio extends AbstractAction {
		
		public ActionCadastrarSocio() {
			putValue(NAME, "Cadastrar Cliente");
			putValue(SHORT_DESCRIPTION, "Cadastrar os dados do sócio");
		}
		
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Cadastrar...");
			new GUICadastroSocio("Cadastro de Socio");
		}
		
	}
	
	private class ActionCadastrarLivro extends AbstractAction {
		
		public ActionCadastrarLivro() {
			putValue(NAME, "Cadastrar livro");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Cadastrando livro...");
			new GUICadastroLivro("Cadastro de Livro");
		}
		
	}
	
	private class ActionEmprestarLivro extends AbstractAction {
		
		public ActionEmprestarLivro() {
			putValue(NAME, "Emprestar livro");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Emprestar livro");
			new GUIEmprestimo("Emprestimo");
		}
	}
	
	private class ActionPesquisarLivro extends AbstractAction {
		
		private Livro l;
		
		public ActionPesquisarLivro() {
			putValue(NAME, "Pesquisar livro");
			putValue(SHORT_DESCRIPTION, "Pesquisar sócio ou livro");
		}
		
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Pesquisar livro");
			l = new Livro(textFieldPesquisarLivro.getText());
			if(SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().ConsultarLivro(l) != null)
				new GUIManterLivro("Pesquisa livro", l);
			else
				JOptionPane.showMessageDialog(null,"Não existente na base de dados !","ERRO !", JOptionPane.ERROR_MESSAGE);
		}
	}
	private class ActionPesquisarSocio extends AbstractAction {
		
		private Cliente c;
		
		public ActionPesquisarSocio() {
			putValue(NAME, "Pesquisar socio");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		public void actionPerformed(ActionEvent e) {
			
			//System.out.print("Pesquisando socio...");
			c = new Cliente(textFieldPesquisarSocio.getText());
			
			if(SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().ConsultarCliente(c) != null)
				new GUIManterSocio("Pesquisa: Socio", c);
			else
				JOptionPane.showMessageDialog(null,"Não existente na base de dados !","ERRO !", JOptionPane.ERROR_MESSAGE);
		}
	}
	private class ActionDevolverLivro extends AbstractAction {
		public ActionDevolverLivro() {
			putValue(NAME, "Devolver livro");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Devolver livro");
			new GUIDevolucao("Devolucao");
		}
	}
}
