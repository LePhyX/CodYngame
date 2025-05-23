package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserDAO {

    public boolean addUser(User user) {
        String sql = "INSERT INTO User (username, email, password_hash) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());

            int rowsInserted = stmt.executeUpdate();
            System.out.println(" Insertion réussie (" + rowsInserted + " ligne)");
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println(" Erreur SQL : " + e.getMessage());
            return false;
        }
    }

    // ✅ Méthode modifiée : renvoie un User complet s'il est trouvé
    public User checkLogin(String username, String passwordHash) {
        String sql = "SELECT * FROM User WHERE username = ? AND password_hash = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // ✅ Ajoute ces logs ici
            System.out.println("→ CHECK LOGIN: username = " + username + ", hash = " + passwordHash);

            stmt.setString(1, username);
            stmt.setString(2, passwordHash);

            ResultSet rs = stmt.executeQuery();

            // ✅ Et ici pour voir si un utilisateur est trouvé
            if (rs.next()) {
                System.out.println("✔ Utilisateur trouvé en BDD : id = " + rs.getInt("id"));
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getInt("score")
                );
            } else {
                System.out.println("❌ Aucun utilisateur trouvé avec ces identifiants.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean userExists(String username) {
        String sql = "SELECT id FROM User WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateUserScore(int userId, int points) throws SQLException {
        String sql = "UPDATE User SET score = score + ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, points);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    // ✅ Corrigé pour utiliser password_hash
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getInt("score")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
