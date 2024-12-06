package dao;

import model.AdminTopOverall;
import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminTop10OverallDao {

    public List<AdminTopOverall> getTop10Overall() {
        List<AdminTopOverall> topOverallList = new ArrayList<>();

        String sql = "SELECT " +
                "s.id AS student_id, " +
                "s.student_name, " +
                "s.phone_number, " +
                "s.gmail, " +
                "ROUND(AVG(m.overall), 2) AS average_overall, " +
                "COUNT(m.id_test) AS mock_test_count " +
                "FROM student s " +
                "JOIN mock_test m ON s.id = m.id " +
                "GROUP BY s.id, s.student_name, s.phone_number, s.gmail " +
                "ORDER BY average_overall DESC, mock_test_count ASC " +
                "LIMIT 10;";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                AdminTopOverall topOverall = new AdminTopOverall(
                        resultSet.getString("student_id"),
                        resultSet.getString("student_name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("gmail"),
                        resultSet.getFloat("average_overall"),
                        resultSet.getInt("mock_test_count")
                );
                topOverallList.add(topOverall);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return topOverallList;
    }
}
