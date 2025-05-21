package model;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
