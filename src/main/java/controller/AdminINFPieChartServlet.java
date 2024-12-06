package controller;

import dao.AdminINFPieChart;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/adminGetCourseCounts")
public class AdminINFPieChartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        AdminINFPieChart adminChartDAO = new AdminINFPieChart();
        JSONArray courseCounts = adminChartDAO.getCourseCounts();

        JSONObject jsonResponse = new JSONObject();
        try (PrintWriter out = response.getWriter()) {
            if (courseCounts.length() > 0) {
                jsonResponse.put("status", "success");
                jsonResponse.put("data", courseCounts);
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "No data found for course counts and revenue.");
            }
            out.print(jsonResponse.toString());
        }
    }
}
