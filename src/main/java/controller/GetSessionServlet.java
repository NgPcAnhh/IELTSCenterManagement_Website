package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/getSession")
public class GetSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy session hiện tại (không tạo mới nếu chưa tồn tại)
        HttpSession session = request.getSession(false);

        // Chuẩn bị phản hồi JSON
        response.setContentType("application/json; charset=UTF-8");
        JSONObject jsonResponse = new JSONObject();

        if (session != null) {
            // Lấy userId từ session
            String userId = (String) session.getAttribute("userId");

            if (userId != null) {
                // Trả về userId trong phản hồi JSON
                jsonResponse.put("success", true);
                jsonResponse.put("userId", userId);
            } else {
                // Nếu session không chứa userId
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Session không hợp lệ hoặc đã hết hạn.");
            }
        } else {
            // Nếu không có session
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Không tìm thấy session.");
        }

        // Gửi phản hồi JSON
        response.getWriter().write(jsonResponse.toString());
    }
}
