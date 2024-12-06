package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdminINFPieChart {

    public JSONArray getCourseCounts() {
        String query = "SELECT ma_mon_hoc, SUM(price) AS revenue, COUNT(ma_mon_hoc) AS SL FROM bills GROUP BY ma_mon_hoc";
        JSONArray courseCounts = new JSONArray();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                JSONObject courseCount = new JSONObject();
                courseCount.put("ma_mon_hoc", rs.getString("ma_mon_hoc"));
                courseCount.put("revenue", rs.getInt("revenue"));
                courseCount.put("SL", rs.getInt("SL"));
                courseCounts.put(courseCount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseCounts;
    }
}
