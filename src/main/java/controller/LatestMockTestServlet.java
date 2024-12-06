package controller;

import dao.LatestMockTest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;

@WebServlet("/getLatestMockTest")
public class LatestMockTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        // Lấy session hiện tại và kiểm tra userId
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userId") != null) {
            String studentId = (String) session.getAttribute("userId");

            // Tạo đối tượng LatestMockTest để truy vấn thông tin mock test
            LatestMockTest latestMockTestDAO = new LatestMockTest();

            try {
                jsonResponse = latestMockTestDAO.getLatestMockTest(studentId);

                // Kiểm tra nếu không có dữ liệu mock test
                if (!jsonResponse.has("idTest")) {
                    jsonResponse.put("message", "Không có dữ liệu mock test.");
                }
            } catch (Exception e) {
                jsonResponse.put("error", "Lỗi khi lấy thông tin mock test: " + e.getMessage());
            }
        } else {
            jsonResponse.put("message", "Người dùng chưa đăng nhập.");
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
