package controller;

import dao.Personalin4;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;

@WebServlet("/getPersonalInfo")
public class StudentInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        // Lấy session hiện tại
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");

            // Tạo đối tượng Personalin4 để truy vấn thông tin sinh viên
            Personalin4 personalIn4 = new Personalin4();

            try {
                jsonResponse = personalIn4.getPersonalInformation(userId);

                if (!jsonResponse.has("studentName")) {
                    jsonResponse.put("message", "Dữ liệu không tồn tại.");
                }
            } catch (Exception e) {
                jsonResponse.put("error", "Lỗi khi lấy thông tin: " + e.getMessage());
            }
        } else {
            jsonResponse.put("message", "Người dùng chưa đăng nhập.");
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
