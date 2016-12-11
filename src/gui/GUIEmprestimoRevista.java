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
import obj.Revista;
import obj.SistemaBiblioteca;

import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import javax.swing.Action;

public class GUIEmprestimoRevista extends AbstractValidadorDadosBiblioteca {
	
	private String titulo;
	
	private JFrame frame;
	
	private JTextField textTitulo;
	
	private JTextField textNroEdicao;
	
	private JTextField textCpf;
	
	private final Action actionEmprestar = new ActionEmprestar();
	
	private final Action actionCancelar = new ActionCancelar();
	
	private JPasswordField textFieldSenha;

	public GUIEmprestimoRevista(String titulo) {
		this.titulo = titulo;
		this.init();
	}
	
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
		
		JLabel lblTitulo = new JLabel("T\u00EDtulo");
		lblTitulo.setBounds(43, 80, 42, 23);
		frame.getContentPane().add(lblTitulo);
		
		JLabel lblDadosLivro = new JLabel("Dados do Revista");
		lblDadosLivro.setFont(new Font("Dialog", Font.BOLD, 13));
		lblDadosLivro.setBounds(10, 56, 128, 23);
		frame.getContentPane().add(lblDadosLivro);
		
		textTitulo = new JTextField();
		textTitulo.setForeground(Color.GRAY);
		textTitulo.setColumns(10);
		textTitulo.setBounds(95, 81, 327, 23);
		frame.getContentPane().add(textTitulo);
		
		JLabel lblNumEdicao = new JLabel("N\u00BA Edi\u00E7\u00E3o");
		lblNumEdicao.setBounds(11, 112, 74, 23);
		frame.getContentPane().add(lblNumEdicao);
		
		textNroEdicao = new JTextField();
		textNroEdicao.setForeground(Color.GRAY);
		textNroEdicao.setColumns(10);
		textNroEdicao.setBounds(95, 112, 327, 23);
		frame.getContentPane().add(textNroEdicao);
		
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
		
		public ActionEmprestar() {
			putValue(NAME, "Emprestar");
			putValue(SHORT_DESCRIPTION, "Empresta livro para o usuário");
		}
		public void actionPerformed(ActionEvent e) {
			
			String cpf = textCpf.getText();
			char[] senha = textFieldSenha.getPassword();
			String tituloRevista = textTitulo.getText();
			
			if(cpf.isEmpty() || senha.length <= 0){
				JOptionPane.showMessageDialog(null,"Preencher os dados do cliente corretamente !","Erro !",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(tituloRevista.isEmpty() || textNroEdicao.getText().isEmpty()){
				JOptionPane.showMessageDialog(null,"Preencher os dados da revista corretamente !","Erro !",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Cliente cliente = new Cliente(senha, cpf);
			Revista revista = new Revista(tituloRevista, null, null, null, new Integer(textNroEdicao.getText()), 0);
			GerenciadorBiblioteca manager = SistemaBiblioteca.getInstance().getGerenciadorBiblioteca();
			int diasAtraso = manager.RealizarEmprestimoRevista(revista, cliente);
						
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
}
