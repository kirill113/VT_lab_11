package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

import Book_type.Book;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ������ ������ �������
	private DefaultTableModel tableModel;
	private JTable table1;
	private NodeList nodeList;
	private ArrayList<Book> usList = new ArrayList<Book>();
	private int sr=1;

	// ��������� ��������
	private Object[] columnsHeader = new String[] { "id", "���", "���", "���-��", "����/�����" };

	public MainFrame() {
		super("������ ������������� TableModel");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// �������� ����������� ������
		tableModel = new DefaultTableModel();
		// ����������� ��������
		tableModel.setColumnIdentifiers(columnsHeader);
		// ���������� ������ �������
		ReadXML();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Object[] array = new String[] { String.valueOf(usList.get(i).getBook_id()),
						Class_chenge((usList.get(i).getTipe())), usList.get(i).getName(),
						String.valueOf(usList.get(i).getCount()), usList.get(i).getGenre() };
				tableModel.addRow(array);
			}
		} else {
			Object[] array = new String[] { "", "", "" };
			tableModel.addRow(array);
		}
		// �������� ������� �� ��������� ������ ������
		table1 = new JTable(tableModel);
		// �������� ������ ���������� ������ �������
		JButton add = new JButton("��������");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����� ���������� ������
				int idx = table1.getSelectedRow();
				// ������� ����� ������ ����� ����������
				tableModel.insertRow(idx + 1, new String[] { "" + String.valueOf(table1.getRowCount()), "", "" });
			}
		});
		JButton  sort= new JButton("sort");
		sort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				Sort_l(sr);
				if(sr==3)
				{
					sr=1;
				}
				else {
					sr++;
				}
				
			}
		});
		JButton save = new JButton("���������");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					WriteXML();
				} catch (TransformerException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// �������� ������ �������� ������ �������
		JButton remove = new JButton("�������");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����� ���������� ������
				int idx = table1.getSelectedRow();
				// �������� ���������� ������
				tableModel.removeRow(idx);
			}
		});
		// �������� ������� �� ������ ������ ������

		// ����������� ������ ������

		// ������������ ����������
		Box contents = new Box(BoxLayout.Y_AXIS);
		contents.add(new JScrollPane(table1));

		getContentPane().add(contents);

		JPanel buttons = new JPanel();
		buttons.add(add);
		buttons.add(save);
		buttons.add(remove);
		buttons.add(sort);
		getContentPane().add(buttons, "South");
		// ����� ���� �� �����
		setSize(400, 300);
		setVisible(true);
	}

	DocumentBuilder builder;

	public void ReadXML() {
		String filepath = "Books.xml";
		File xmlFile = new File(filepath);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			nodeList = doc.getElementsByTagName("Book");

			for (int i = 0; i < nodeList.getLength(); i++) {
				usList.add(getBook(nodeList.item(i)));
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void WriteXML() throws TransformerException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		Document doc = builder.newDocument();
		Element RootElement = doc.createElement("base");
		for (int i = 0; i < table1.getRowCount(); i++) {

			Element RootElement2 = doc.createElement("Book");

			Element NameElementTitle = doc.createElement("id");
			NameElementTitle.appendChild(doc.createTextNode(String.valueOf(table1.getValueAt(i, 0))));
			RootElement2.appendChild(NameElementTitle);

			Element NameElementCompile = doc.createElement("tipe");
			NameElementCompile.appendChild(doc.createTextNode(String.valueOf(R_Class_chenge(String.valueOf(table1.getValueAt(i, 1))))));
			RootElement2.appendChild(NameElementCompile);

			Element NameElementCompile1 = doc.createElement("name");
			NameElementCompile1.appendChild(doc.createTextNode(String.valueOf(table1.getValueAt(i, 2))));
			RootElement2.appendChild(NameElementCompile1);

			Element NameElementCompile11 = doc.createElement("count");
			NameElementCompile11.appendChild(doc.createTextNode(String.valueOf(table1.getValueAt(i, 3))));
			RootElement2.appendChild(NameElementCompile11);

			Element NameElementRuns = doc.createElement("genre");
			NameElementRuns.appendChild(doc.createTextNode(String.valueOf(table1.getValueAt(i, 3))));
			RootElement2.appendChild(NameElementRuns);
			RootElement.appendChild(RootElement2);
		}
		doc.appendChild(RootElement);

		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("Books.xml")));

	}

	private static Book getBook(Node node) {
		Book us = new Book();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			us.setBook_id(Integer.parseInt(getTagValue("id", element)));
			us.setTipe(Integer.parseInt(getTagValue("tipe", element)));
			us.setName(getTagValue("name", element));
			us.setCount(Integer.parseInt(getTagValue("count", element)));
			us.setGenre(getTagValue("genre", element));
		}

		return us;
	}

	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

	private static String Class_chenge(int clas) {
		switch (clas) {
		case 1:
			return "�������";
		case 2:
			return "���";
		case 3:
			return "������";
		default:
			return "������";

		}
	}
	private  void Sort_l(int s) {
		switch(s){
		case 1:
			usList.sort(Book.IdComparator);
			tableModel.setRowCount(0);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Object[] array = new String[] { String.valueOf(usList.get(i).getBook_id()),
						Class_chenge((usList.get(i).getTipe())), usList.get(i).getName(),
						String.valueOf(usList.get(i).getCount()), usList.get(i).getGenre() };
				tableModel.addRow(array);
			}
			break;
		case 2:
			usList.sort(Book.NameComparator);
			tableModel.setRowCount(0);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Object[] array = new String[] { String.valueOf(usList.get(i).getBook_id()),
						Class_chenge((usList.get(i).getTipe())), usList.get(i).getName(),
						String.valueOf(usList.get(i).getCount()), usList.get(i).getGenre() };
				tableModel.addRow(array);
			}
			break;
		case 3:
			usList.sort(Book.CountComparator);
			tableModel.setRowCount(0);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Object[] array = new String[] { String.valueOf(usList.get(i).getBook_id()),
						Class_chenge((usList.get(i).getTipe())), usList.get(i).getName(),
						String.valueOf(usList.get(i).getCount()), usList.get(i).getGenre() };
				tableModel.addRow(array);
			}
			
			break;
			
			
	}
		
	}

	private static int R_Class_chenge(String clas) {
		switch (clas) {
		case "�������":
			return 1;
		case "���":
			return 2;
		case "������":
			return 3;
		default:
			return 4;

		}
	}

}