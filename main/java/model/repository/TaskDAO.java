package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;

import model.Task;
import model.User;


public class TaskDAO {
	
	// SQL TABLE Data
	private final static String TABLE_NAME = "todos";
	private final static String COLUMN_NAME =  "name";
	private final static String COLUMN_CHECKED = "checked";
	private final static String COLUMN_USER_NAME = "user_name";
	private final static String COLUMN_USER_ID = "userID";
	
	// SQL Statements
	private final static String QUERY_USERS = "SELECT * FROM todos, users WHERE userID = id";
	private final static String QUERY_USERID = "SELECT id FROM users WHERE user_name =";
	private final static String QUERY_ALL_TASKS_FROM_USER = " SELECT * FROM " +"(" + QUERY_USERS +")" + " tasks " + " WHERE " +COLUMN_USER_NAME +"=?";

	private final static String DELETE_TASK_FROM_USER = "DELETE FROM "+ TABLE_NAME + " WHERE "+COLUMN_NAME+"=?"+" AND "+COLUMN_USER_ID+"=?";
	
	public static ArrayList<Task> queryAll(User user) {
		ArrayList<Task> tasks = new ArrayList<>();
		String name;

		try {
			//Connection
			Connection conn = DBConnector.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_ALL_TASKS_FROM_USER);
			statement.setString(1, user.getUserName());

			// Debug
			System.out.println("Class TaskDAO: " + statement);
			
			ResultSet results = statement.executeQuery();
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
	public static void updateTask(Task task, User user) {
		
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
	public static void deleteTask(Task task, User user) {
		// TODO Auto-generated method stub
		
		try {
			// Connection
			Connection conn = DBConnector.getConnection();
			
			// Statement
			Statement prestatement = conn.createStatement();
			ResultSet preresults = prestatement.executeQuery(QUERY_USERID+"\'"+user.getUserName()+"\'");
			String user_id_nr = "";
			
			while (preresults.next()) {
				 user_id_nr = preresults.getString(1);
			}
			
			PreparedStatement statement = conn.prepareStatement(DELETE_TASK_FROM_USER);
			statement.setString(1, task.getText());
			statement.setString(2, user_id_nr);
			
			System.out.println("Class TaskDAO, delete: "+statement);
			// Execute
			int result = statement.executeUpdate();
			if(result>0)
				System.out.println("Rows deleted: "+result);
			//Close
			statement.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println("Delete went wrong: " + e.getMessage());
		}
	}

}
