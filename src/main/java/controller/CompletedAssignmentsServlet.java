package controller;

import dao.AssignmentDAO;
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

@WebServlet("/getCompletedAssignments")
public class CompletedAssignmentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONArray jsonResponse = new JSONArray();

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userId") != null) {
            String studentId = (String) session.getAttribute("userId");

            AssignmentDAO assignmentDAO = new AssignmentDAO();

            try {
                jsonResponse = assignmentDAO.getCompletedAssignments(studentId);
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
