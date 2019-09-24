package Frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Library.User;

public class AddFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private NodeList nodeList;
	private ArrayList<User> usList = new ArrayList<User>();
	private JPanel panel;
	private JTextField login, password;
	private JLabel labelLogin, labelpassword;
	private JButton rega,exit,ret;
	private static int maxid = 0;
	int newlenth = 0;
	private boolean check = true;

	public AddFrame() {

		setTitle("RegistrationFrame");
		setSize(240, 280);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
		action();
		setVisible(true);
	}

	public void initComponents() {
		panel = new JPanel();
		login = new JTextField("", 20);
		password = new JPasswordField("", 20);
		labelLogin = new JLabel("login");
		labelpassword = new JLabel("password");
		rega = new JButton("Добавить");
		exit = new JButton("Выход");
		ret = new JButton("Назад");
		panel.add(labelLogin);
		panel.add(login);
		panel.add(labelpassword);
		panel.add(password);
		panel.add(rega);
		panel.add(ret);
		panel.add(exit);

		add(panel);

	}

	DocumentBuilder builder;

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
			if (Integer.parseInt(getTagValue("id", element)) > maxid) {
				maxid = Integer.parseInt(getTagValue("id", element));
			}
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

	public void WriteXML() throws TransformerException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (usList.get(i).getName().equals(login.getText()) ) {
					check = false;
					JOptionPane.showMessageDialog(panel, "пользователь существует", "Ошибка!",
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
			
		}
		if (check == true && (nodeList != null)) {
			usList.add(new User(login.getText(), password.getText(), maxid + 1,1));
			newlenth = nodeList.getLength() + 1;
			new StartFrame();
			dispose();
		} 
		if (check == false && (nodeList != null)) {
				newlenth = nodeList.getLength();
				new AddFrame();
				dispose();
			}
		
		if ( nodeList != null) {
			Document doc = builder.newDocument();
			Element RootElement = doc.createElement("base");
			for (int i = 0; i < newlenth; i++) {

				Element RootElement2 = doc.createElement("user");

				Element NameElementTitle = doc.createElement("name");
				NameElementTitle.appendChild(doc.createTextNode(usList.get(i).getName()));
				RootElement2.appendChild(NameElementTitle);

				Element NameElementCompile = doc.createElement("pasword");
				NameElementCompile.appendChild(doc.createTextNode(usList.get(i).getpasword()));
				RootElement2.appendChild(NameElementCompile);

				Element NameElementCompile1 = doc.createElement("class");
				NameElementCompile1.appendChild(doc.createTextNode(String.valueOf(usList.get(i).getUser_class())));
				RootElement2.appendChild(NameElementCompile1);

				Element NameElementCompile11 = doc.createElement("Book_id");
				NameElementCompile11.appendChild(doc.createTextNode(String.valueOf(usList.get(i).getBook_t_id())));
				RootElement2.appendChild(NameElementCompile11);

				Element NameElementRuns = doc.createElement("id");
				NameElementRuns.appendChild(doc.createTextNode(String.valueOf(usList.get(i).getUser_id())));
				RootElement2.appendChild(NameElementRuns);
				RootElement.appendChild(RootElement2);
			}
			doc.appendChild(RootElement);

			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("users.xml")));
			
		} 
		
		if ( nodeList == null) {
			usList.add(new User(login.getText(), password.getText(), maxid + 1, 1));
			Document doc = builder.newDocument();
			Element RootElement = doc.createElement("base");

			Element RootElement2 = doc.createElement("user");

			Element NameElementTitle = doc.createElement("name");
			NameElementTitle.appendChild(doc.createTextNode(usList.get(0).getName()));
			RootElement2.appendChild(NameElementTitle);

			Element NameElementCompile = doc.createElement("pasword");
			NameElementCompile.appendChild(doc.createTextNode(usList.get(0).getpasword()));
			RootElement2.appendChild(NameElementCompile);

			Element NameElementCompile1 = doc.createElement("class");
			NameElementCompile1.appendChild(doc.createTextNode(String.valueOf(usList.get(0).getUser_class())));
			RootElement2.appendChild(NameElementCompile1);

			Element NameElementCompile11 = doc.createElement("Book_id");
			NameElementCompile11.appendChild(doc.createTextNode(usList.get(0).getBook_t_id()));
			RootElement2.appendChild(NameElementCompile11);

			Element NameElementRuns = doc.createElement("id");
			NameElementRuns.appendChild(doc.createTextNode(String.valueOf(usList.get(0).getUser_id())));
			RootElement2.appendChild(NameElementRuns);
			RootElement.appendChild(RootElement2);

			doc.appendChild(RootElement);

			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("users.xml")));
			
		}
	}

	public void action() {
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				
				dispose();
			}
		});
		ret.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new StartFrame();
				dispose();
			}
		});

		rega.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					ReadXML();
					WriteXML();
					
				} catch (TransformerException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

	}

}
