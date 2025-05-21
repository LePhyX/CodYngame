package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private final Connection conn;

    public AdminDAO(Connection conn) {
        this.conn = conn;
    }

    //  Créer un nouvel administrateur (après avoir créé son User)

    public boolean insertAdmin(int userId) {
        String sql = "INSERT INTO Admin (user_id) VALUES (?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println(" Erreur insertAdmin : " + e.getMessage());
            return false;
        }
    }

    //  Supprimer un administrateur
    public boolean deleteAdminById(int adminId) {
        String sql = "DELETE FROM Admin WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println(" Erreur deleteAdmin : " + e.getMessage());
            return false;
        }


    }

    // 🔹 Récupérer tous les administrateurs
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
                admin.setId(rs.getInt("user_id")); // hérité de User
                admins.add(admin);
            }

        } catch (SQLException e) {
            System.err.println(" Erreur getAllAdmins : " + e.getMessage());
        }

        return admins;
    }

    // Ajouter un exercice
    public boolean addExercise(Exercise exo) {
        ExerciseDAO exerciseDAO = new ExerciseDAO(conn);
        return exerciseDAO.insertExercise(exo);
    }

    // Modifier un exercice existant
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
            System.err.println("Erreur updateExercise : " + e.getMessage());
            return false;
        }
    }

    // Supprimer un exercice par ID
    public boolean deleteExercise(int id) {
        String sql = "DELETE FROM Exercise WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur deleteExercise : " + e.getMessage());
            return false;
        }
    }

    // Ajouter un langage
    public boolean addLanguage(Language lang) {
        LanguageDAO langDAO = new LanguageDAO(conn);
        return langDAO.insertLanguage(lang.getName());
    }





    // Supprimer un langage
    public boolean deleteLanguage(int languageId) {
        String sql = "DELETE FROM Language WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, languageId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur deleteLanguage : " + e.getMessage());
            return false;
        }
    }

}

