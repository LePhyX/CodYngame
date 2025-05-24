package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing Admin entities and their administrative operations.
 * This class provides methods to create, read, update, and delete admins, exercises, and languages.
 */
public class AdminDAO {

    private final Connection conn;

    /**
     * Constructs the AdminDAO with a database connection.
     *
     * @param conn the database connection to use
     */
    public AdminDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new admin linked to an existing user.
     *
     * @param userId the ID of the user to promote to admin
     * @return true if the insertion was successful, false otherwise
     */
    public boolean insertAdmin(int userId) {
        String sql = "INSERT INTO Admin (user_id) VALUES (?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error insertAdmin: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes an admin by their admin ID.
     *
     * @param adminId the admin ID to delete
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteAdminById(int adminId) {
        String sql = "DELETE FROM Admin WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleteAdmin: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all admins from the database.
     *
     * @return a list of Admin objects
     */
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();

        String sql = """
            SELECT a.id AS admin_id, u.id AS user_id, u.username, u.email, u.password_hash
            FROM Admin a
            INNER JOIN User u ON a.user_id = u.id
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Admin admin = new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash")
                );
                admin.setId(rs.getInt("user_id")); // inherited from User
                admins.add(admin);
            }

        } catch (SQLException e) {
            System.err.println("Error getAllAdmins: " + e.getMessage());
        }

        return admins;
    }

    /**
     * Adds a new exercise using ExerciseDAO.
     *
     * @param exo the exercise to add
     * @return true if the insertion was successful, false otherwise
     */
    public boolean addExercise(Exercise exo) {
        ExerciseDAO exerciseDAO = new ExerciseDAO(conn);
        return exerciseDAO.insertExercise(exo);
    }

    /**
     * Updates an existing exercise.
     *
     * @param exo the updated exercise object
     * @return true if the update was successful, false otherwise
     */
    public boolean updateExercise(Exercise exo) {
        String sql = "UPDATE Exercise SET title = ?, description = ?, type = ?, exercise_image = ?, solution_image = ?, solution = ?, difficulty = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, exo.getTitle());
            stmt.setString(2, exo.getDescription());
            stmt.setString(3, exo.getType());
            stmt.setBytes(4, exo.getExercisePdf());
            stmt.setBytes(5, exo.getSolutionPdf());
            stmt.setString(6, exo.getSolution());
            stmt.setInt(7, exo.getDifficulty());
            stmt.setInt(8, exo.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updateExercise: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes an exercise by its ID.
     *
     * @param id the ID of the exercise to delete
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteExercise(int id) {
        String sql = "DELETE FROM Exercise WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleteExercise: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds a new programming language using LanguageDAO.
     *
     * @param lang the Language object
     * @return true if the insertion was successful, false otherwise
     */
    public boolean addLanguage(Language lang) {
        LanguageDAO langDAO = new LanguageDAO(conn);
        return langDAO.insertLanguage(lang.getName());
    }

    /**
     * Deletes a programming language by its ID.
     *
     * @param languageId the ID of the language to delete
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteLanguage(int languageId) {
        String sql = "DELETE FROM Language WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, languageId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleteLanguage: " + e.getMessage());
            return false;
        }
    }
}
