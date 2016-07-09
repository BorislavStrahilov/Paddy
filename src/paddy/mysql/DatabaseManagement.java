package paddy.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import paddy.auth.BCrypt;

public class DatabaseManagement {

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/PADDY?useSSL=false";

	// Database credentials
	private static final String USER = "admin";
	private static final String PASS = "admin";
	
	private static String sql;
	private static Connection conn;
	private static Statement stmt;
	
	
	//method that will preform the checks necesarry to login the user
	public static boolean userLogin(String username, String password){
		
		boolean successful = false;
		
		conn = null;
		stmt = null;
		
		
		try {

			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// check if user is already in the database
			stmt = conn.createStatement();
			
			sql = "SELECT * FROM USERS WHERE username = '" + username + "'";
			
			//save result to be able to check if it is empty
			ResultSet rs = stmt.executeQuery(sql);
			
			//result is empty meaning user doesnt exist
			if (!rs.isBeforeFirst() ) {	
				//the login attempt failed
				successful = false;
			}else{
				
				//change the cursor to the first row
				rs.next();
				
				//user exists, now have to check password
				if(BCrypt.checkpw(password, rs.getString("password"))){
					//password check passed, user logs in
					successful = true;
				}else{
					//the passsword check failed
					successful = false;
				}
				
			}
			

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		
		
		return successful;
	}

	//method that will try to create a user
	public static boolean createUser(String username, String salt, String hashedPassword) {
		
		conn = null;
		stmt = null;
		
		// default value is false since if
		// it exits try statement then
		// the connection failed
		boolean userAdded = false;
		
		try {

			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// check if user is already in the database
			stmt = conn.createStatement();
			
			sql = "SELECT * FROM USERS WHERE username = '" + username + "'";
			
			//save result to be able to check if it is empty
			ResultSet rs = stmt.executeQuery(sql);
			
			
			//result is empty meaning user doesnt exist
			if (!rs.isBeforeFirst() ) {
				
				//add the user to the database
				stmt = conn.createStatement();

				sql = "INSERT INTO users (username, password, salt) "
							+ "VALUES ('" + username + "', '" + hashedPassword + "', '" + salt + "')";
				
				stmt.executeUpdate(sql);
				
				//save the value of return statement
				userAdded = true;
			}else{
				//save the value of return statement
				userAdded = false;
			}
			

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		
		return userAdded;
	}

	public static void connectDB() {

		conn = null;
		
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

	}

	public static void createUsersTable() {
		conn = null;
		stmt = null;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			stmt = conn.createStatement();

			sql = "CREATE TABLE USERS " + 
					"(id 			INTEGER NOT NULL AUTO_INCREMENT, " + 
					" username 		VARCHAR(255), " + 
					" password 		VARCHAR(255), " + 
					" salt 			VARCHAR(255), " + 
					" question 		VARCHAR(255), " + 
					" answer 		VARCHAR(255), " + 
					" PRIMARY KEY 	( id ))";

			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}// end create users table
	
	public static void dropTable(String table) {
		conn = null;
		stmt = null;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			stmt = conn.createStatement();

			sql = "DROP TABLE " + table;

			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}// end drop table
}
