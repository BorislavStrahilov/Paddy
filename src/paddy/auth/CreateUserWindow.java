package paddy.auth;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import paddy.main.PaddyLauncher;

public class CreateUserWindow extends JFrame{
	
	//JFrame's size
	private int windowWidth = 400;
	private int windowHeight = 400;
	
	private JPanel panel;
	
	//user's name
	private String username;

	public CreateUserWindow(String username){
		
		this.username = username;
		
		//basic JFrame options
		setTitle("Login");
		setIconImage(new ImageIcon("paddyIcon.png").getImage());
		setBounds(300, 300, windowWidth, windowHeight);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = new JPanel(null);
		
	
		//create and set up all the components needed
		componentSetUp();


		setContentPane(panel);
		getContentPane().setBackground(PaddyLauncher.mainCol);
	}

	private void componentSetUp() {
		// TODO Auto-generated method stub
		
	}
}
