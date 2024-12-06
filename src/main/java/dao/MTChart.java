package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class MTChart {

    // Phương thức lấy dữ liệu mock test theo studentId và sắp xếp theo thời gian tăng dần
    public JSONArray getMockTestsByStudentId(String studentId) {
        String sql = "SELECT id, overall, time FROM mock_test WHERE id = ? ORDER BY time ASC";

        JSONArray mockTests = new JSONArray();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Thiết lập tham số cho câu truy vấn
            stmt.setString(1, studentId);

            // Thực thi câu truy vấn
            ResultSet rs = stmt.executeQuery();

            // Duyệt qua các kết quả và thêm vào JSONArray
            while (rs.next()) {
                JSONObject mockTest = new JSONObject();
                mockTest.put("id", rs.getString("id"));
                mockTest.put("overall", rs.getDouble("overall"));
                mockTest.put("time", rs.getString("time"));

                mockTests.put(mockTest);
            }

        } catch (SQLException e) {
            // Xử lý lỗi và ghi thông báo lỗi vào JSONArray
            JSONObject error = new JSONObject();
            error.put("error", "Lỗi khi kết nối hoặc truy vấn: " + e.getMessage());
            mockTests.put(error);
        }

        // Trả về JSONArray chứa dữ liệu mock test
        return mockTests;
    }
}
