package model.repository;

import model.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class TaskDAO {
	
	private final static String TABLE_NAME = "tasks";
	private final static String COLUMN_NAME =  "text";
	private final static String COLUMN_CHECKED = "check";
	
	public static ArrayList<Task> queryDBTasks() {
		ArrayList<Task> tasks = new ArrayList<>();
		String name;

		try {
			//Connection
			Connection conn = DBConnector.getConnection();
			Statement statement = conn.createStatement();

			// Query Data from Table
			ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
			while (results.next()) {
				name = results.getString(1);
				tasks.add(new Task(name,results.getBoolean(2)));
			}
			
			//Close All
			results.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println("Something went wrong: " + e.getMessage());
		}
		return tasks;
	}
	public static void updateDBTasks(Task task) {
		
		try {
			//Connection
			Connection conn = DBConnector.getConnection();
			Statement statement = conn.createStatement();

			// Write Data to Table
			int affectedRows = statement.executeUpdate(
											"UPDATE" 
											+ TABLE_NAME 
											+"SET "+ COLUMN_CHECKED+"="+task.isCheck()+ " "
											+"WHERE "+COLUMN_NAME+"="+"\'"+task.getName()+"\'"
											);
			if(affectedRows == 0){
				statement.executeUpdate(
						"INSERT INTO " 
						+ TABLE_NAME
						+"("+COLUMN_NAME+", "+COLUMN_CHECKED+") "
						+ "VALUES(\'"+task.getName()+"\',"+task.isCheck()+")"
						);
			}
			
			//Close All
			statement.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println("Something went wrong: " + e.getMessage());
		}
		
	}

}
