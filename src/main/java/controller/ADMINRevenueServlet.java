package controller;

import dao.ADMINRevenueDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/adminRevenueData")
public class ADMINRevenueServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ADMINRevenueDAO revenueDAO = new ADMINRevenueDAO();
        JSONArray revenueData = revenueDAO.getMonthlyRevenue();

        try (PrintWriter out = response.getWriter()) {
            out.print(revenueData.toString());
            out.flush();
        }
    }
}
