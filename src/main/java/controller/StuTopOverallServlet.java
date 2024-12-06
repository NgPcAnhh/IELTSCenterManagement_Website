package controller;

import dao.StuTopOverallDao;
import model.StudentTopOverall;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/StuTopOverall")
public class StuTopOverallServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StuTopOverallDao dao = new StuTopOverallDao();
        List<StudentTopOverall> topStudents = dao.getTop5Students();

        // Chuyển danh sách thành JSON
        String json = new com.google.gson.Gson().toJson(topStudents);

        // Set response header và gửi trả về client
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
