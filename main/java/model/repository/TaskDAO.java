package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Task;
import model.User;

public class TaskDAO {

	// SQL TABLE Data
	private final static String TABLE_NAME = "todos";
	private final static String COLUMN_TEXT = "name";
	private final static String COLUMN_CHECKED = "checked";
	private final static String COLUMN_TASK_ID = "taskID";
	private final static String COLUMN_USER_NAME = "user_name";
	private final static String COLUMN_USER_ID = "userID";

	// SQL QUERYS
	private final static String QUERY_USERS = "SELECT * FROM todos, users WHERE userID = id";
	private final static String QUERY_USERID = "SELECT id FROM users WHERE user_name =";
	private final static String QUERY_ALL_TASKS_FROM_USER = " SELECT * FROM " + "(" + QUERY_USERS + ")" + " tasks " + " WHERE " + COLUMN_USER_NAME + "=?";
	// SQL INSERT/UPDATE
	private final static String INSERT_TASK = "INSERT INTO " + TABLE_NAME + " " + "(" + COLUMN_TEXT + ", " + COLUMN_CHECKED + ", " + COLUMN_USER_ID + ") "
			+ "VALUES(?,?, (" + QUERY_USERID + "?))";
	private final static String UPDATE_TASK = "UPDATE " + TABLE_NAME + " SET " + COLUMN_CHECKED + "=?, " + COLUMN_TEXT + "=?" + " WHERE " + COLUMN_TASK_ID + "=?";
	// SQL DELETE
	private final static String DELETE_TASK_FROM_USER = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TASK_ID + "=?" + " AND " + COLUMN_USER_ID + "=?";

	public static ArrayList<Task> queryAll(User user) {
		ArrayList<Task> tasks = new ArrayList<>();
		String name;
		boolean check;
		int id;

		try {
			// Connection
			Connection conn = DBConnector.getConnection();
			PreparedStatement statement = conn.prepareStatement(QUERY_ALL_TASKS_FROM_USER);
			statement.setString(1, user.getUserName());

			// Debug
			System.out.println("Class TaskDAO: " + statement);

			ResultSet results = statement.executeQuery();

			// Encapsulate Data from DB into Java Object
			while (results.next()) {
				name = results.getString(1);
				check = results.getBoolean(2);
				id = results.getInt(4);
				tasks.add(new Task(name, check, id));
			}

			// Close All
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
			// Connection
			Connection conn = DBConnector.getConnection();
			PreparedStatement statement = null;
			int affectedRows = 0;

			// Write Data to Table
			if (task.getId() > 0) {
				statement = conn.prepareStatement(UPDATE_TASK);
				statement.setBoolean(1, task.isCheck());
				statement.setString(2, task.getText());
				statement.setInt(3, task.getId());
				
				// Debug
				System.out.println(statement);
				
				affectedRows = statement.executeUpdate();

			}
			if (affectedRows == 0) {
				statement = conn.prepareStatement(INSERT_TASK);
				statement.setString(1, task.getText());
				statement.setBoolean(2, task.isCheck());
				statement.setString(3,user.getUserName());
				
				// Debug
				System.out.println(statement);
				
				statement.executeUpdate();
			}

			// Close All
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
			ResultSet preresults = prestatement.executeQuery(QUERY_USERID + "\'" + user.getUserName() + "\'");
			String user_id_nr = "";

			while (preresults.next()) {
				user_id_nr = preresults.getString(1);
			}

			PreparedStatement statement = conn.prepareStatement(DELETE_TASK_FROM_USER);
			statement.setInt(1, task.getId());
			statement.setString(2, user_id_nr);

			System.out.println("Class TaskDAO, delete: " + statement);
			// Execute
			int result = statement.executeUpdate();
			if (result > 0)
				System.out.println("Rows deleted: " + result);
			// Close
			statement.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println("Delete went wrong: " + e.getMessage());
		}
	}

}
