package dao;

import model.Noti;
import util.ConnecttoSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminNotificationDao {
    // Hàm lấy tất cả thông báo từ database
    public List<Noti> getAllNotifications() {
        List<Noti> notifications = new ArrayList<>();
        String query = "SELECT noti.id, noti_name, time, content, picture, worker_tracking.name \n" +
                "FROM noti\n" +
                "JOIN worker_tracking ON noti.writter = worker_tracking.id";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Noti noti = new Noti();
                noti.setId(rs.getString("id"));
                noti.setNotiName(rs.getString("noti_name"));
                noti.setTime(rs.getTimestamp("time"));
                noti.setContent(rs.getString("content"));
                noti.setPicture(rs.getString("picture"));
                noti.setWritter(rs.getString("name")); // Thay "writter" bằng "name"

                notifications.add(noti);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }


}
