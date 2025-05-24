package model;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object (DAO) for the Exercise entity.
 * Handles database operations such as insert, retrieve and filter exercises.
 */
public class ExerciseDAO {

    private final Connection conn;

    /**
     * Default constructor. Automatically gets the connection from DatabaseConnection.
     */
    public ExerciseDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Connection error in ExerciseDAO", e);
        }
    }

    /**
     * Constructor with an existing connection (for reuse or testing).
     *
     * @param conn the database connection
     */
    public ExerciseDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new exercise into the database.
     *
     * @param exercise the Exercise object to insert
     * @return true if successful, false otherwise
     */
    public boolean insertExercise(Exercise exercise) {
        String sql = "INSERT INTO Exercise " +
                "(title, description, type, exercise_image, solution_image, solution, difficulty, BaseCode, LineCode) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, exercise.getTitle());
            stmt.setString(2, exercise.getDescription());
            stmt.setString(3, exercise.getType());
            stmt.setBytes(4, exercise.getExercisePdf());
            stmt.setBytes(5, exercise.getSolutionPdf());
            stmt.setString(6, exercise.getSolution());
            stmt.setInt(7, exercise.getDifficulty());
            stmt.setString(8, exercise.getBaseCode());
            stmt.setInt(9, exercise.getLineCode());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error insertExercise: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the last inserted exercise ID.
     *
     * @return the last inserted ID, or -1 on failure
     */
    public int getLastInsertedExerciseId() {
        String sql = "SELECT LAST_INSERT_ID()";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving last ID: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Retrieves all exercises in the database.
     *
     * @return a list of Exercise objects
     */
    public List<Exercise> getAllExercises() {
        List<Exercise> list = new ArrayList<>();
        String sql = "SELECT * FROM Exercise";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // For each result row, create an Exercise and add to the list
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
                ex.setBaseCode(rs.getString("BaseCode"));
                ex.setLineCode(rs.getInt("LineCode"));
                list.add(ex);
            }

        } catch (SQLException e) {
            System.err.println("Error getAllExercises: " + e.getMessage());
        }

        return list;
    }

    /**
     * Retrieves exercises linked to a specific language.
     *
     * @param languageId the ID of the language
     * @return a list of Exercise objects
     */
    public List<Exercise> getExercisesByLanguage(int languageId) {
        List<Exercise> exercises = new ArrayList<>();
        String sql = """
            SELECT e.* FROM Exercise e
            JOIN ExerciseLanguage el ON e.id = el.exercise_id
            WHERE el.language_id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, languageId);
            ResultSet rs = stmt.executeQuery();

            // Map each result row to an Exercise
            while (rs.next()) {
                Exercise ex = new Exercise();
                ex.setId(rs.getInt("id"));
                ex.setTitle(rs.getString("title"));
                ex.setDifficulty(rs.getInt("difficulty"));
                ex.setType(rs.getString("type"));
                ex.setBaseCode(rs.getString("BaseCode"));
                ex.setLineCode(rs.getInt("LineCode"));
                exercises.add(ex);
            }
        } catch (SQLException e) {
            System.err.println("Error getExercisesByLanguage: " + e.getMessage());
        }

        return exercises;
    }

    /**
     * Retrieves exercises for a specific language and type (e.g., "INCLUDE").
     *
     * @param languageId the language ID
     * @param type       the exercise type
     * @return a list of matching exercises
     */
    public List<Exercise> getExercisesByLanguageAndType(int languageId, String type) {
        List<Exercise> exercises = new ArrayList<>();
        String sql = """
            SELECT e.* FROM Exercise e
            JOIN ExerciseLanguage el ON e.id = el.exercise_id
            WHERE el.language_id = ? AND e.type = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, languageId);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();

            // Map each result row to an Exercise
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
                ex.setBaseCode(rs.getString("BaseCode"));
                ex.setLineCode(rs.getInt("LineCode"));
                exercises.add(ex);
            }
        } catch (SQLException e) {
            System.err.println("Error getExercisesByLanguageAndType: " + e.getMessage());
        }

        return exercises;
    }
}
