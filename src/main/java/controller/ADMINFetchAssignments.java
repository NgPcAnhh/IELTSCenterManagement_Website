package controller;

import dao.ADMINAssignmentsDAO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/ADMINFetchAssignments")
public class ADMINFetchAssignments extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt mã hóa UTF-8 cho yêu cầu và phản hồi
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String subjectCode = request.getParameter("subjectCode");
        String queryPattern = subjectCode + "%"; // Tạo biến `queryPattern` với dấu `%`

        ADMINAssignmentsDAO dao = new ADMINAssignmentsDAO();
        List<Map<String, Object>> assignments = dao.getAssignmentsByHWID(queryPattern);

        String json = new Gson().toJson(assignments);
        response.getWriter().write(json);
    }
}
