package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Noti;

public class ADMINInsertNotiDao {
    public boolean insertNotification(Noti noti) {
        String sql = "INSERT INTO noti (id, noti_name, time, content, picture, writter) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, noti.getId());
            preparedStatement.setString(2, noti.getNotiName());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(noti.getTime().getTime()));
            preparedStatement.setString(4, noti.getContent());
            preparedStatement.setString(5, noti.getPicture());
            preparedStatement.setString(6, noti.getWritter());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
