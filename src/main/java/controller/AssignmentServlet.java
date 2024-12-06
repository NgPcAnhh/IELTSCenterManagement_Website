package controller;

import dao.Assignment;
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

@WebServlet("/getAssignmentInfo")
public class AssignmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONArray jsonResponse = new JSONArray();

        // Lấy session hiện tại
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");

            // Tạo đối tượng Assignment để truy vấn thông tin assignment
            Assignment assignmentDao = new Assignment();

            try {
                jsonResponse = assignmentDao.getAssignmentInformation(userId);

                // Kiểm tra nếu không có dữ liệu assignment
                if (jsonResponse.isEmpty()) {
                    JSONObject message = new JSONObject();
                    message.put("message", "Không có dữ liệu assignment.");
                    jsonResponse.put(message);
                }
            } catch (Exception e) {
                JSONObject error = new JSONObject();
                error.put("error", "Lỗi khi lấy thông tin assignment: " + e.getMessage());
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
