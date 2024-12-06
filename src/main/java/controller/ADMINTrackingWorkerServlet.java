package controller;

import com.google.gson.Gson;
import dao.ADMINTrackingWorker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ADMINTrackingWorkerServlet")
public class ADMINTrackingWorkerServlet extends HttpServlet {
    private final ADMINTrackingWorker dao = new ADMINTrackingWorker();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Đảm bảo xử lý request UTF-8
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        try {
            if ("getAll".equals(action)) {
                List<Map<String, Object>> workers = dao.getAllWorkers();
                String json = new Gson().toJson(workers);
                response.getWriter().write(json);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Invalid action!\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Error retrieving workers data.\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Xử lý request UTF-8
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("update".equals(action)) {
            try (BufferedReader reader = request.getReader()) {
                // Đọc dữ liệu JSON từ request body
                Map<String, Object> data = new Gson().fromJson(reader, HashMap.class);

                System.out.println("Dữ liệu nhận được từ client: " + data); // Log dữ liệu nhận được

                // Gọi DAO để cập nhật
                boolean success = dao.updateWorker(data);
                if (success) {
                    response.getWriter().write("{\"message\": \"Worker updated successfully!\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"message\": \"Failed to update worker.\"}");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"message\": \"Error updating worker.\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid action!\"}");
        }
    }
}
