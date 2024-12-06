package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ADMINASSLAYOUTDAO {

    public Map<String, Object> getAssignmentDetails(String assignmentId, String studentId) {
        Map<String, Object> assignmentData = new HashMap<>();
        String query = "SELECT hand_on, feedbacks FROM assignment WHERE HW_id = ? AND student_id = ?";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, assignmentId);
            stmt.setString(2, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                assignmentData.put("hand_on", rs.getString("hand_on"));
                assignmentData.put("feedbacks", rs.getString("feedbacks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignmentData;
    }

    public boolean insertFeedback(String assignmentId, String studentId, String feedback) {
        String query = "UPDATE assignment SET feedbacks = ? WHERE HW_id = ? AND student_id = ?";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, feedback);
            stmt.setString(2, assignmentId);
            stmt.setString(3, studentId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
