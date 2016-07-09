package paddy.auth;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import paddy.main.MainWindow;
import paddy.main.PaddyLauncher;
import paddy.main.TextPrompt;
import paddy.mysql.DatabaseManagement;

public class LoginWindow extends JFrame{
	
	private int windowWidth = 260;
	private int windowHeight = 300;
	
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
	
	//JLabel to display general info
	private JLabel infoLabel;
	
	//String to hold specific info to be displayed
	private String infoText;
	
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
		panel.add(infoLabel);

		setContentPane(panel);
		getContentPane().setBackground(PaddyLauncher.mainCol);
	}
	
	private void componentSetUp(){
		
		//logo
		logo = new JLabel(new ImageIcon("paddyLogo.png"));
		logo.setBounds(20, 10, textFieldWidth, 100);
		
		//using HTML inside the label to allow for multiple lines
		infoText = "Enter your credentials, or create an account.";
		infoLabel = new JLabel("<html><div style='text-align: center;'>" + infoText + "</html>");
		infoLabel.setBounds(20, (int) (windowHeight * 0.38), textFieldWidth, textFieldHeight);
		infoLabel.setHorizontalAlignment(JLabel.CENTER);
		infoLabel.setFont(PaddyLauncher.mainFont.deriveFont(Font.ITALIC, 12f));
		
		//user field creation and options
		userField = new JTextField();
		userField.setBounds(20, (int) (windowHeight * 0.50), textFieldWidth, textFieldHeight);
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
		loginButton.setFocusPainted(false);
		
		//action listener for login
		loginButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    loginButtonPressed();
			  } 
		} );
		
		//create account button
		registerButton = new JButton("Create");
		registerButton.setBounds(loginButton.getX() + 10 + buttonWidth, passwordField.getY() + textFieldHeight + 10, buttonWidth, buttonHeight);
		registerButton.setBackground(PaddyLauncher.lighterCol);
		registerButton.setFocusPainted(false);
		
		//action listener for register
		registerButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    registerButtonPressed();
			  } 
		} );

	}

	//user presses register button
	private void registerButtonPressed() {

		//get password and username as strings
		String password = new String(passwordField.getPassword());
		String username = userField.getText();
		
		// create password hash and salt
		String salt = BCrypt.gensalt();
		String passHash = BCrypt.hashpw(password, salt);

		if(DatabaseManagement.createUser(username, salt, passHash)){
			//user created succesfully
			CreateUserWindow cuw = new CreateUserWindow(username);
			cuw.setVisible(true);
			dispose();

		}else{
			//there was a user with that name alread
			infoText = "Sorry, that username is already in use.";
			infoLabel.setText("<html><div style='text-align: center;'>" + infoText + "</html>");
			infoLabel.setForeground(Color.red);
		}

	}

	//user presses login Button
	private void loginButtonPressed() {
		
		//get username and password as strings
		String username = userField.getText();
		String password = new String(passwordField.getPassword());

		//empty field check
		if(username.equals("")){
			infoText = "Both password and username field must be filled.";
			infoLabel.setText("<html><div style='text-align: center;'>" + infoText + "</html>");
			infoLabel.setForeground(Color.red);

		}else{
			
			
			if(DatabaseManagement.userLogin(username, password)){
				//login was successful
				MainWindow mw = new MainWindow(username);
				mw.setVisible(true);
				dispose();
			}else{
				// login unsucesfull either due to
				// wrong username or password
				infoText = "Either the username or password did not match.";
				infoLabel.setText("<html><div style='text-align: center;'>" + infoText + "</html>");
				infoLabel.setForeground(Color.red);
			}
			

		}
		
		//get password as a string
		String pass = new String(passwordField.getPassword());
		
	}
}
