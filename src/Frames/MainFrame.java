package Frames;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;






public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;

	private JButton connect;

	public MainFrame() {
		setTitle("MainFrame");
		setSize(240, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initComponents();
	
		setVisible(true);
	}
	
	public void initComponents() {
		panel = new JPanel();

		

	
		connect = new JButton("Войти");

		panel.add(connect);

		add(panel);


		
	}
	

}
