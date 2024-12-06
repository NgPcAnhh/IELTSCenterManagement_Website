package dao;

import model.Noti;
import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminUpdateNotiDao {
    public boolean updateNotification(Noti noti) {
        String sql = "UPDATE noti SET noti_name = ?, time = ?, content = ?, picture = ?, writter = ? WHERE id = ?";
        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, noti.getNotiName());
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(noti.getTime().getTime()));
            preparedStatement.setString(3, noti.getContent());
            preparedStatement.setString(4, noti.getPicture());
            preparedStatement.setString(5, noti.getWritter());
            preparedStatement.setString(6, noti.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
