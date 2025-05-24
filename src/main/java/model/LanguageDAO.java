package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing programming languages.
 * Provides methods to insert and retrieve language entries from the database.
 */
public class LanguageDAO {

    private final Connection conn;

    /**
     * Default constructor.
     * Uses the global database connection.
     */
    public LanguageDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Connection error in LanguageDAO", e);
        }
    }

    /**
     * Constructor with an existing connection.
     *
     * @param conn an active database connection
     */
    public LanguageDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new language into the database.
     *
     * @param languageName the name of the language (e.g., "Java", "Python")
     * @return true if the insertion succeeded, false otherwise
     */
    public boolean insertLanguage(String languageName) {
        String sql = "INSERT INTO Language (name) VALUES (?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, languageName);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error insertLanguage: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all programming languages from the database.
     *
     * @return a list of {@link Language} objects
     */
    public List<Language> getAllLanguages() {
        List<Language> list = new ArrayList<>();
        String sql = "SELECT * FROM Language";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // For each result, create a Language object and add to the list
            while (rs.next()) {
                Language lang = new Language();
                lang.setId(rs.getInt("id"));
                lang.setName(rs.getString("name"));
                list.add(lang);
            }

        } catch (SQLException e) {
            System.err.println("Error getAllLanguages: " + e.getMessage());
        }

        return list;
    }
}
