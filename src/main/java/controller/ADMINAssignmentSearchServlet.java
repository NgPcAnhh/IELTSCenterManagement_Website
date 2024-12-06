package controller;

import dao.ADMINAssignmentSearchDAO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/adminSearchAssignments")
public class ADMINAssignmentSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String keyword = request.getParameter("query");
        ADMINAssignmentSearchDAO dao = new ADMINAssignmentSearchDAO();
        List<Map<String, Object>> assignments = dao.searchAssignments(keyword);

        String json = new Gson().toJson(assignments);
        response.getWriter().write(json);
    }
}
