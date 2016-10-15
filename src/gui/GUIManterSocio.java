package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import obj.SistemaBiblioteca;
import obj.Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class GUIManterSocio {

	private String titulo;
	
	private JFrame frame;
	
	private JTextField txtNome;
	
	private JTextField txtEmail;
	
	private JTextField txtTelefone;
	
	private JTextField txtLogradoudo;
	
	private final Action actionCadastrar = new ActionCadastrar();
	
	private final Action actionCancelar = new ActionCancelar();
	
	private  JTextField txtCpf;
	
	private String btnActionName;
	
	/*
	 * Se passando a constante DADOS para f, inicia uma interface de edição de dados
	 * para cliente
	 */
	public GUIManterSocio(String titulo, Cliente c) {
		this.titulo = titulo;
		this.init();
		this.txtCpf.setText(c.getCPF());
		this.txtCpf.setEditable(false);
		this.txtEmail.setText(c.getEmail());
		this.txtNome.setText(c.getNome());
		this.txtTelefone.setText(c.getTelefone());
		this.txtLogradoudo.setText(c.getEndereco());	
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		frame = new JFrame();
		frame.setTitle(this.titulo);
		frame.setBounds(100, 100, 450, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnCadastrar = new JButton("Editar");
		btnCadastrar.setAction(actionCadastrar);
		btnCadastrar.setBounds(220, 397, 95, 23);
		frame.getContentPane().add(btnCadastrar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setAction(actionCancelar);
		btnCancelar.setBounds(325, 397, 95, 23);
		frame.getContentPane().add(btnCancelar);
		
		txtNome = new JTextField();
		txtNome.setForeground(Color.GRAY);
		txtNome.setBounds(95, 68, 325, 23);
		frame.getContentPane().add(txtNome);
		txtNome.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setForeground(Color.GRAY);
		txtEmail.setBounds(95, 148, 325, 23);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		txtTelefone = new JTextField();
		txtTelefone.setForeground(Color.GRAY);
		txtTelefone.setBounds(95, 182, 179, 23);
		frame.getContentPane().add(txtTelefone);
		txtTelefone.setColumns(10);
		
		txtLogradoudo = new JTextField();
		txtLogradoudo.setForeground(Color.GRAY);
		txtLogradoudo.setBounds(95, 216, 243, 23);
		frame.getContentPane().add(txtLogradoudo);
		txtLogradoudo.setColumns(10);
		
		JLabel lblCadastroDeCliente = new JLabel("Pesquisa: Socio");
		lblCadastroDeCliente.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblCadastroDeCliente.setBounds(10, 11, 171, 23);
		frame.getContentPane().add(lblCadastroDeCliente);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(43, 68, 42, 23);
		frame.getContentPane().add(lblNome);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(47, 148, 42, 23);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(28, 182, 57, 23);
		frame.getContentPane().add(lblTelefone);
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o");
		lblEndereco.setBounds(28, 216, 61, 23);
		frame.getContentPane().add(lblEndereco);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setBounds(53, 105, 34, 16);
		frame.getContentPane().add(lblCpf);
		
		txtCpf = new JTextField();
		txtCpf.setForeground(Color.GRAY);
		txtCpf.setToolTipText("");
		txtCpf.setBounds(95, 103, 179, 20);
		frame.getContentPane().add(txtCpf);
		
	}
	
	private class ActionCadastrar extends AbstractAction {
			
		private Cliente cliente;

		public ActionCadastrar() {
			putValue(NAME, "Editar");
			putValue(SHORT_DESCRIPTION, "Cadastrar usuário");
		}
		
		public void actionPerformed(ActionEvent e) {
						
			cliente = new Cliente(txtLogradoudo.getText(),
					txtEmail.getText(),
					txtTelefone.getText(),
					txtNome.getText(),
					txtCpf.getText());
			
			System.out.println("Cadastrando "+cliente.getNome());
			
			if(SistemaBiblioteca.getInstance().getGerenciadorBiblioteca().EditarCliente(cliente)){
				JOptionPane.showMessageDialog(null,"Ediçao realizada com sucesso !","SUCESSO !", JOptionPane.YES_OPTION);
				frame.dispose();
			}else{
				JOptionPane.showMessageDialog(null,"Erro ao realizar edição !","ERRO !", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	private class ActionCancelar extends AbstractAction {
		public ActionCancelar() {
			putValue(NAME, "Cancelar");
			putValue(SHORT_DESCRIPTION, "Cancelar cadastro");
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println("Cancelado");
			frame.dispose();
		}
	}
}
