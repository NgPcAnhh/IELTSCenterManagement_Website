package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

public class AssignmentQueries {

    // Truy vấn tên bài tập
    public JSONObject getHWName(String studentId, String hwId) {
        String query = "SELECT HW_name FROM assignment WHERE student_id = ? AND HW_ID = ?";
        JSONObject result = new JSONObject();
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, hwId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result.put("status", "success");
                result.put("HW_name", rs.getString("HW_name"));
            } else {
                result.put("status", "error");
                result.put("message", "Không tìm thấy tên bài tập.");
            }
        } catch (SQLException e) {
            result.put("status", "error");
            result.put("message", "Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        }
        return result;
    }

    // Truy vấn chi tiết bài tập
    public JSONObject getAssignmentDetails(String studentId, String hwId) {
        String query = "SELECT HW_name, deadline, checking, hand_on, submit_time FROM assignment WHERE student_id = ? AND HW_ID = ?";
        JSONObject result = new JSONObject();
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, hwId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result.put("status", "success");
                result.put("HW_name", rs.getString("HW_name"));
                result.put("deadline", rs.getString("deadline"));
                result.put("checking", rs.getString("checking"));
                result.put("hand_on", rs.getString("hand_on"));
                result.put("submit_time", rs.getString("submit_time"));
            } else {
                result.put("status", "error");
                result.put("message", "Không tìm thấy chi tiết bài tập.");
            }
        } catch (SQLException e) {
            result.put("status", "error");
            result.put("message", "Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        }
        return result;
    }

    // Truy vấn feedback
    public JSONObject getFeedback(String studentId, String hwId) {
        String query = "SELECT feedbacks FROM assignment WHERE student_id = ? AND HW_ID = ?";
        JSONObject result = new JSONObject();
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, hwId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result.put("status", "success");
                result.put("feedbacks", rs.getString("feedbacks"));
            } else {
                result.put("status", "error");
                result.put("message", "Không có feedback cho bài tập này.");
            }
        } catch (SQLException e) {
            result.put("status", "error");
            result.put("message", "Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        }
        return result;
    }

    // Truy vấn thông tin hand_on
    public JSONObject getHandOn(String studentId, String hwId) {
        String query = "SELECT hand_on FROM assignment WHERE student_id = ? AND HW_ID = ?";
        JSONObject result = new JSONObject();
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, hwId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result.put("status", "success");
                result.put("hand_on", rs.getString("hand_on"));
            } else {
                result.put("status", "error");
                result.put("message", "Không có dữ liệu hand-on cho bài tập này.");
            }
        } catch (SQLException e) {
            result.put("status", "error");
            result.put("message", "Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        }
        return result;
    }


        // Phương thức lấy tên giáo viên từ worker_tracking dựa trên HW_ID và student_id
    public JSONObject getTeacherName(String hwId, String studentId) {
        String query = "SELECT assignment.teacher_id, worker_tracking.name " +
                "FROM assignment " +
                "JOIN worker_tracking " +
                "ON worker_tracking.id COLLATE utf8mb4_general_ci = assignment.teacher_id COLLATE utf8mb4_general_ci " +
                "WHERE assignment.HW_ID COLLATE utf8mb4_general_ci = ? " +
                "AND assignment.student_id COLLATE utf8mb4_general_ci = ?";

        JSONObject result = new JSONObject();
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hwId);
            stmt.setString(2, studentId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result.put("status", "success");
                result.put("teacher_id", rs.getString("teacher_id"));
                result.put("teacher_name", rs.getString("name")); // Thêm tên giáo viên vào kết quả
            } else {
                result.put("status", "error");
                result.put("message", "Không tìm thấy tên giáo viên.");
            }
        } catch (SQLException e) {
            result.put("status", "error");
            result.put("message", "Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        }
        return result;
    }


}

