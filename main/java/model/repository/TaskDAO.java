package model.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Task;
import model.User;


public class TaskDAO {
	
	private final static String TABLE_NAME = "todos";
	private final static String COLUMN_NAME =  "name";
	private final static String COLUMN_CHECKED = "checked";
	private final static String COLUMN_USER_NAME = "user_name";
	private final static String COLUMN_USER_ID = "userID";
	private final static String QUERY_USERS = "SELECT * FROM todos, users WHERE userID = id";
	private final static String QUERY_USERID = "SELECT id FROM users WHERE user_name =";
	
	private final static String QUERY_ALL_TASKS_FROM_USER = " SELECT * FROM " 
			+"(" + QUERY_USERS +")"
			+ " tasks " + " WHERE " +COLUMN_USER_NAME +"=";
	
	public static ArrayList<Task> queryDBTasks(User user) {
		ArrayList<Task> tasks = new ArrayList<>();
		String name;

		try {
			//Connection
			Connection conn = DBConnector.getConnection();
			Statement statement = conn.createStatement();

			// Debug
			System.out.println("Class TaskDAO: " + QUERY_ALL_TASKS_FROM_USER + "\'"+user.getUserName()+"\'");
			
			ResultSet results = statement.executeQuery(QUERY_ALL_TASKS_FROM_USER + "\'"+user.getUserName()+"\'");
			while (results.next()) {
				name = results.getString(1);
				tasks.add(new Task(name,results.getBoolean(2)));
			}
			
			//Close All
			results.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println("Query went wrong: " + e.getMessage());
		}
		return tasks;
	}
	public static void updateDBTasks(Task task, User user) {
		
		try {
			//Connection
			Connection conn = DBConnector.getConnection();
			Statement statement = conn.createStatement();

			// Write Data to Table
			int affectedRows = statement.executeUpdate(
											"UPDATE " 
											+ TABLE_NAME +" "
											+"SET "+ COLUMN_CHECKED+"="+task.isCheck()+ " "
											+"WHERE "+COLUMN_NAME+"="+"\'"+task.getText()+"\'"
											);
			
			if(affectedRows == 0){
				// Debug
				System.out.println("Class TaskDAO: "+  "INSERT INTO " 
						+ TABLE_NAME+" "
						+"("+COLUMN_NAME+", "+COLUMN_CHECKED+") "
						+ "VALUES(\'"+task.getText()+"\',"+task.isCheck()+", ("+QUERY_USERID+"\'"+user.getUserName()+"\'"+"))");
				
				statement.executeUpdate(
						"INSERT INTO " 
						+ TABLE_NAME+" "
						+"("+COLUMN_NAME+", "+COLUMN_CHECKED+", "+COLUMN_USER_ID+") "
						+ "VALUES(\'"+task.getText()+"\',"+task.isCheck()+", ("+QUERY_USERID+"\'"+user.getUserName()+"\'"+"))"
						);
			}
			//Close All
			statement.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println("Insert/Update went wrong: " + e.getMessage());
		}
		
	}

}
