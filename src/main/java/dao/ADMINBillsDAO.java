package dao;

import org.json.JSONArray;
import org.json.JSONObject;
import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ADMINBillsDAO {

    // Phương thức để lấy 50 bản ghi gần nhất từ bảng bills
    public JSONArray getLatestBills() {
        String query = "SELECT ma_mon_hoc AS subject_id, student_id, time, price FROM bills ORDER BY time DESC LIMIT 50";
        JSONArray billsList = new JSONArray();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                JSONObject latestbills = new JSONObject();
                latestbills.put("subject_id", rs.getString("subject_id"));
                latestbills.put("student_id", rs.getString("student_id"));
                latestbills.put("time", rs.getTimestamp("time"));
                latestbills.put("price", rs.getDouble("price"));
                billsList.put(latestbills);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log lỗi thay vì chỉ in ra console
        }
        return billsList;
    }
}
