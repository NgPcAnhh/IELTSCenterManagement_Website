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

public class ADMINTrackingWorker {

    // Get all workers
    public List<Map<String, Object>> getAllWorkers() throws SQLException {
        List<Map<String, Object>> workers = new ArrayList<>();
        String sql = "SELECT * FROM worker_tracking";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> worker = new HashMap<>();
                worker.put("id", rs.getString("id"));
                worker.put("name", rs.getString("name"));
                worker.put("role", rs.getString("role"));
                worker.put("phone_number", rs.getString("phone_number"));
                worker.put("email", rs.getString("email"));
                worker.put("wage", rs.getDouble("wage"));
                worker.put("attendance_tracking", rs.getString("attendance_tracking"));
                workers.add(worker);
            }
        }
        return workers;
    }

    // Update worker
    public boolean updateWorker(Map<String, Object> data) throws SQLException {
        String sql = "UPDATE worker_tracking SET name = ?, role = ?, phone_number = ?, email = ?, wage = ?, attendance_tracking = ? WHERE id = ?";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, (String) data.get("name"));
            ps.setString(2, (String) data.get("role"));
            ps.setString(3, (String) data.get("phone_number"));
            ps.setString(4, (String) data.get("email"));
            ps.setDouble(5, Double.parseDouble(data.get("wage").toString()));
            ps.setString(6, (String) data.get("attendance_tracking"));
            ps.setString(7, (String) data.get("id")); // Đảm bảo id là chuỗi nếu id trong DB là VARCHAR

            System.out.println("Câu lệnh SQL: " + ps.toString()); // Log câu lệnh SQL
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Log số hàng bị ảnh hưởng
            return rowsAffected > 0;
        }
    }

}
