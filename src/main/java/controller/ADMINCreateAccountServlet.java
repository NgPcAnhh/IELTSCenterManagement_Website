package controller;

import com.google.gson.Gson;
import dao.ADMINCreateAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ADMINCreateAccountServlet")
public class ADMINCreateAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt encoding cho request và response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // Đọc JSON từ body request
        StringBuilder jsonBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        }

        // Chuyển đổi JSON thành Map
        Gson gson = new Gson();
        Map<String, String> data = gson.fromJson(jsonBody.toString(), HashMap.class);

        // Gọi DAO để lưu dữ liệu
        ADMINCreateAccount dao = new ADMINCreateAccount();
        try {
            dao.saveAccountAndBills(data);
            response.getWriter().write("{\"message\": \"Dữ liệu đã được lưu thành công!\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi khi lưu dữ liệu: " + e.getMessage() + "\"}");
        }
    }
}
