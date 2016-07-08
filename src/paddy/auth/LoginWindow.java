package paddy.auth;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import paddy.main.PaddyLauncher;
import paddy.main.TextPrompt;

public class LoginWindow extends JFrame{
	
	private int windowWidth = 260;
	private int windowHeight = 280;
	
	private int textFieldWidth = windowWidth - 45;
	private int textFieldHeight = 30;
	
	private int buttonWidth = (textFieldWidth/2) - 5;
	private int buttonHeight = textFieldHeight;
	
	private JPanel panel;
	
	//username field
	private JTextField userField;
	private TextPrompt userPrompt;
	
	//password field
	private JPasswordField passwordField;
	private TextPrompt passwordPrompt;
	
	//login and create account buttons
	private JButton loginButton;
	private JButton registerButton;
	
	//JLabel to hold the logo
	private JLabel logo;
	
	//CONSTRUCTOR
	public LoginWindow(){
		
		//basic JFrame options
		setTitle("Login");
		setIconImage(new ImageIcon("paddyIcon.png").getImage());
		setBounds(300, 300, windowWidth, windowHeight);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = new JPanel(null);
		
	
		//create and set up all the components needed
		componentSetUp();

		//add components to the panel
		panel.add(userField);
		panel.add(passwordField);
		panel.add(loginButton);
		panel.add(registerButton);
		
		panel.add(logo);

		setContentPane(panel);
		getContentPane().setBackground(PaddyLauncher.mainCol);
	}
	
	private void componentSetUp(){
		
		//logo
		logo = new JLabel(new ImageIcon("paddyLogo.png"));
		logo.setBounds(20, 10, textFieldWidth, 100);
		
		//user field creation and options
		userField = new JTextField();
		userField.setBounds(20, (int) (windowHeight * 0.45), textFieldWidth, textFieldHeight);
		userField.setHorizontalAlignment(JTextField.CENTER);
		userField.setFont(PaddyLauncher.mainFont);
		userField.setForeground(PaddyLauncher.darkestCol);
		userField.setBorder(null);
		userField.setVisible(true);
		
		//text prompt for the username field
		userPrompt = new TextPrompt("username", userField, TextPrompt.Show.ALWAYS);
		userPrompt.changeAlpha(0.5f);
		userPrompt.setHorizontalAlignment(JLabel.CENTER);
		userPrompt.setFont(PaddyLauncher.mainFont);
		userPrompt.changeStyle(Font.ITALIC);
		
		//password field creation and options
		passwordField = new JPasswordField();
		passwordField.setBounds(userField.getX(), userField.getY() + textFieldHeight + 10, textFieldWidth, textFieldHeight);
		passwordField.setHorizontalAlignment(JTextField.CENTER);
		passwordField.setFont(PaddyLauncher.mainFont);
		passwordField.setForeground(PaddyLauncher.darkestCol);
		passwordField.setBorder(null);
		passwordField.setVisible(true);
		
		//text prompt for the password field
		passwordPrompt = new TextPrompt("password", passwordField, TextPrompt.Show.ALWAYS);
		passwordPrompt.changeAlpha(0.5f);
		passwordPrompt.setHorizontalAlignment(JLabel.CENTER);
		passwordPrompt.setFont(PaddyLauncher.mainFont);
		passwordPrompt.changeStyle(Font.ITALIC);
		
		//login button
		loginButton = new JButton("Login");
		loginButton.setBounds(userField.getX(), passwordField.getY() + textFieldHeight + 10, buttonWidth, buttonHeight);
		loginButton.setBackground(PaddyLauncher.lighterCol);

		//create account button
		registerButton = new JButton("Create");
		registerButton.setBounds(loginButton.getX() + 10 + buttonWidth, passwordField.getY() + textFieldHeight + 10, buttonWidth, buttonHeight);
		registerButton.setBackground(PaddyLauncher.lighterCol);

		


	}
}
