package controller;

import com.google.gson.Gson;
import dao.AdminTop10OverallDao;
import model.AdminTopOverall;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ADMINTop10Overall")
public class ADMINTop10OverallServlet extends HttpServlet {

    private final AdminTop10OverallDao adminTop10OverallDao = new AdminTop10OverallDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        try {
            // Lấy danh sách top 10 từ DAO
            List<AdminTopOverall> topOverallList = adminTop10OverallDao.getTop10Overall();

            // Chuyển đổi danh sách sang JSON
            Gson gson = new Gson();
            String json = gson.toJson(topOverallList);

            // Gửi JSON về client
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal server error\"}");
        }
    }
}
