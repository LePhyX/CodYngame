package model;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;
import utils.*;


/**
 * DAO pour insérer une soumission dans la table Submission
 */
public class SubmissionDAO {

    private final Connection conn;

    // Constructeur : on récupère la connexion à la base
    public SubmissionDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Insère une nouvelle soumission dans la BDD
     *
     * @param submission :  Objet Submission à enregistrer
     * @return true : si insertion OK, false sinon
     */
    public boolean insertSubmission(Submission submission) {
        String sql = "INSERT INTO Submission " +
                "(user_id, exercise_id, language_id, code, result, success) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, submission.getUserId());
            stmt.setInt(2, submission.getExerciseId());
            stmt.setInt(3, submission.getLanguageId());
            stmt.setString(4, submission.getCode());
            stmt.setString(5, submission.getResult());
            stmt.setBoolean(6, submission.isSuccess());

            int rows = stmt.executeUpdate();
            System.out.println(" Submission insérée, lignes ajoutées : " + rows);
            return rows > 0; //On execute l'insert et si rows > , c'est que ca a marché

        } catch (SQLException e) {
            System.err.println(" Erreur d’insertion de soumission : " + e.getMessage());
            return false;
        }
    }

    public int countByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM Submission WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Dans model/SubmissionDAO.java
    public Map<String,Integer> countByUserGroupedByExercise(int userId) {
        String sql =
                "SELECT e.title, COUNT(s.id) AS cnt " +
                        "FROM Submission s " +
                        "JOIN Exercise e ON s.exercise_id = e.id " +
                        "WHERE s.user_id = ? " +
                        "GROUP BY e.title";
        Map<String,Integer> result = new LinkedHashMap<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("title"), rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Retourne pour chaque exercice le nombre total de soumissions,
     * le nombre de réussites et l'ID de la première soumission réussie.
     */
    public List<SubmissionStats> getStatsByUserGroupedByExercise(int userId) throws SQLException {
        String sql = """
        SELECT
          e.title AS exercise_title,
          COUNT(s.id) AS total_count,
          SUM(CASE WHEN LOWER(TRIM(s.result)) = 'fonction correcte !' THEN 1 ELSE 0 END) AS success_count,
          MIN(CASE WHEN LOWER(TRIM(s.result)) = 'fonction correcte !' THEN rn ELSE NULL END) AS first_success_attempt
        FROM (
          SELECT
            id,
            exercise_id,
            result,
            created_at,
            ROW_NUMBER() OVER (
              PARTITION BY exercise_id
              ORDER BY created_at
            ) AS rn
          FROM Submission
          WHERE user_id = ?
        ) s
        JOIN Exercise e ON s.exercise_id = e.id
        GROUP BY e.title
        ORDER BY e.title
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            List<SubmissionStats> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new SubmissionStats(
                        rs.getString("exercise_title"),
                        rs.getInt("total_count"),
                        rs.getInt("success_count"),
                        rs.getInt("first_success_attempt")
                ));
            }
            return list;
        }
    }
    public int getScoreByUser(int userId) {
        String sql = """
        SELECT COUNT(*) * 1 AS score
        FROM Submission
        WHERE user_id = ? AND LOWER(TRIM(result)) = 'fonction correcte !'
    """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("score") : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }







}
