package test;

import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class assignment {

    public static void main(String[] args) {
        // Tạo biến chứa mã sinh viên
        String studentId = "STU-K5-003"; // Gán trực tiếp giá trị ID sinh viên

        // SQL query để lấy dữ liệu assignment cho student_id
        String sql = "SELECT * \n" +
                "FROM assignment\n" +
                "WHERE student_id = ?;";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gán giá trị cho tham số student_id
            stmt.setString(1, studentId);

            // Thực thi truy vấn
            ResultSet rs = stmt.executeQuery();

            // Xử lý kết quả
            while (rs.next()) {
                String hwId = rs.getString("HW_ID");
                String hwName = rs.getString("HW_name");
                String teacherId = rs.getString("teacher_id");
                String deadline = rs.getString("deadline");
                String checking = rs.getString("checking");
                String feedbacks = rs.getString("feedbacks");
                String handOn = rs.getString("hand_on");
                String submitTime = rs.getString("submit_time");

                // Hiển thị dữ liệu
                System.out.println("HW_ID: " + hwId);
                System.out.println("HW_name: " + hwName);
                System.out.println("Teacher ID: " + teacherId);
                System.out.println("Deadline: " + deadline);
                System.out.println("Checking: " + checking);
                System.out.println("Feedbacks: " + feedbacks);
                System.out.println("Hand-on: " + handOn);
                System.out.println("Submit Time: " + submitTime);
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

