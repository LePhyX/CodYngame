package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a static database connection utility.
 * Manages connection to the MySQL database used by the application.
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/codingame";
    private static final String USER = "root";
    private static final String PASSWORD = "maikimaik";

    private static Connection connection;

    /**
     * Returns a singleton Connection instance.
     * If the connection is closed or not initialized, it opens a new one.
     *
     * @return the database connection
     * @throws SQLException if the connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        // If no connection exists or the current one is closed, reinitialize it
        if (connection == null || connection.isClosed()) {
            try {
                // Load MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish connection to the database
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                // Rethrow as SQLException for uniform exception handling
                e.printStackTrace();
                throw new SQLException("JDBC Driver not found.", e);
            }
        }
        return connection;
    }

    /**
     * Closes the current database connection if it exists.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                // Attempt to close the connection safely
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
