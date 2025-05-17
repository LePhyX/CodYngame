package com.example.projet_java;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.*;

/**
 * Classe DAO (Data Access Object) pour gérer les interactions
 * entre Java et la base de données concernant les exercices.
 **/


public class ExerciseDAO {

    private final Connection conn;

    /**
     * Constructeur du DAO, prend une connexion SQL déjà ouverte.
     * @param conn : La connexion MySQL active
     **/
    public ExerciseDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Méthode pour insérer un nouvel exercice dans la base de données.
     * @param exercise : L’objet Exercise à enregistrer
     * @return true si l’insertion a réussi, false sinon
     */
    public boolean insertExercise(Exercise exercise) {
        String sql = "INSERT INTO Exercise " +
                "(title, description, type, exercise_image, solution_image, solution, difficulty) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // DEBUG : Affichage des valeurs avant insertion
            System.out.println(" Insertion en cours avec les données suivantes :");
            System.out.println("- Titre         : " + exercise.getTitle());
            System.out.println("- Description   : " + exercise.getDescription());
            System.out.println("- Type          : " + exercise.getType());
            System.out.println("- Diff          : " + exercise.getDifficulty());
            System.out.println("- PDF énoncé    : " + (exercise.getExercisePdf() != null ? exercise.getExercisePdf().length + " octets" : "null"));
            System.out.println("- PDF solution  : " + (exercise.getSolutionPdf() != null ? exercise.getSolutionPdf().length + " octets" : "null"));
            System.out.println("- Solution text : " + exercise.getSolution());

            // Insertion des paramètres dans la requête
            stmt.setString(1, exercise.getTitle());
            stmt.setString(2, exercise.getDescription());
            stmt.setString(3, exercise.getType());
            stmt.setBytes(4, exercise.getExercisePdf());
            stmt.setBytes(5, exercise.getSolutionPdf());
            stmt.setString(6, exercise.getSolution());
            stmt.setInt(7, exercise.getDifficulty());

            // Exécution de la requête
            int rows = stmt.executeUpdate();
            System.out.println("🧾 Nombre de lignes insérées : " + rows);

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'insertion d’un exercice : " + e.getMessage());
            return false;
        }
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> list = new ArrayList<>();

        String sql = "SELECT * FROM Exercise";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Exercise ex = new Exercise();
                ex.setId(rs.getInt("id"));
                ex.setTitle(rs.getString("title"));
                ex.setDescription(rs.getString("description"));
                ex.setType(rs.getString("type"));
                ex.setExercisePdf(rs.getBytes("exercise_image"));
                ex.setSolutionPdf(rs.getBytes("solution_image"));
                ex.setSolution(rs.getString("solution"));
                ex.setDifficulty(rs.getInt("difficulty"));
                ex.setAttemptsCount(rs.getInt("attempts_count"));
                ex.setSuccessCount(rs.getInt("success_count"));
                list.add(ex);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur dans getAllExercises : " + e.getMessage());
        }

        return list;
    }
}
