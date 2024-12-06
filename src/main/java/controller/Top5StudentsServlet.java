package controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import util.ConnecttoSQL;

@WebServlet("/Top5Students")
public class Top5StudentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lấy userId từ session
        HttpSession session = request.getSession(false);
        String userId = (session != null) ? (String) session.getAttribute("userId") : null;

        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"User is not logged in\"}");
            return;
        }

        ArrayList<StudentScore> topStudents = new ArrayList<>();

        try (Connection conn = ConnecttoSQL.getConnection()) {
            String query = "SELECT s.student_name, mt.overall " +
                    "FROM student s " +
                    "JOIN mock_test mt ON s.id = mt.id " +
                    "WHERE mt.id_test = ( " +
                    "   SELECT id_test " +
                    "   FROM mock_test " +
                    "   WHERE id = ? " +
                    "   ORDER BY time DESC " +
                    "   LIMIT 1 " +
                    ") " +
                    "ORDER BY mt.overall DESC " +
                    "LIMIT 5";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userId); // Sử dụng userId từ session
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String studentName = rs.getString("student_name");
                double overallScore = rs.getDouble("overall");
                topStudents.add(new StudentScore(studentName, overallScore));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String json = gson.toJson(topStudents);
        response.getWriter().write(json);
    }

    // Lớp nội bộ để lưu trữ tên và điểm tổng
    private class StudentScore {
        String studentName;
        double overallScore;

        StudentScore(String studentName, double overallScore) {
            this.studentName = studentName;
            this.overallScore = overallScore;
        }
    }
}
