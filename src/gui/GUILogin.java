package gui;

import javax.swing.JFrame;

import obj.Cliente;
import obj.Funcionario;
import obj.SistemaBiblioteca;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import manager.AbstractValidadorDadosBiblioteca;
import manager.GerenciadorBiblioteca;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class GUILogin extends AbstractValidadorDadosBiblioteca  {
	
	private JFrame frame;
	
	private JTextField textFieldLogin;
	
	private JPasswordField textFieldSenha;
	
	private final Action actionLogin = new ActionLogin();
			
	private Object loginModeObj = null;
		
	public GUILogin(){}
	
	/**
	 * @brief Inicializa a interface de login.
	 * @param o - Usado para o login. É o objeto que sera usado para logar.
	 * @param c - Objecto que está chamando GUILogin.
	 */
	public void initialize(Object o, Object c){
				
		if(o instanceof Cliente)
			this.loginModeObj = ((Cliente) o);
		else
			this.loginModeObj = ((Funcionario) o);
		
		this.init();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		frame = new JFrame();
		frame.setBounds(100, 100, 266, 198);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 226, 137);
		frame.getContentPane().add(panel);
		
		JLabel lblLogin = new JLabel("Login");
		
		textFieldLogin = new JTextField();
		textFieldLogin.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		
		textFieldSenha = new JPasswordField();
		textFieldSenha.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setAction(actionLogin);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(textFieldLogin, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldSenha, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(36, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		if(this.loginModeObj instanceof Cliente){
			textFieldLogin.setEnabled(false);
			lblLogin.setEnabled(false);
		}
		
		frame.setVisible(true);
	}
	
	public void initSistema(String endereco, String database, String login, String senha){
		if(!SistemaBiblioteca.getInstance().
		abrirBiblioteca("jdbc:postgresql://"+endereco+"/"+database,
				login, senha)){
			JOptionPane.showMessageDialog(null,"Conexao com a base de dados não foi realizada !","AVISO !", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@SuppressWarnings("serial")
	private class ActionLogin extends AbstractAction {
		public ActionLogin() {
			putValue(NAME, "Login");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			
			String login = textFieldLogin.getText();
			char[] pass = textFieldSenha.getPassword();
			GerenciadorBiblioteca manager = SistemaBiblioteca.getInstance().getGerenciadorBiblioteca();

			if(loginModeObj instanceof Cliente){
				
				Cliente cliente = (Cliente) loginModeObj;
				cliente.setSenha(pass);

				if(ValidarLogin(cliente)){
					if(manager.RealizarLogin(cliente)){
						// code
					}else{
						JOptionPane.
						showMessageDialog(null,"Não existente na base de dados !","ERRO !",
								JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.
					showMessageDialog(null,"Preencha os campos !","ERRO !",
							JOptionPane.ERROR_MESSAGE);
				}
			}else{
				Funcionario func = (Funcionario) loginModeObj;
				func.setLogin(login);
				func.setSenha(pass);
				if(ValidarLogin(func)){
					if(manager.RealizarLogin(func)){
						new GUIInicial(login);
						frame.dispose();
					}else{
						JOptionPane.
						showMessageDialog(null,"Não existente na base de dados !","ERRO !",
								JOptionPane.ERROR_MESSAGE);
						frame.dispose();
					}
				}else{
					JOptionPane.
					showMessageDialog(null,"Preencha os campos !","ERRO !",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}	
	}
}
