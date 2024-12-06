package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

public class LatestMockTest {

    public JSONObject getLatestMockTest(String studentId) {
        String sql = "SELECT id, id_test, time, reading, listening, writing, speaking, overall FROM mock_test WHERE id = ? ORDER BY time DESC LIMIT 1;";

        JSONObject mockTestData = new JSONObject();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mockTestData.put("idTest", rs.getString("id_test"));
                mockTestData.put("time", rs.getString("time"));
                mockTestData.put("reading", rs.getDouble("reading"));
                mockTestData.put("listening", rs.getDouble("listening"));
                mockTestData.put("writing", rs.getDouble("writing"));
                mockTestData.put("speaking", rs.getDouble("speaking"));
                mockTestData.put("overall", rs.getDouble("overall"));
            } else {
                mockTestData.put("message", "Không có dữ liệu mock test.");
            }

        } catch (SQLException e) {
            mockTestData.put("error", "Lỗi khi kết nối hoặc truy vấn: " + e.getMessage());
        }

        return mockTestData;
    }
}
