package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {

        public static void main(String[] args) {
            String url = "jdbc:mysql://localhost:3306/codingame"; //
            String user = "root"; //
            String password = "maikimaik";

             try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println(" Connexion réussie à la base de données !");
             } catch (SQLException e) {
            System.out.println(" Connexion échouée : " + e.getMessage());
        }
        }
    }


