package com.example.projet_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {

   /* public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/codingame"; // ⚠️ change ça
        String user = "root"; // ⚠️ change ça
        String password = "maikimaik";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println(" Connexion réussie à la base de données !");
        } catch (SQLException e) {
            System.out.println(" Connexion échouée : " + e.getMessage());
        }
    }*/

    public static void main(String[] args) {
        User user = new User("maiki", "maiki@example.com", "hashpassword123");

        UserDAO dao = new UserDAO();
        boolean success = dao.addUser(user);

        if (success) {
            System.out.println("✅ Utilisateur ajouté avec succès !");
        } else {
            System.out.println("❌ Échec de l'ajout.");
        }
    }




}
