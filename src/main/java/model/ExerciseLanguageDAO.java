package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for managing the many-to-many relationship between exercises and programming languages.
 * Maps entries in the ExerciseLanguage table.
 */
public class ExerciseLanguageDAO {

    private final Connection conn;

    /**
     * Constructor with active database connection.
     *
     * @param conn the database connection to use
     */
    public ExerciseLanguageDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a relation (exercise_id, language_id) into the ExerciseLanguage table.
     *
     * @param exerciseId the ID of the exercise
     * @param languageId the ID of the language
     * @return true if insertion succeeded, false otherwise
     */
    public boolean insertRelation(int exerciseId, int languageId) {
        String sql = "INSERT INTO ExerciseLanguage (exercise_id, language_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exerciseId);
            stmt.setInt(2, languageId);
            int rows = stmt.executeUpdate();

            System.out.println("Relation inserted: exercise " + exerciseId + " ↔ language " + languageId);
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error insertRelation: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all language IDs associated with a given exercise.
     *
     * @param exerciseId the ID of the exercise
     * @return a list of language IDs associated with the exercise
     */
    public List<Integer> getByExerciseId(int exerciseId) {
        List<Integer> languageIds = new ArrayList<>();
        String sql = "SELECT language_id FROM ExerciseLanguage WHERE exercise_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exerciseId);
            ResultSet rs = stmt.executeQuery();

            // Loop through the results and collect each language ID
            while (rs.next()) {
                languageIds.add(rs.getInt("language_id"));
            }

        } catch (SQLException e) {
            System.err.println("Error getByExerciseId: " + e.getMessage());
        }

        return languageIds;
    }
}
