package model.repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
	/*Server Data*/
	private final static String USER_NAME = "root";
	private final static String PASSWORD = "sqldatabase";
	private final static String DMBS = "mysql";
	private final static String SERVER_NAME="localhost";
	private final static int PORT = 3306;
	private final static String ENCODING="characterEncoding=UTF-8";
	private final static String DATABASE="todo_list";
	
	
	
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (InstantiationException e) {
			
			System.err.println(e.getClass());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			System.err.println(e.getClass());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			System.err.println(e.getClass());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			System.err.println(e.getClass());
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			
			System.err.println(e.getClass());
			e.printStackTrace();
		} catch (SecurityException e) {
			
			System.err.println(e.getClass());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			System.err.println(e.getClass());
			e.printStackTrace();
		}
		
		
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", USER_NAME);
	    connectionProps.put("password", PASSWORD);
	    

	    if (DMBS.equals("mysql")) {
	        conn = DriverManager.getConnection(
	                   "jdbc:" + DMBS + "://" +
	                   SERVER_NAME +
	                   ":" + PORT + "/"
	                   +DATABASE
	                   +"?"+ENCODING,
	                   connectionProps);
	    }

	    System.out.println("Connected to database");
	    return conn;
	}
}
