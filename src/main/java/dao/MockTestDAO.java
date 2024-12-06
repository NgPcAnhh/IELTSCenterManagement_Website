package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class MockTestDAO {

    // Phương thức lấy dữ liệu mock test từ bảng mock_test dựa trên id (studentId)
    public JSONArray getMockTestsById(String studentId) {
        String query = "SELECT * FROM mock_test WHERE id = ?";
        JSONArray mockTests = new JSONArray();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject mockTest = new JSONObject();
                mockTest.put("id", rs.getString("id"));
                mockTest.put("idTest", rs.getString("id_test"));
                mockTest.put("time", rs.getString("time"));
                mockTest.put("reading", rs.getDouble("reading"));
                mockTest.put("listening", rs.getDouble("listening"));
                mockTest.put("writing", rs.getDouble("writing"));
                mockTest.put("speaking", rs.getDouble("speaking"));
                mockTest.put("overall", rs.getDouble("overall"));
                mockTest.put("feedback_r", rs.getString("feedback_r"));
                mockTest.put("feedback_l", rs.getString("feedback_l"));
                mockTest.put("feedback_w", rs.getString("feedback_w"));
                mockTest.put("feedback_s", rs.getString("feedback_s"));

                mockTests.put(mockTest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mockTests;
    }
}
