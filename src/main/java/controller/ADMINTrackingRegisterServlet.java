package controller;

import com.google.gson.Gson;
import dao.ADMINTrackingRegister;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin-tracking-register")
public class ADMINTrackingRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set UTF-8 encoding for request and response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // Fetch data from DAO
        ADMINTrackingRegister dao = new ADMINTrackingRegister();
        List<Map<String, String>> registrations = dao.getPendingRegistrations();

        // Convert the data to JSON
        Gson gson = new Gson();
        String json = gson.toJson(registrations);

        // Write JSON response
        response.getWriter().write(json);

        // Debug logs
        System.out.println("Pending registrations size: " + registrations.size());
    }
}
