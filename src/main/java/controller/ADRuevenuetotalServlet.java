package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.ConnecttoSQL;

@WebServlet("/getRevenue")
public class ADRuevenuetotalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Use ConnecttoSQL to get database connection
            Connection conn = ConnecttoSQL.getConnection();
            String sql = "SELECT SUM(price) AS total_revenue FROM bills";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            double totalRevenue = 0;
            if (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }

            rs.close();
            ps.close();
            conn.close();

            // Return JSON response
            out.println("{\"totalRevenue\": " + totalRevenue + "}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

