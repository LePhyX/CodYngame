package model;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MakeExerciseDAO {

    private final Connection conn;

    public MakeExerciseDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insert(MakeExercise me) {
        String sql = "INSERT INTO MakeExercise (user_id, exercise_id, success) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, me.getUserId());
            stmt.setInt(2, me.getExerciseId());
            stmt.setBoolean(3, me.isSuccess());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur insert MakeExercise : " + e.getMessage());
            return false;
        }
    }

    public List<MakeExercise> getByUser(int userId) {
        List<MakeExercise> list = new ArrayList<>();
        String sql = "SELECT * FROM MakeExercise WHERE user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MakeExercise me = new MakeExercise();
                me.setId(rs.getInt("id"));
                me.setUserId(rs.getInt("user_id"));
                me.setExerciseId(rs.getInt("exercise_id"));
                Timestamp ts = rs.getTimestamp("begins_the");
                if (ts != null) {
                    me.setBeginsThe(ts.toLocalDateTime());
                }
                me.setSuccess(rs.getBoolean("success"));
                list.add(me);
            }

        } catch (SQLException e) {
            System.err.println("Erreur getByUser MakeExercise : " + e.getMessage());
        }

        return list;
    }

    public boolean updateSuccess(int userId, int exerciseId, boolean success) {
        String sql = "UPDATE MakeExercise SET success = ? WHERE user_id = ? AND exercise_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, success);
            stmt.setInt(2, userId);
            stmt.setInt(3, exerciseId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur updateSuccess MakeExercise : " + e.getMessage());
            return false;
        }
    }



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


