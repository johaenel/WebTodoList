package model.repository;

import model.User;

import java.sql.*;

public class UserDAO {
    private final static String TABLE_NAME = "users";
    //private final static String COLUMN_USER_ID = "id";
    private final static String COLUMN_FIRST_NAME = "first_name";
    private final static String COLUMN_LAST_NAME = "last_name";
    private final static String COLUMN_USER_NAME = "user_name";
    private final static String COLUMN_PASSWORD = "password";

    private static boolean isUser(Connection connection, String first_name, String last_name) {
        // Debug
        System.out.println("Executing UserDao.isUser()");
        String sqlQueryUser = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_FIRST_NAME + "= ?" + "  AND " + COLUMN_LAST_NAME
                + "= ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlQueryUser);
            statement.setString(1, first_name);
            statement.setString(2, last_name);

            System.err.println("SQL Statement in case: " + statement.toString());
            ResultSet resultSet = statement.executeQuery();
            boolean result = resultSet.next();
            resultSet.close();

            return result;
        } catch (SQLException e) {
			System.err.println("Cannot search for user in database, cause: ");
            e.printStackTrace();
			System.err.println("SQL Statement in case: " + sqlQueryUser);
        }
        return false;
    }

    public static boolean signIn(String first_name, String last_name, String user_name, String password) {
        System.out.println("Executing UserDao.signIn()");
        boolean result = false;
        String sqlInsertUser = "INSERT INTO users (" + COLUMN_FIRST_NAME + "," + COLUMN_LAST_NAME + "," + COLUMN_USER_NAME
                + "," + COLUMN_PASSWORD + ") " + "VALUES(?,?,?,?); ";
        try {
            Connection connection = DBConnector.getConnection();

            if (!isUser(connection, first_name, last_name)) {
                PreparedStatement statement = connection.prepareStatement(sqlInsertUser);
                statement.setString(1, first_name);
                statement.setString(2, last_name);
                statement.setString(3, user_name);
                statement.setString(4, password);

                System.err.println("SQL Statement in case: " + sqlInsertUser);

                int affectedRow = statement.executeUpdate();
				if (affectedRow>0) {
					result = true;
				}

                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Cannot insert new user into database, cause: ");
            e.printStackTrace();
			System.err.println("SQL Statement in case: " + sqlInsertUser);
            result = false;
        }

        return result;
    }

    public static User logIn(String user_name, String password) {
        User user = null;
        Connection connection;
		String sqlQueryUserName = "SELECT * FROM " + TABLE_NAME + " WHERE "
				+ COLUMN_USER_NAME + "=? AND " + COLUMN_PASSWORD + "=?;";
        try {
            connection = DBConnector.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQueryUserName);
			statement.setString(1, user_name);
			statement.setString(2, password);

            System.err.println("SQL Statement in case: " + sqlQueryUserName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
                user.setLastName(resultSet.getString(COLUMN_LAST_NAME));
                user.setUserName(resultSet.getString(COLUMN_USER_NAME));
                user.setPassword(resultSet.getString(COLUMN_PASSWORD));
				// Verbose
                System.out.println("User object: " + user.getUserName() + " " + user.getPassword());
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
			System.err.println("Cannot query user in database, cause: " + e.getMessage());
			System.err.println("SQL Statement in case: " + sqlQueryUserName);
        }

        return user;
    }

}