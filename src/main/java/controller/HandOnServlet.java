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

@WebServlet("/getHandOn")
public class HandOnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        String studentId = (String) session.getAttribute("userId");
        String hwId = request.getParameter("hwId");

        AssignmentQueries queries = new AssignmentQueries();
        JSONObject jsonResult = queries.getHandOn(studentId, hwId);

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResult.toString());
            out.flush();
        }
    }
}
