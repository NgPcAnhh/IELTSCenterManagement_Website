package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.ADMINASSLAYOUTDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/adminGetAssignmentDetails")
public class ADMINASSLAYOUTServlet extends HttpServlet {

    private ADMINASSLAYOUTDAO dao = new ADMINASSLAYOUTDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String assignmentId = request.getParameter("assignmentId");
        String studentId = request.getParameter("studentId");

        // Lấy thông tin assignment dưới dạng Map
        Map<String, Object> assignmentData = dao.getAssignmentDetails(assignmentId, studentId);

        // Chuyển Map sang JSON
        String json = new Gson().toJson(assignmentData);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject jsonRequest = new Gson().fromJson(request.getReader(), JsonObject.class);
        String assignmentId = jsonRequest.get("assignmentId").getAsString();
        String studentId = jsonRequest.get("studentId").getAsString();
        String feedback = jsonRequest.get("feedback").getAsString();

        boolean success = dao.insertFeedback(assignmentId, studentId, feedback);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
}
