package controller;

import com.google.gson.Gson;
import dao.ADMINBillTracking;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/ADMINBillTrackingServlet")
public class ADMINBillTrackingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set UTF-8 encoding for response
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            // Call DAO to get bills
            ADMINBillTracking dao = new ADMINBillTracking();
            // Modify the pattern to match student IDs starting with "STU-K5"
            List<Map<String, Object>> billsList = dao.getBillsByStudentId("STU-K5%");

            // Convert to JSON and send response
            Gson gson = new Gson();
            String json = gson.toJson(billsList);
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Error retrieving bills.\"}");
        }
    }
}
