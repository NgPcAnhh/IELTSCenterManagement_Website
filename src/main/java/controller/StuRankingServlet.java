package controller;

import com.google.gson.Gson;
import dao.StuRankingDao;
import model.Ranking;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/StuRanking")
public class StuRankingServlet extends HttpServlet {

    private final StuRankingDao stuRankingDao = new StuRankingDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        // Lấy userId từ session
        String userId = (String) request.getSession().getAttribute("userId");

        if (userId == null || userId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"User ID is missing\"}");
            return;
        }

        try {
            // Lấy dữ liệu xếp hạng từ DAO
            Ranking ranking = stuRankingDao.getStudentRanking(userId);

            if (ranking == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"No ranking data found\"}");
            } else {
                // Trả dữ liệu dưới dạng JSON
                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(ranking));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal server error\"}");
        }
    }
}
