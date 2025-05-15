
package com.example.projet_java;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {


    private static final String URL = "jdbc:mysql://localhost:3307/codingame";  // URL de la base de données
    private static final String USER = "root";  // Nom d'utilisateur de la base de données
    private static final String PASSWORD = "ProjetJava2526!!!";  // Le mot de passe de la base de données

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                // Charger le driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Etablir la connexion
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new SQLException("JDBC Driver not found.", e);
            }
        }
        return connection;
    }

    // Fermer la connexion
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

