package model.repository;

import model.User;

import java.sql.*;

public class UserDAO {
	private final static String TABLE_NAME = "users";
	// private final static String COLUMN_USER_ID = "id";
	private final static String COLUMN_FIRST_NAME = "first_name";
	private final static String COLUMN_LAST_NAME = "last_name";
	private final static String COLUMN_USER_NAME = "user_name";
	private final static String COLUMN_PASSWORD = "password";

	private static boolean isUser(Connection connection, String first_name, String last_name) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_FIRST_NAME + "= ?" + "  AND " + COLUMN_LAST_NAME + "= ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, first_name);
			statement.setString(2, last_name);

			ResultSet resultSet = statement.executeQuery();
			Boolean result = resultSet.next();
			resultSet.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean signIn(String first_name, String last_name, String user_name, String password) {
		Boolean result = false;
		try {
			Connection connection = DBConnector.getConnection();

			if (!isUser(connection, first_name, last_name)) {
				Statement statement = connection.createStatement();
				String sql = "INSERT INTO users (" + COLUMN_FIRST_NAME + "," + COLUMN_LAST_NAME + "," + COLUMN_USER_NAME
						+ "," + COLUMN_PASSWORD + ") " + "VALUES(" + "'" + first_name + "'" + "," + "'" + last_name
						+ "'" + "," + "'" + user_name + "'" + "," + "'" + password + "'" + "); ";
				statement.execute(sql);
				result = true;
				statement.close();
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("Something went wrong: " + e.getMessage());
			result = false;
		}

		return result;
	}

	public static User logIn(String user_name, String password){
			User user = null;
			Connection connection;
			
			try {
				connection = DBConnector.getConnection();
				Statement statement = connection.createStatement();
				String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "
								+COLUMN_USER_NAME+"='"+ user_name +"' AND "
								+COLUMN_PASSWORD+"='"+password+"';";
				
				//Debug: print the statement
				System.out.println("Class UserDAO: "+sql);
				
				ResultSet resultSet = statement.executeQuery(sql);
				if(resultSet.next()) {
					user = new User();
					user.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
					user.setLastName(resultSet.getString(COLUMN_LAST_NAME));
					user.setUserName(resultSet.getString(COLUMN_USER_NAME));
					user.setPassword(resultSet.getString(COLUMN_PASSWORD));
					
					//Debug: print the Object
					System.out.println("Class UserDAO: User: "+user.getUserName()+" "+user.getPassword());
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return user;
	}

}
