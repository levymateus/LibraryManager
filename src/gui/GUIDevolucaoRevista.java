package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;

import manager.AbstractValidadorDadosBiblioteca;
import manager.GerenciadorBiblioteca;
import obj.Revista;
import obj.SistemaBiblioteca;

import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class GUIDevolucaoRevista extends AbstractValidadorDadosBiblioteca {

	private String titulo;
	
	private JFrame frame;
	
	private JTextField textINroEdicao;
	
	private final Action actionDevolverRevista = new ActionDevolver();
	
	private final Action actionCancelar = new ActionCancelar();
	
	private JTextField textFieldTitulo;

	public GUIDevolucaoRevista(String titulo) {
		this.titulo = titulo;
		this.init();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 232);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDevolucao = new JLabel("Devolu\u00E7\u00E3o");
		lblDevolucao.setBounds(10, 11, 171, 23);
		lblDevolucao.setFont(new Font("Century Gothic", Font.BOLD, 16));
		frame.getContentPane().add(lblDevolucao);
		
		JButton btnDevolver = new JButton(this.titulo);
		btnDevolver.setAction(actionDevolverRevista);
		btnDevolver.setBounds(225, 158, 95, 23);
		frame.getContentPane().add(btnDevolver);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setAction(actionCancelar);
		btnCancelar.setBounds(329, 158, 95, 23);
		frame.getContentPane().add(btnCancelar);
		
		textINroEdicao = new JTextField();
		textINroEdicao.setForeground(Color.GRAY);
		textINroEdicao.setColumns(10);
		textINroEdicao.setBounds(97, 72, 327, 23);
		frame.getContentPane().add(textINroEdicao);
		
		JLabel lblNroEdicao = new JLabel("N\u00BA Edi\u00E7\u00E3o");
		lblNroEdicao.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNroEdicao.setBounds(15, 71, 74, 23);
		frame.getContentPane().add(lblNroEdicao);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(12, 106, 55, 16);
		frame.getContentPane().add(lblTitulo);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setBounds(97, 104, 325, 20);
		frame.getContentPane().add(textFieldTitulo);
		textFieldTitulo.setColumns(10);
		
	}
	@SuppressWarnings("serial")
	private class ActionDevolver extends AbstractAction {
		
		public ActionDevolver() {
			putValue(NAME, "Devolver");
			putValue(SHORT_DESCRIPTION, "Devolve o revista");
		}
		public void actionPerformed(ActionEvent e) {
			
			if(textINroEdicao.getText().isEmpty() || textFieldTitulo.getText().isEmpty()){
				JOptionPane.showMessageDialog(null,"Todos os campos devem ser preenchidos !",
						"Erro !", JOptionPane.ERROR_MESSAGE);
				return;
			}

			GerenciadorBiblioteca manager = SistemaBiblioteca.getInstance().getGerenciadorBiblioteca();
			Revista revista = new Revista(textFieldTitulo.getText(), null, null, null, new Integer(textINroEdicao.getText()), 0);
			
			if(revista.getTitulo().isEmpty() || revista.getNroEdicao() <= 0){
				JOptionPane.showMessageDialog(null,"Preencher todos os campos !",
						"Mensagem !", JOptionPane.INFORMATION_MESSAGE);
			}else{
				if( manager.RealizarDevolucaoRevista(revista)){
					JOptionPane.showMessageDialog(null,"Devolucao realizada com sucesso !",
							"Sucesso !", JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				}else{
					JOptionPane.showMessageDialog(null,"Erro ao realizar devolução !",
							"Erro !", JOptionPane.ERROR_MESSAGE);
					frame.dispose();
				}
			}
			frame.dispose();	
		}
	}
	@SuppressWarnings("serial")
	private class ActionCancelar extends AbstractAction {
		public ActionCancelar() {
			putValue(NAME, "Cancelar");
			putValue(SHORT_DESCRIPTION, "Cancela devolução");
		}
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
	}
}
