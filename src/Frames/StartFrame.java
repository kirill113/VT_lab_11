package Frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import Frames.MainFrame;



public class StartFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField   login;
	private JPasswordField password;
	private JLabel   labelLogin, labelPassword;
	private JButton connect,exit;

	public StartFrame() {
		setTitle("StartFrame");
		setSize(240, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		action();
		setVisible(true);
	}

	public void initComponents() {
		panel = new JPanel();

		
		login = new JTextField("root", 20);

		password = new JPasswordField("1234", 20);

	
		
		labelLogin = new JLabel("login");
		labelPassword = new JLabel("pass");

	
		connect = new JButton("Войти");
		exit = new JButton("Выход");
	
		panel.add(labelLogin);
		panel.add(login);
		panel.add(labelPassword);
		panel.add(password);
		panel.add(connect);
		panel.add(exit);
		add(panel);


		
	}
	public void action() {
	connect.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			new MainFrame();
			dispose();
		}
	});
	exit.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			
			dispose();
		}
	});
	}
	
	

}
