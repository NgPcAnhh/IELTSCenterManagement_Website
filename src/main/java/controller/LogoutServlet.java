package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import util.ConnecttoSQL;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String userId = (String) session.getAttribute("userId"); // Assuming userId is stored in session

            // Update the latest session timeout in the login_tracking table
            try (Connection conn = ConnecttoSQL.getConnection()) {  // Using utils.ConnecttoSQL for MySQL connection
                String sql = "UPDATE login_tracking SET time_out = ? WHERE id = ? ORDER BY time_in DESC LIMIT 1";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setTimestamp(1, Timestamp.from(Instant.now()));
                    pstmt.setString(2, userId);
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating logout time.");
                return;
            }

            // Invalidate session to log user out
            session.invalidate();
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
