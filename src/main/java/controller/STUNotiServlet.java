package controller;

import com.google.gson.Gson;
import util.ConnecttoSQL;
import model.STUNoti;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/STUNoti")
public class STUNotiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        List<STUNoti> notifications = new ArrayList<>();
        String sql = "SELECT noti.id, noti.noti_name, noti.time, noti.content, noti.picture, worker_tracking.name " +
                "FROM noti " +
                "JOIN worker_tracking ON worker_tracking.id = noti.writter " +
                "ORDER BY time DESC";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                STUNoti noti = new STUNoti();
                noti.setId(rs.getString("id"));
                noti.setNotiName(rs.getString("noti_name"));
                noti.setTime(rs.getTimestamp("time"));
                String content = rs.getString("content");

                // Xử lý xuống dòng với 2 dấu cách
                if (content != null) {
                    content = content.replace("  ", "\n"); // Thay 2 dấu cách bằng ký tự xuống dòng
                }
                noti.setContent(content);
                noti.setPicture(rs.getString("picture"));
                noti.setWriterName(rs.getString("name"));
                notifications.add(noti);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the list to JSON and send it as the response
        String json = new Gson().toJson(notifications);
        response.getWriter().write(json);
    }
}
