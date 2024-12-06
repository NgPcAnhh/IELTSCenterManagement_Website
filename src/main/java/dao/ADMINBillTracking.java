package dao;

import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ADMINBillTracking {
    public List<Map<String, Object>> getBillsByStudentId(String studentIdPattern) {
        List<Map<String, Object>> billsList = new ArrayList<>();
        String query = "SELECT ma_mon_hoc, student_id, time, price FROM bills WHERE student_id LIKE ? ORDER BY time DESC LIMIT 100";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, studentIdPattern);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> bill = new HashMap<>();
                // Use correct column names from the SQL query
                bill.put("mamonhoc", resultSet.getString("ma_mon_hoc")); // Column: ma_mon_hoc (varchar(10))
                bill.put("studentid", resultSet.getString("student_id")); // Column: student_id (varchar(10))
                bill.put("time", resultSet.getTimestamp("time")); // Column: time (datetime)
                bill.put("price", resultSet.getInt("price")); // Column: price (int)

                billsList.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return billsList;
    }
}
