package Frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import Library.User;



public class StartFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private NodeList nodeList;
	private ArrayList<User> usList = new ArrayList<User>();
	private JTextField   login;
	private JPasswordField password;
	private JLabel   labelLogin, labelPassword;
	private JButton connect,exit,newuser;
	boolean Check=false;

	public StartFrame() {
		setTitle("StartFrame");
		setSize(240, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		 ReadXML();
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
		newuser = new JButton("новый");
	
		panel.add(labelLogin);
		panel.add(login);
		panel.add(labelPassword);
		panel.add(password);
		panel.add(newuser);
		panel.add(connect);
		panel.add(exit);
		add(panel);


		
	}
	public void ReadXML() {
		String filepath = "users.xml";
		File xmlFile = new File(filepath);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			nodeList = doc.getElementsByTagName("user");

			for (int i = 0; i < nodeList.getLength(); i++) {
				usList.add(getUser(nodeList.item(i)));
			}


		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	private static User getUser(Node node) {
		User us = new User();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			us.setName(getTagValue("name", element));
			us.setpasword(getTagValue("pasword", element));
			us.setUser_id(Integer.parseInt(getTagValue("id", element)));
			us.setBook_t_id(getTagValue("Book_id", element));
			us.setUser_class(Integer.parseInt(getTagValue("class", element)));
		}

		return us;
	}

	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}
	public void action() {
	connect.addActionListener(new ActionListener() {

	
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i <nodeList.getLength() ; i++) {
				if ((usList.get(i).getName().equals(login.getText()) &&(usList.get(i).getpasword().equals(password.getPassword()))));  
						{
		Check=true;
		
						}
			}
			if(Check==true) {
				new UserFrame(usList.get(0));
				dispose();
			}
		}
	});
	newuser.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			new AddFrame();
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
