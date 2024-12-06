package dao;

import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ADMINLoginTracking {

    // Lấy 10 bản ghi mới nhất
    public List<Map<String, Object>> getDefaultTracking() throws SQLException {
        List<Map<String, Object>> records = new ArrayList<>();
        String sql = "SELECT login_name, time_in, time_out, id FROM login_tracking ORDER BY time_in DESC LIMIT 10";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> record = new HashMap<>();
                record.put("login_name", rs.getString("login_name"));
                record.put("time_in", rs.getTimestamp("time_in"));
                record.put("time_out", rs.getTimestamp("time_out"));
                record.put("id", rs.getString("id"));
                records.add(record);
            }
        }
        return records;
    }

    // Tìm kiếm theo ID hoặc khoảng thời gian
    public List<Map<String, Object>> searchTracking(String id, String startTime, String endTime) throws SQLException {
        List<Map<String, Object>> records = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT login_name, time_in, time_out, id FROM login_tracking WHERE 1=1");

        if (id != null && !id.isEmpty()) {
            sql.append(" AND id = ?");
        }
        if (startTime != null && endTime != null) {
            sql.append(" AND time_in BETWEEN ? AND ?");
        }

        sql.append(" ORDER BY time_in DESC");

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (id != null && !id.isEmpty()) {
                ps.setString(paramIndex++, id);
            }
            if (startTime != null && endTime != null) {
                ps.setTimestamp(paramIndex++, java.sql.Timestamp.valueOf(startTime));
                ps.setTimestamp(paramIndex++, java.sql.Timestamp.valueOf(endTime));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> record = new HashMap<>();
                    record.put("login_name", rs.getString("login_name"));
                    record.put("time_in", rs.getTimestamp("time_in"));
                    record.put("time_out", rs.getTimestamp("time_out"));
                    record.put("id", rs.getString("id"));
                    records.add(record);
                }
            }
        }
        return records;
    }

}
