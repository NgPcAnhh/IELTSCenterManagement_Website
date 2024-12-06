package controller;

import com.google.gson.Gson;
import dao.ADMINTrackingSearchBill;
import model.Bills;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin-search-bills")
public class ADMINTrackingSearchBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set UTF-8 encoding for request and response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // Get the search query
        String query = request.getParameter("query");
        if (query == null || query.trim().isEmpty()) {
            response.getWriter().write("[]");
            return;
        }

        // Prepare wildcard query for DAO
        String wildcardQuery = "%" + query.trim() + "%";

        // Call DAO
        ADMINTrackingSearchBill dao = new ADMINTrackingSearchBill();
        List<Bills> bills = dao.searchBills(wildcardQuery, wildcardQuery);

        // Convert the list to JSON
        Gson gson = new Gson();
        String json = gson.toJson(bills);

        // Write JSON response
        response.getWriter().write(json);

        // Debug logs
        System.out.println("Query received: " + query);
        System.out.println("Bills found: " + bills.size());
    }
}
