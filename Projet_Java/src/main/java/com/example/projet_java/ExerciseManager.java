package com.example.projet_java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ExerciseManager {
    public void displayExercises() {
        String query = "SELECT * FROM Exercise";  // Exemple de requête pour récupérer les exercices
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Parcourir le résultat
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");

                System.out.println("ID: " + id + ", Title: " + title + ", Description: " + description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
