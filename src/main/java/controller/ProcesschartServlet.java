package controller;

import com.google.gson.Gson;
import dao.ProcesschartSTU;
import model.MockTest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/processChartScores")
public class ProcesschartServlet extends HttpServlet {
    private ProcesschartSTU processchartSTU = new ProcesschartSTU();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String studentId = (String) session.getAttribute("userId"); // lấy id từ session

        if (studentId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized access - Please log in.");
            return;
        }

        List<MockTest> testScores = processchartSTU.getTestScores(studentId);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String json = new Gson().toJson(testScores);
        out.print(json);
        out.flush();
    }
}
