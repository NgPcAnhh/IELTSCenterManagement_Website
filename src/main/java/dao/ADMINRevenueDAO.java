package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class ADMINRevenueDAO {

    public JSONArray getMonthlyRevenue() {
        String query = "SELECT YEAR(time) AS year, MONTH(time) AS month, SUM(price) AS total_price " +
                "FROM bills " +
                "WHERE time BETWEEN '2020-01-01' AND '2024-11-30' " +
                "GROUP BY YEAR(time), MONTH(time) " +
                "ORDER BY year, month";

        JSONArray revenueData = new JSONArray();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                JSONObject record = new JSONObject();
                record.put("year", rs.getInt("year"));
                record.put("month", rs.getInt("month"));
                record.put("total_price", rs.getDouble("total_price"));
                revenueData.put(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenueData;
    }
}
