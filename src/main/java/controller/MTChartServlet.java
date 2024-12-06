package controller;

import dao.MTChart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/getMockTests")
public class MTChartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONArray jsonResponse = new JSONArray();

        // Lấy session hiện tại và kiểm tra userId
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userId") != null) {
            String studentId = (String) session.getAttribute("userId");

            // Tạo đối tượng MockTestDAO để truy vấn thông tin mock test
            MTChart mockTestDAO = new MTChart();

            try {
                jsonResponse = mockTestDAO.getMockTestsByStudentId(studentId);

                // Kiểm tra nếu không có dữ liệu mock test
                if (jsonResponse.isEmpty()) {
                    JSONObject message = new JSONObject();
                    message.put("message", "Không có dữ liệu mock test.");
                    jsonResponse.put(message);
                }
            } catch (Exception e) {
                JSONObject error = new JSONObject();
                error.put("error", "Lỗi khi lấy thông tin mock test: " + e.getMessage());
                jsonResponse.put(error);
            }
        } else {
            JSONObject message = new JSONObject();
            message.put("message", "Người dùng chưa đăng nhập.");
            jsonResponse.put(message);
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
