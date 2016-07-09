package paddy.main;

import java.awt.Color;
import java.awt.Font;

import paddy.auth.LoginWindow;
import paddy.mysql.DatabaseManagement;;

public class PaddyLauncher {

	// main color and variants of it to be used throughout
	public static Color mainCol = new Color(161, 203, 203);
	public static Color darkerCol = new Color(108, 165, 165);
	public static Color darkestCol = new Color(67, 134, 134);
	public static Color lighterCol = new Color(209, 232, 232);
	public static Color lightestCol = new Color(244, 251, 251);

	// main font for whole program
	public static Font mainFont = new Font("Euphemia", Font.PLAIN, 18);

	public static void main(String[] args) {
		
		LoginWindow login = new LoginWindow();
		login.setVisible(true);

	}
}
