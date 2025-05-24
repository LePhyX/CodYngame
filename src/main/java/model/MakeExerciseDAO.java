package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for the MakeExercise table.
 * Handles user-attempt tracking for exercises including insertion, update and query.
 */
public class MakeExerciseDAO {

    private final Connection conn;

    /**
     * Constructor with active database connection.
     *
     * @param conn the database connection
     */
    public MakeExerciseDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new MakeExercise record into the database.
     *
     * @param me the MakeExercise instance to insert
     * @return true if insertion was successful, false otherwise
     */
    public boolean insert(MakeExercise me) {
        String sql = "INSERT INTO MakeExercise (user_id, exercise_id, success) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, me.getUserId());
            stmt.setInt(2, me.getExerciseId());
            stmt.setBoolean(3, me.isSuccess());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting MakeExercise: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all MakeExercise entries for a given user.
     *
     * @param userId the user ID
     * @return a list of MakeExercise records
     */
    public List<MakeExercise> getByUser(int userId) {
        List<MakeExercise> list = new ArrayList<>();
        String sql = "SELECT * FROM MakeExercise WHERE user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            // Loop through result set and map to objects
            while (rs.next()) {
                MakeExercise me = new MakeExercise();
                me.setId(rs.getInt("id"));
                me.setUserId(rs.getInt("user_id"));
                me.setExerciseId(rs.getInt("exercise_id"));

                // Convert timestamp to LocalDateTime if not null
                Timestamp ts = rs.getTimestamp("begins_the");
                if (ts != null) {
                    me.setBeginsThe(ts.toLocalDateTime());
                }

                me.setSuccess(rs.getBoolean("success"));
                list.add(me);
            }

        } catch (SQLException e) {
            System.err.println("Error getByUser MakeExercise: " + e.getMessage());
        }

        return list;
    }

    /**
     * Updates the success flag for a given user and exercise.
     *
     * @param userId     the user ID
     * @param exerciseId the exercise ID
     * @param success    the success value to set
     * @return true if the update succeeded, false otherwise
     */
    public boolean updateSuccess(int userId, int exerciseId, boolean success) {
        String sql = "UPDATE MakeExercise SET success = ? WHERE user_id = ? AND exercise_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, success);
            stmt.setInt(2, userId);
            stmt.setInt(3, exerciseId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updateSuccess MakeExercise: " + e.getMessage());
            return false;
        }
    }

    /**
     * Counts the number of successful exercise attempts by a user.
     *
     * @param userId the user ID
     * @return number of successes
     */
    public int countSuccess(int userId) {
        String sql = "SELECT COUNT(*) FROM MakeExercise WHERE user_id = ? AND success = true";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Counts the number of failed exercise attempts by a user.
     *
     * @param userId the user ID
     * @return number of failures
     */
    public int countFail(int userId) {
        String sql = "SELECT COUNT(*) FROM MakeExercise WHERE user_id = ? AND success = false";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
