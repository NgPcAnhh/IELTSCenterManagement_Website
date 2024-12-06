package dao;

import util.ConnecttoSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ADMINTrackingRegister {
    public List<Map<String, String>> getPendingRegistrations() {
        List<Map<String, String>> registrations = new ArrayList<>();
        String sql = "SELECT full_name, phone_number, date_birth, email, class_id, more_information FROM register WHERE id IS NULL";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("fullName", rs.getString("full_name"));
                row.put("phoneNumber", rs.getString("phone_number"));
                row.put("dateOfBirth", rs.getString("date_birth"));
                row.put("email", rs.getString("email"));
                row.put("classId", rs.getString("class_id"));
                row.put("moreInfo", rs.getString("more_information"));
                registrations.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registrations;
    }
}
