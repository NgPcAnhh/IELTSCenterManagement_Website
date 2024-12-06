package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class Assignment {

    // Phương thức lấy thông tin assignment của sinh viên theo studentId
    public JSONArray getAssignmentInformation(String studentId) {
        // Câu lệnh SQL để lấy dữ liệu assignment cho một student_id
        String sql = "SELECT HW_ID, HW_name, teacher_id, deadline, checking, feedbacks, hand_on, submit_time " +
                "FROM assignment " +
                "WHERE student_id = ?";

        // Tạo đối tượng JSONArray để lưu trữ kết quả
        JSONArray assignments = new JSONArray();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gán giá trị cho tham số student_id
            stmt.setString(1, studentId);

            // Thực thi truy vấn và xử lý kết quả
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Tạo đối tượng JSONObject cho từng assignment
                JSONObject assignmentData = new JSONObject();
                assignmentData.put("hwId", rs.getString("HW_ID"));
                assignmentData.put("hwName", rs.getString("HW_name"));
                assignmentData.put("teacherId", rs.getString("teacher_id"));
                assignmentData.put("deadline", rs.getString("deadline"));
                assignmentData.put("checking", rs.getString("checking"));
                assignmentData.put("feedbacks", rs.getString("feedbacks"));
                assignmentData.put("handOn", rs.getString("hand_on"));
                assignmentData.put("submitTime", rs.getString("submit_time"));

                // Thêm JSONObject vào JSONArray
                assignments.put(assignmentData);
            }
        } catch (SQLException e) {
            // Xử lý lỗi và thêm thông tin lỗi vào JSONArray
            JSONObject errorData = new JSONObject();
            errorData.put("error", "Lỗi khi kết nối hoặc truy vấn: " + e.getMessage());
            assignments.put(errorData);
        }

        // Trả về JSONArray chứa dữ liệu assignment
        return assignments;
    }
}
