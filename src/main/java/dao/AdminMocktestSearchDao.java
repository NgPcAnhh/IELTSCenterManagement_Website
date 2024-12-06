package dao;

import util.ConnecttoSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminMocktestSearchDao {

    /**
     * Tìm kiếm mock tests dựa trên từ khóa.
     * @param keyword Từ khóa để tìm kiếm.
     * @return Danh sách các bản ghi phù hợp.
     */
    public List<Map<String, Object>> searchMocktests(String keyword) {
        List<Map<String, Object>> mocktests = new ArrayList<>();

        // Truy vấn SQL với 3 cột để tìm kiếm
        String query = "SELECT * FROM mock_test WHERE id LIKE ? OR id_test LIKE ? OR time LIKE ?";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Thêm wildcard "%" trước và sau từ khóa để tìm kiếm
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern); // Ánh xạ tham số cho cột "id"
            stmt.setString(2, searchPattern); // Ánh xạ tham số cho cột "id_test"
            stmt.setString(3, searchPattern); // Ánh xạ tham số cho cột "time"

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Tạo một bản ghi dưới dạng Map
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", rs.getString("id"));
                    row.put("idTest", rs.getString("id_test"));
                    row.put("time", rs.getString("time"));
                    row.put("reading", rs.getDouble("reading"));
                    row.put("listening", rs.getDouble("listening"));
                    row.put("writing", rs.getDouble("writing"));
                    row.put("speaking", rs.getDouble("speaking"));
                    row.put("overall", rs.getDouble("overall"));
                    row.put("feedback_r", rs.getString("feedback_r"));
                    row.put("feedback_l", rs.getString("feedback_l"));
                    row.put("feedback_w", rs.getString("feedback_w"));
                    row.put("feedback_s", rs.getString("feedback_s"));
                    mocktests.add(row); // Thêm bản ghi vào danh sách
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mocktests; // Trả về danh sách kết quả
    }
}
