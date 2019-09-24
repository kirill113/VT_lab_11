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
import Library.User;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UserFrame extends JFrame {
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	// Модель данных таблицы
	
	private DefaultTableModel tableModel,tableModel2;
	private JTable table1,table12;
	private NodeList nodeList;
	private ArrayList<Book> usList = new ArrayList<Book>();

	// Заголовки столбцов
	private Object[] columnsHeader = new String[] { "id", "тип", "Имя", "кол-во", "жанр/сфера" };
	private Object[] columnsHeader2 = new String[]  { "id", "Имя","Взятые_книги" };

	public UserFrame(User you) {
		
		super("Пример использования TableModel");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Создание стандартной модели
		
		tableModel = new DefaultTableModel();
		tableModel2 = new DefaultTableModel();
		// Определение столбцов
		tableModel2.setColumnIdentifiers(columnsHeader2);
		tableModel.setColumnIdentifiers(columnsHeader);
		// Наполнение модели данными
		Object[] array = new String[] { String.valueOf(you.getUser_id()), you.getName(),you.getBook_t_id() };
		tableModel2.addRow(array);
		ReadXML();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Object[] array1 = new String[] { String.valueOf(usList.get(i).getBook_id()),
						Class_chenge((usList.get(i).getTipe())), usList.get(i).getName(),
						String.valueOf(usList.get(i).getCount()), usList.get(i).getGenre() };
				tableModel.addRow(array1);
			}
		} else {
			Object[] array1 = new String[] { "", "", "" };
			tableModel.addRow(array1);
		}
		// Создание таблицы на основании модели данных
		
		table1 = new JTable(tableModel);
		table12 = new JTable(tableModel2);
		// Создание кнопки добавления строки таблицы
		
		
		// Создание кнопки удаления строки таблицы
		
		

		// Создание таблицы на основе модели данных

		// Определение высоты строки

		// Формирование интерфейса
		Box contents = new Box(BoxLayout.Y_AXIS);
		contents.add(new JScrollPane(table12));
		contents.add(new JScrollPane(table1));

		getContentPane().add(contents);

		JPanel buttons = new JPanel();
	
		getContentPane().add(buttons, "South");
		// Вывод окна на экран
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
			return "Учебник";
		case 2:
			return "Худ";
		case 3:
			return "Пресса";
		default:
			return "другое";

		}
	}

	private static int R_Class_chenge(String clas) {
		switch (clas) {
		case "Учебник":
			return 1;
		case "Худ":
			return 2;
		case "Пресса":
			return 3;
		default:
			return 4;

		}
	}

}