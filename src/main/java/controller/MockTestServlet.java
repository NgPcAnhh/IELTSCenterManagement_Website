package controller;

import dao.MockTestDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/mocktestquery")
public class MockTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Người dùng chưa đăng nhập.");
            response.getWriter().print(errorResponse.toString());
            return;
        }

        String studentId = (String) session.getAttribute("userId");

        MockTestDAO mockTestDAO = new MockTestDAO();
        JSONArray mockTests = mockTestDAO.getMockTestsById(studentId);

        try (PrintWriter out = response.getWriter()) {
            if (mockTests.length() > 0) {
                out.print(mockTests.toString());
            } else {
                JSONObject noDataResponse = new JSONObject();
                noDataResponse.put("status", "error");
                noDataResponse.put("message", "Không có dữ liệu mock test cho người dùng này.");
                out.print(noDataResponse.toString());
            }
            out.flush();
        }
    }
}
