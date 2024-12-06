package controller;

import dao.ADMINCreateAssignmentDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.JsonObject;

@WebServlet("/adminCreateAssignment")
public class ADMINCreateAssignmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String hwId = request.getParameter("hwId");
        String hwName = request.getParameter("hwName");
        String teacherId = request.getParameter("teacherId");
        String deadline = request.getParameter("deadline");
        String subjectCode = request.getParameter("subjectCode");

        ADMINCreateAssignmentDAO dao = new ADMINCreateAssignmentDAO();
        boolean isCreated = dao.createAssignmentsForStudents(hwId, hwName, teacherId, deadline, subjectCode);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", isCreated);

        response.getWriter().write(jsonResponse.toString());
    }
}
