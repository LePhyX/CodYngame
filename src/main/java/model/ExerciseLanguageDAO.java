package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour gérer les relations entre exercices et langages
 */
public class ExerciseLanguageDAO {

    private final Connection conn;

    // Constructeur avec connexion active
    public ExerciseLanguageDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Insère une relation (exercice_id, language_id) dans la table ExerciseLanguage
     */
    public boolean insertRelation(int exerciseId, int languageId) {
        String sql = "INSERT INTO ExerciseLanguage (exercise_id, language_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exerciseId);
            stmt.setInt(2, languageId);
            int rows = stmt.executeUpdate();
            System.out.println(" Relation insérée : exercice " + exerciseId + " ↔ langage " + languageId);
            return rows > 0;

        } catch (SQLException e) {
            System.err.println(" Erreur insertRelation : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère tous les langages associés à un exercice donné
     * @param exerciseId :  l'ID de l'exercice
     * @return :  liste des IDs de langages
     */
    public List<Integer> getByExerciseId(int exerciseId) {
        List<Integer> languageIds = new ArrayList<>();
        String sql = "SELECT language_id FROM ExerciseLanguage WHERE exercise_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exerciseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                languageIds.add(rs.getInt("language_id"));
            }

        } catch (SQLException e) {
            System.err.println(" Erreur getByExerciseId : " + e.getMessage());
        }

        return languageIds;
    }
}

