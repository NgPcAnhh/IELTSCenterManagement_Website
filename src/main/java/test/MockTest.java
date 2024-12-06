package test;


import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MockTest {

    public static void main(String[] args) {
        String studentID = "STU-K5-040"; // Bạn có thể thay đổi giá trị ID học sinh tại đây
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Kết nối tới cơ sở dữ liệu
            conn = ConnecttoSQL.getConnection();

            // Câu lệnh SQL để lấy thông tin dựa trên ID
            String sqlQuery = "SELECT [ID Test], ID, Time, Reading, Listening, Writing, Speaking, overall "
                    + "FROM mock_test WHERE ID = ?";

            // Chuẩn bị câu lệnh với PreparedStatement
            stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, studentID); // Đặt giá trị ID vào câu truy vấn

            // Thực thi truy vấn và lấy kết quả
            rs = stmt.executeQuery();

            // Duyệt qua kết quả và in ra thông tin
            while (rs.next()) {
                String idTest = rs.getString("ID Test");
                String id = rs.getString("ID");
                String time = rs.getString("Time");
                float reading = rs.getFloat("Reading");
                float listening = rs.getFloat("Listening");
                float writing = rs.getFloat("Writing");
                float speaking = rs.getFloat("Speaking");
                float overall = rs.getFloat("overall");

                System.out.println("ID Test: " + idTest);
                System.out.println("ID: " + id);
                System.out.println("Time: " + time);
                System.out.println("Reading: " + reading);
                System.out.println("Listening: " + listening);
                System.out.println("Writing: " + writing);
                System.out.println("Speaking: " + speaking);
                System.out.println("Overall: " + overall);
                System.out.println("----------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

