package controller;

import dao.AssignmentQueries;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getTeacherName")
public class TeacherNameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt mã hóa UTF-8 cho request và response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        HttpSession session = request.getSession(false);
        String studentId = (String) session.getAttribute("userId");
        String hwId = request.getParameter("hwId");

        // Kiểm tra nếu session hoặc hwId chưa có
        if (session == null || studentId == null || hwId == null) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Yêu cầu không hợp lệ.");
            response.getWriter().print(errorResponse.toString());
            return;
        }

        // Gọi DAO để lấy dữ liệu
        AssignmentQueries queries = new AssignmentQueries();
        JSONObject jsonResult = queries.getTeacherName(hwId, studentId);

        // Gửi kết quả về phía client
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResult.toString());
            out.flush();
        }
    }
}


