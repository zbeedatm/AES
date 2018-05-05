package com.aes.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MysqlConnection {

	private static Connection conn = null;

//	private MysqlConnection() {}
//
//	public static Connection getConnection() {
//		if (conn == null) {
//			initConnection();
//		}
//		return conn;
//	}
	
	static {
		initConnection();
	}

	private static void initConnection() {
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			/* handle the error*/
		}

		try 
		{
			conn = DriverManager.getConnection("jdbc:mysql://localhost/aes","root","root");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public static ResultSet getQuestions()
	{
		Statement stmt;
		ResultSet rs=null;
		try 
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM questions;");
			//stmt.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}


	////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) 
	{
		initConnection();
		printQuestions();
	}

	public static void printQuestions()
	{
		try 
		{
			ResultSet rs = getQuestions();
			while(rs.next())
			{
				// Print out the values
				System.out.println(rs.getString(4)); //Question Text field
			} 
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}


