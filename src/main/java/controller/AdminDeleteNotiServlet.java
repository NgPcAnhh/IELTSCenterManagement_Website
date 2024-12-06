package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.AdminDeleteNotiDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/AdminDeleteNoti")
public class AdminDeleteNotiServlet extends HttpServlet {
    private final AdminDeleteNotiDao dao = new AdminDeleteNotiDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // Đọc dữ liệu JSON từ body của request
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        String jsonData = jsonBuffer.toString();
        System.out.println("Dữ liệu JSON nhận được: " + jsonData);

        // Phân tích JSON để lấy ID
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        String id = jsonObject.get("id").getAsString();
        System.out.println("ID cần xóa: " + id);

        // Gọi DAO để xóa thông báo
        boolean result = dao.deleteNotification(id);

        // Gửi phản hồi JSON
        response.getWriter().write(gson.toJson(result));
    }
}
