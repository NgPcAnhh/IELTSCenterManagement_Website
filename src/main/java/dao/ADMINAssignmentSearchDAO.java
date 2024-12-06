package dao;

import util.ConnecttoSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ADMINAssignmentSearchDAO {

    public List<Map<String, Object>> searchAssignments(String keyword) {
        List<Map<String, Object>> assignments = new ArrayList<>();
        String query = "SELECT * FROM assignment WHERE HW_name LIKE ? OR HW_ID LIKE ? OR student_id LIKE ? OR teacher_id LIKE ?";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Tạo mẫu tìm kiếm với ký tự `%` bao quanh từ khóa
            String searchPattern = "%" + keyword + "%";
            statement.setString(1, searchPattern); // Sử dụng cho HW_name
            statement.setString(2, searchPattern); // Sử dụng cho HW_ID
            statement.setString(3, searchPattern); // Sử dụng cho student_id
            statement.setString(4, searchPattern); // Sử dụng cho teacher_id
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("hw_id", resultSet.getString("hw_id"));
                    row.put("student_id", resultSet.getString("student_id"));
                    row.put("hw_name", resultSet.getString("hw_name"));
                    row.put("teacher_id", resultSet.getString("teacher_id"));
                    row.put("deadline", resultSet.getDate("deadline"));
                    row.put("checking", resultSet.getString("checking"));
                    row.put("submit_time", resultSet.getString("submit_time"));
                    assignments.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }
}
