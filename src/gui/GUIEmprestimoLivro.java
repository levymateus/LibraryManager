package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;

import manager.AbstractValidadorDadosBiblioteca;
import manager.GerenciadorBiblioteca;
import obj.Cliente;
import obj.Livro;
import obj.SistemaBiblioteca;

import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import javax.swing.Action;

public class GUIEmprestimoLivro extends AbstractValidadorDadosBiblioteca {
	
	private String titulo;
	
	private JFrame frame;
	
	private JTextField textISBNCode;
	
	private JTextField textCpf;
	
	private final Action actionEmprestar = new ActionEmprestar();
	
	private final Action actionCancelar = new ActionCancelar();
	
	private JPasswordField textFieldSenha;

	/**
	 * Create the application.
	 */
	public GUIEmprestimoLivro(String titulo) {
		this.titulo = titulo;
		this.init();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		frame = new JFrame(titulo);
		frame.setBounds(100, 100, 450, 394);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEmprestimo = new JLabel("Empr\u00E9stimo");
		lblEmprestimo.setBounds(10, 11, 171, 23);
		lblEmprestimo.setFont(new Font("Century Gothic", Font.BOLD, 16));
		frame.getContentPane().add(lblEmprestimo);
		
		JButton btnEmprestar = new JButton("Emprestar");
		btnEmprestar.setAction(actionEmprestar);
		btnEmprestar.setBounds(222, 321, 95, 23);
		frame.getContentPane().add(btnEmprestar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setAction(actionCancelar);
		btnCancelar.setBounds(327, 321, 95, 23);
		frame.getContentPane().add(btnCancelar);
		
		JLabel lblDadosLivro = new JLabel("Dados do Livro");
		lblDadosLivro.setFont(new Font("Dialog", Font.BOLD, 13));
		lblDadosLivro.setBounds(10, 56, 128, 23);
		frame.getContentPane().add(lblDadosLivro);
		
		JLabel lblISBNCode = new JLabel("C\u00F3digo ISBN");
		lblISBNCode.setBounds(11, 80, 74, 23);
		frame.getContentPane().add(lblISBNCode);
		
		textISBNCode = new JTextField();
		textISBNCode.setForeground(Color.GRAY);
		textISBNCode.setColumns(10);
		textISBNCode.setBounds(95, 80, 327, 23);
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
		
		JLabel labelSenha = new JLabel("Senha");
		labelSenha.setBounds(43, 253, 42, 23);
		frame.getContentPane().add(labelSenha);
		
		textFieldSenha = new JPasswordField();
		textFieldSenha.setForeground(Color.GRAY);
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(95, 253, 327, 23);
		frame.getContentPane().add(textFieldSenha);
		
		this.frame.setVisible(true);
		
	}
	@SuppressWarnings("serial")
	private class ActionEmprestar extends AbstractAction {
		
		private Cliente cliente;
		private Livro livro;
		
		public ActionEmprestar() {
			putValue(NAME, "Emprestar");
			putValue(SHORT_DESCRIPTION, "Empresta livro para o usuário");
		}
		public void actionPerformed(ActionEvent e) {
			
			cliente = new Cliente(textFieldSenha.getPassword(), textCpf.getText());
			livro = new Livro(null,
					null, null, textISBNCode.getText());
			GerenciadorBiblioteca manager = SistemaBiblioteca.getInstance().getGerenciadorBiblioteca();
			
			if(!ValidarEmprestimo(livro, cliente)){
				JOptionPane.showMessageDialog(null,"Todos os campos devem ser preenchidos !","ERRO !", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int diasAtraso = manager.RealizarEmprestimoLivro(livro, cliente);
						
			if(diasAtraso == 0){
				JOptionPane.showMessageDialog(null,"Emprestimo realizado com sucesso !",
						"Sucesso !", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}else if(diasAtraso < 0){
				JOptionPane.showMessageDialog(null,"Erro ao realizar o emprestimo !",
						"Erro !", JOptionPane.ERROR_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(null,"Só será permitida um novo emprestimo daqui há: "+diasAtraso+" dias !",
						"Penalizacao detectada !", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	@SuppressWarnings("serial")
	private class ActionCancelar extends AbstractAction {
		public ActionCancelar() {
			putValue(NAME, "Cancelar");
			putValue(SHORT_DESCRIPTION, "Cancela empréstimo do livro");
		}
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
	}
	public int Emprestar(Livro l, Cliente c){
		return SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().RealizarEmprestimoLivro(l, c);
	}
}
