// src/dao/BillDAO.java
package dao;

import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillDAO {

    public List<Map<String, Object>> getBillSummaryByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> summaries = new ArrayList<>();
        String query = "SELECT ma_mon_hoc, price, SUM(price) AS total_price, COUNT(ma_mon_hoc) AS SL, " +
                "ROUND((COUNT(ma_mon_hoc) * 100.0) / (SELECT COUNT(*) FROM bills), 2) AS percentage " +
                "FROM bills WHERE time BETWEEN ? AND ? GROUP BY ma_mon_hoc, price";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, startDate.atStartOfDay().toString());
            statement.setString(2, endDate.atTime(23, 59, 59).toString());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> summary = new HashMap<>();
                summary.put("subjectId", resultSet.getString("ma_mon_hoc"));
                summary.put("price", resultSet.getDouble("price"));
                summary.put("totalPrice", resultSet.getDouble("total_price"));
                summary.put("quantity", resultSet.getInt("SL"));
                summary.put("percentage", resultSet.getDouble("percentage"));
                summaries.add(summary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summaries;
    }
}
