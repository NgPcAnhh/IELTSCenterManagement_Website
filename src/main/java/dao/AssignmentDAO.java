package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class AssignmentDAO {
    // Phương thức để lấy tất cả các assignment cho một sinh viên
    public JSONArray getAllAssignments(String studentId) {
        String sql = "SELECT HW_ID, HW_name, teacher_id, deadline, checking " +
                "FROM assignment " +
                "WHERE student_id = ?";

        JSONArray assignments = new JSONArray();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject assignmentData = new JSONObject();
                assignmentData.put("hwId", rs.getString("HW_ID"));
                assignmentData.put("hwName", rs.getString("HW_name"));
                assignmentData.put("teacherId", rs.getString("teacher_id"));
                assignmentData.put("deadline", rs.getString("deadline"));
                assignmentData.put("checking", rs.getString("checking"));
                assignments.put(assignmentData);
            }
        } catch (SQLException e) {
            JSONObject error = new JSONObject();
            error.put("error", "Lỗi khi kết nối hoặc truy vấn: " + e.getMessage());
            assignments.put(error);
        }

        return assignments;
    }

    // Phương thức để lấy các assignment đã hoàn thành (có checking) cho một sinh viên
    public JSONArray getCompletedAssignments(String studentId) {
        String sql = "SELECT HW_ID, HW_name, teacher_id, deadline, checking " +
                "FROM assignment " +
                "WHERE student_id = ? AND checking IS NOT NULL";

        JSONArray completedAssignments = new JSONArray();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject assignmentData = new JSONObject();
                assignmentData.put("hwId", rs.getString("HW_ID"));
                assignmentData.put("hwName", rs.getString("HW_name"));
                assignmentData.put("teacherId", rs.getString("teacher_id"));
                assignmentData.put("deadline", rs.getString("deadline"));
                assignmentData.put("checking", rs.getString("checking"));
                completedAssignments.put(assignmentData);
            }
        } catch (SQLException e) {
            JSONObject error = new JSONObject();
            error.put("error", "Lỗi khi kết nối hoặc truy vấn: " + e.getMessage());
            completedAssignments.put(error);
        }

        return completedAssignments;
    }
}
