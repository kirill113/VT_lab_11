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

import Library.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AdminFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Модель данных таблицы
	private DefaultTableModel tableModel;
	private JTable table1;
	private NodeList nodeList;
	private ArrayList<User> usList = new ArrayList<User>();

	// Заголовки столбцов
	private Object[] columnsHeader = new String[] { "id", "Имя","Пароль", "Админ/Библиотекарь","книги" };

	public AdminFrame() {
		super("Пример использования TableModel");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Создание стандартной модели
		tableModel = new DefaultTableModel();
		// Определение столбцов
		tableModel.setColumnIdentifiers(columnsHeader);
		// Наполнение модели данными
		ReadXML();
		if(nodeList!=null) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Object[] array = new String[] { String.valueOf(usList.get(i).getUser_id()), usList.get(i).getName(),usList.get(i).getpasword(),Class_chenge((usList.get(i).getUser_class())),usList.get(i).getBook_t_id() };
			tableModel.addRow(array);
		}}else {
			 Object[] array = new String[] {"","" ,""};
			 tableModel.addRow(array);
		}
		// Создание таблицы на основании модели данных
		table1 = new JTable(tableModel);
		// Создание кнопки добавления строки таблицы
		JButton add = new JButton("Добавить");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Номер выделенной строки
				int idx = table1.getSelectedRow();
				// Вставка новой строки после выделенной
				tableModel.insertRow(idx + 1, new String[] { "" + String.valueOf(table1.getRowCount()), "", "" });
			}
		});
		JButton  save= new JButton("Сохранить");
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
		// Создание кнопки удаления строки таблицы
		JButton remove = new JButton("Удалить");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Номер выделенной строки
				int idx = table1.getSelectedRow();
				// Удаление выделенной строки
				tableModel.removeRow(idx);
			}
		});
		// Создание таблицы на основе модели данных

		// Определение высоты строки

		// Формирование интерфейса
		Box contents = new Box(BoxLayout.Y_AXIS);
		contents.add(new JScrollPane(table1));

		getContentPane().add(contents);

		JPanel buttons = new JPanel();
		buttons.add(add);
		buttons.add(save);
		buttons.add(remove);
		getContentPane().add(buttons, "South");
		// Вывод окна на экран
		setSize(400, 300);
		setVisible(true);
	}DocumentBuilder builder;

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

				Element RootElement2 = doc.createElement("user");

				Element NameElementTitle = doc.createElement("name");
				NameElementTitle.appendChild(doc.createTextNode(String.valueOf(table1.getValueAt(i, 1))));
				RootElement2.appendChild(NameElementTitle);

				Element NameElementCompile = doc.createElement("pasword");
				NameElementCompile.appendChild(doc.createTextNode(String.valueOf(table1.getValueAt(i, 2))));
				RootElement2.appendChild(NameElementCompile);

				Element NameElementCompile1 = doc.createElement("class");
				NameElementCompile1.appendChild(doc.createTextNode(String.valueOf(R_Class_chenge(String.valueOf(table1.getValueAt(i, 3))))));
				RootElement2.appendChild(NameElementCompile1);
				
				Element NameElementCompile11 = doc.createElement("Book_id");
				NameElementCompile11.appendChild(doc.createTextNode(String.valueOf(table1.getValueAt(i, 4))));
				RootElement2.appendChild(NameElementCompile11);


				Element NameElementRuns = doc.createElement("id");
				NameElementRuns.appendChild(doc.createTextNode(String.valueOf(table1.getValueAt(i, 0))));
				RootElement2.appendChild(NameElementRuns);
				RootElement.appendChild(RootElement2);
			}
			doc.appendChild(RootElement);

			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("users.xml")));
			
	
		
		
	}

	private static User getUser(Node node) {
		User us = new User();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			us.setName(getTagValue("name", element));
			us.setpasword(getTagValue("pasword", element));
			us.setUser_id(Integer.parseInt(getTagValue("id", element)));
			us.setUser_class(Integer.parseInt(getTagValue("class", element)));
			us.setBook_t_id(getTagValue("Book_id", element));
		}

		return us;
	}

	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

	private static String Class_chenge(int clas) {
		if (clas == 2) {
			return "Библиотекарь";
		} else {

			if (clas == 3) {
				return "Админ";
			} else {
				return "Посетитель";
			}
		}

	}
	private static int R_Class_chenge(String clas) {
		if (clas.equals("Библиотекарь")) {
			return 2;
		} else {

			if (clas.equals("Админ")) {
				return 3;
			} else {
				return 1;
			}
		}
	}

}