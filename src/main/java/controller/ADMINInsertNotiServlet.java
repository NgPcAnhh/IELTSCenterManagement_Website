package controller;

import com.google.gson.Gson;
import dao.ADMINInsertNotiDao;
import model.Noti;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

@WebServlet("/AdminInsertNoti")
public class ADMINInsertNotiServlet extends HttpServlet {
    private final ADMINInsertNotiDao dao = new ADMINInsertNotiDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Đọc dữ liệu JSON từ request
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        String jsonData = jsonBuffer.toString();
        Gson gson = new Gson();
        Noti noti = gson.fromJson(jsonData, Noti.class);

        // Gán thời gian hiện tại cho thông báo
        noti.setTime(new Date());

        boolean isInserted = dao.insertNotification(noti);

        // Trả kết quả về client
        response.getWriter().write(gson.toJson(isInserted));
    }
}
