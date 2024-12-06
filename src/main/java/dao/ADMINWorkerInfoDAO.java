package dao;

import util.ConnecttoSQL;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ADMINWorkerInfoDAO {

    // Method to get worker details by ID
    public JSONObject getWorkerInfoById(String workerId) {
        String query = "SELECT worker_tracking.id, worker_tracking.name, worker_tracking.role, worker_tracking.phone_number, worker_tracking.email\n" +
                "FROM worker_tracking \n" +
                "JOIN account ON worker_tracking.id = account.id\n" +
                "WHERE worker_tracking.id = ?;";
        JSONObject worker = new JSONObject();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, workerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                worker.put("id", rs.getString("id"));
                worker.put("name", rs.getString("name"));
                worker.put("role", rs.getString("role"));
                worker.put("phone_number", rs.getString("phone_number"));
                worker.put("email", rs.getString("email"));
            } else {
                worker.put("error", "No data found for this ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            worker.put("error", "Database error: " + e.getMessage());
        }
        return worker;
    }
}
