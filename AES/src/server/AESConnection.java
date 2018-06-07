package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AESConnection {

	private static Connection conn = null;
	private static Object lock = new Object();

	static {
		synchronized (lock) {
			if (conn == null) {
				initConnection();
			}
		}
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/aes?autoReconnect=true&useSSL=false","root","root");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}

	public static ResultSet handleGetQuery(String query, Object[] values) throws Exception
	{
		/*Statement stmt;
		ResultSet rs=null;
		try 
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			//stmt.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;*/
		
		try
		{
			// create the java mysql update preparedstatement
			PreparedStatement preparedStmt = prepareStatement(query, values);

			return preparedStmt.executeQuery();
			//conn.close();
		}
		catch (SQLException e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			throw new Exception(e.getMessage() + " : " + e.getCause());
		}
		
	}
	
	public static void handleUpdateQuery(String query, Object[] values)
	{
		try
		{
			// create the java mysql update preparedstatement
			PreparedStatement preparedStmt = prepareStatement(query, values);

			// execute the java preparedstatement
			preparedStmt.executeUpdate();

			//conn.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
	
	private static PreparedStatement prepareStatement(String query, Object[] values) throws SQLException {
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		if (values!=null) {
			for (int i=0; i<values.length; i++) {
				if (values[i] instanceof Integer) {
					preparedStmt.setInt(i+1, (int) values[i]);
				} else if (values[i] instanceof String) {
					preparedStmt.setString(i+1, values[i].toString());
				} 
			}
		}

		return preparedStmt;
	}
	

	/////////////////////////////////////	For testing	////////////////////////////////////

//	public static void main(String[] args) 
//	{
//		initConnection();
//		printQuestions();
//	}
//
//	public static void printQuestions()
//	{
//		try 
//		{
//			ResultSet rs = getQueryResult("Select * From questions");
//			while(rs.next())
//			{
//				// Print out the values
//				System.out.println(rs.getString(4)); //Question Text field
//			} 
//			rs.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}


