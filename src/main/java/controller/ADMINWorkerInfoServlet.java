package controller;

import dao.ADMINWorkerInfoDAO;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/adminWorkerInfo")
public class ADMINWorkerInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            // Return error if user is not logged in
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message", "User not logged in.");
            response.getWriter().print(errorResponse.toString());
            return;
        }

        String workerId = (String) session.getAttribute("userId");

        ADMINWorkerInfoDAO workerInfoDAO = new ADMINWorkerInfoDAO();
        JSONObject worker = workerInfoDAO.getWorkerInfoById(workerId);

        try (PrintWriter out = response.getWriter()) {
            out.print(worker.toString());
            out.flush();
        }
    }
}
