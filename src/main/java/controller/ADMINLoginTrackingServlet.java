package controller;

import com.google.gson.Gson;
import dao.ADMINLoginTracking;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/ADMINLoginTrackingServlet")
public class ADMINLoginTrackingServlet extends HttpServlet {
    private final ADMINLoginTracking dao = new ADMINLoginTracking();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if ("default".equals(action)) {
                // Lấy 10 bản ghi mới nhất
                List<Map<String, Object>> records = dao.getDefaultTracking();
                response.getWriter().write(new Gson().toJson(records));
            } else if ("search".equals(action)) {
                String id = request.getParameter("id");
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");

                // Log tham số để kiểm tra
                System.out.println("ID: " + id);
                System.out.println("Start Time: " + startTime);
                System.out.println("End Time: " + endTime);

                if ((startTime != null && endTime == null) || (startTime == null && endTime != null)) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"message\": \"Start Time và End Time phải được nhập cùng nhau.\"}");
                    return;
                }

                // Truy vấn DAO
                List<Map<String, Object>> records = dao.searchTracking(
                        (id != null && !id.trim().isEmpty()) ? id : null,
                        (startTime != null && !startTime.trim().isEmpty()) ? startTime : null,
                        (endTime != null && !endTime.trim().isEmpty()) ? endTime : null
                );

                response.getWriter().write(new Gson().toJson(records));
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Invalid action!\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Error fetching login tracking data.\"}");
        }
    }
}

