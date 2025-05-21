package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAO {

    private final Connection conn;

    // Constructeur par défaut (automatique)
    public LanguageDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de connexion dans LanguageDAO", e);
        }
    }

    // Constructeur personnalisé (facultatif)
    public LanguageDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertLanguage(String languageName) {
        String sql = "INSERT INTO Language (name) VALUES (?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, languageName);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur insertLanguage : " + e.getMessage());
            return false;
        }
    }

    public List<Language> getAllLanguages() {
        List<Language> list = new ArrayList<>();
        String sql = "SELECT * FROM Language";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Language lang = new Language();
                lang.setId(rs.getInt("id"));
                lang.setName(rs.getString("name"));
                list.add(lang);
            }

        } catch (SQLException e) {
            System.err.println("Erreur getAllLanguages : " + e.getMessage());
        }

        return list;
    }
}
