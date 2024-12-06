package dao;

import util.ConnecttoSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ADMINAssignmentsDAO {

    public List<Map<String, Object>> getAssignmentsByHWID(String hwIdPattern) {
        List<Map<String, Object>> assignments = new ArrayList<>();
        String query = "SELECT hw_id, student_id, hw_name, teacher_id, deadline, checking, submit_time " +
                "FROM assignment WHERE HW_ID LIKE ? ORDER BY deadline DESC";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, hwIdPattern);

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
