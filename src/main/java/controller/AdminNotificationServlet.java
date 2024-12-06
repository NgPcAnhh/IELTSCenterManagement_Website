package controller;

import dao.AdminNotificationDao;
import model.Noti;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdminNotification")
public class AdminNotificationServlet extends HttpServlet {
    private final AdminNotificationDao notificationDao = new AdminNotificationDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");

        // Lấy danh sách thông báo từ DAO
        List<Noti> notifications = notificationDao.getAllNotifications();

        // Tạo JSON thủ công và loại bỏ ký tự không hợp lệ
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");

        for (int i = 0; i < notifications.size(); i++) {
            Noti noti = notifications.get(i);
            jsonBuilder.append("{")
                    .append("\"id\":\"").append(escapeJson(noti.getId())).append("\",")
                    .append("\"notiName\":\"").append(escapeJson(noti.getNotiName())).append("\",")
                    .append("\"time\":\"").append(noti.getTime()).append("\",")
                    .append("\"content\":\"").append(escapeJson(noti.getContent())).append("\",")
                    .append("\"picture\":\"").append(escapeJson(noti.getPicture())).append("\",")
                    .append("\"writter\":\"").append(escapeJson(noti.getWritter())).append("\"")
                    .append("}");

            if (i < notifications.size() - 1) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("]");
        response.getWriter().write(jsonBuilder.toString());
    }

    // Hàm escape để xử lý các ký tự không hợp lệ
    private String escapeJson(String value) {
        if (value == null) {
            return ""; // Trả về chuỗi rỗng nếu giá trị null
        }

        return value.replace("\\", "\\\\")    // Escape backslash
                .replace("\"", "\\\"")   // Escape dấu ngoặc kép
                .replace("\b", "\\b")    // Escape ký tự backspace
                .replace("\f", "\\f")    // Escape ký tự form feed
                .replace("\n", "\\n")    // Escape newline
                .replace("\r", "\\r")    // Escape carriage return
                .replace("\t", "\\t");   // Escape tab
    }
}
