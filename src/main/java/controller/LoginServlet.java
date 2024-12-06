package controller;

import dao.LoginSTU; // Bạn có thể đổi tên LoginSTU thành LoginDAO để phù hợp với tất cả các vai trò
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đọc dữ liệu JSON từ request
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        // Parse JSON
        JSONObject jsonRequest = new JSONObject(sb.toString());
        String loginName = jsonRequest.getString("loginName");
        String password = jsonRequest.getString("password");

        // Khởi tạo LoginDAO để kiểm tra thông tin đăng nhập
        LoginSTU loginDAO = new LoginSTU(); // Bạn có thể đổi thành LoginDAO để phù hợp với việc kiểm tra tất cả các vai trò
        String userId = loginDAO.checkLogin(loginName, password);

        // Chuẩn bị phản hồi JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            JSONObject jsonResponse = new JSONObject();

            if (userId != null) {
                // Nếu thông tin đăng nhập hợp lệ

                // Lấy thời gian hiện tại để cập nhật login
                String latestLoginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                // Cập nhật thời gian đăng nhập cuối cùng và ghi log vào bảng login_tracking
                loginDAO.updateLatestLogin(loginName, latestLoginTime);
                loginDAO.insertLoginTracking(userId, loginName, password, latestLoginTime);

                // Tạo session và lưu trữ thông tin userId
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setMaxInactiveInterval(15 * 60); // Session hết hạn sau 30 phút không hoạt động

                // Trả về phản hồi JSON thành công cùng với userId
                jsonResponse.put("success", true);
                jsonResponse.put("userId", userId);
                jsonResponse.put("message", "Đăng nhập thành công.");
            } else {
                // Nếu thông tin đăng nhập không hợp lệ, trả về JSON thất bại
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Tên đăng nhập hoặc mật khẩu không đúng.");
            }

            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
