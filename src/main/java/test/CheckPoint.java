package test;

import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckPoint {

    // Biến ID để nhập từ bên ngoài
    public static String ID = "";

    public static void main(String[] args) {
        ID = "STU-K5-003";  // Nhập ID
        checkMockTest();
    }

    public static void checkMockTest() {
        // Tạo câu truy vấn SQL
        String sql = "SELECT ID, Time, [ID Test], Reading, Listening, Speaking, Writing, overall FROM mock_test WHERE ID = ?";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Gán giá trị cho tham số ?
            pstmt.setString(1, ID);

            // Thực hiện truy vấn và lấy kết quả
            try (ResultSet rs = pstmt.executeQuery()) {
                // In kết quả
                while (rs.next()) {
                    String id = rs.getString("ID");
                    String time = rs.getString("Time");
                    String idTest = rs.getString("ID Test");
                    String reading = rs.getString("Reading");
                    String listening = rs.getString("Listening");
                    String speaking = rs.getString("Speaking");
                    String writing = rs.getString("Writing");
                    String overall = rs.getString("overall");

                    // In ra kết quả
                    System.out.println("ID: " + id);
                    System.out.println("Time: " + time);
                    System.out.println("ID Test: " + idTest);
                    System.out.println("Reading: " + reading);
                    System.out.println("Listening: " + listening);
                    System.out.println("Speaking: " + speaking);
                    System.out.println("Writing: " + writing);
                    System.out.println("Overall: " + overall);
                    System.out.println("---------------------------");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn: " + e.getMessage());
        }
    }
}
