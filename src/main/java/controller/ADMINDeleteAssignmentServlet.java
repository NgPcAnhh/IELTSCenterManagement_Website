package controller;

import dao.ADMINDeleteAssignmentDAO;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adminDeleteAssignment")
public class ADMINDeleteAssignmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String hwId = request.getParameter("hw_id");
        ADMINDeleteAssignmentDAO dao = new ADMINDeleteAssignmentDAO();
        boolean isDeleted = dao.deleteAssignment(hwId);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", isDeleted);

        response.getWriter().write(jsonResponse.toString());
    }
}
