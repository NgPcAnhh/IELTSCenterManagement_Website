package dao;

import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ADMINStudentUpdate {
    public boolean updateStudent(
            String studentName,
            String dateBirth,
            String phoneNumber,
            String gmail,
            String parentName,
            String parentNumber,
            String maMon,
            String maMon1,
            String maMon2,
            String ss1,
            String id) {

        String query = "UPDATE student SET " +
                "student_name = ?, " +
                "date_birth = ?, " +
                "phone_number = ?, " +
                "gmail = ?, " +
                "parent_name = ?, " +
                "parent_number = ?, " +
                "ma_mon = ?, " +
                "ma_mon_1 = ?, " +
                "ma_mon_2 = ?, " +
                "ss1 = ? " +
                "WHERE id = ?";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, studentName);
            preparedStatement.setString(2, dateBirth);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setString(4, gmail);
            preparedStatement.setString(5, parentName);
            preparedStatement.setString(6, parentNumber);
            preparedStatement.setString(7, maMon);
            preparedStatement.setString(8, maMon1);
            preparedStatement.setString(9, maMon2);
            preparedStatement.setString(10, ss1);
            preparedStatement.setString(11, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
