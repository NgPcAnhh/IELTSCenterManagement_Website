package dao;

import model.NotLoginData;
import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ADMINNotLoginDao {

    public List<NotLoginData> getNotLoggedInStudents() {
        List<NotLoginData> notLoginList = new ArrayList<>();

        String sql = "SELECT " +
                "s.id AS student_id, " +
                "s.student_name, " +
                "s.phone_number AS student_phone, " +
                "s.parent_name, " +
                "s.parent_number AS parent_phone, " +
                "a.`latest-login` AS latest_login, " +
                "TIMESTAMPDIFF(MONTH, a.`latest-login`, NOW()) AS months_not_logged_in " +
                "FROM student s " +
                "LEFT JOIN ptit_web_project.account a ON s.id = a.id " +
                "WHERE a.`latest-login` < DATE_SUB(NOW(), INTERVAL 6 MONTH) " +
                "ORDER BY months_not_logged_in DESC;";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                NotLoginData data = new NotLoginData(
                        resultSet.getString("student_id"),
                        resultSet.getString("student_name"),
                        resultSet.getString("student_phone"),
                        resultSet.getString("parent_name"),
                        resultSet.getString("parent_phone"),
                        resultSet.getTimestamp("latest_login"),
                        resultSet.getInt("months_not_logged_in")
                );
                notLoginList.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notLoginList;
    }
}
