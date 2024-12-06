package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDeleteNotiDao {
    public boolean deleteNotification(String id) {
        String sql = "DELETE FROM noti WHERE id = ?";
        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            System.out.println("Số dòng đã xóa: " + rowsDeleted);

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
