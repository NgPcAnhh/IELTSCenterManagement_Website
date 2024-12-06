package controller;

import com.google.gson.Gson;
import dao.AdminMocktestSearchDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/adminMocktestSearch")
public class AdminMocktestSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String keyword = request.getParameter("query");
        AdminMocktestSearchDao dao = new AdminMocktestSearchDao();
        List<Map<String, Object>> mocktests = dao.searchMocktests(keyword);

        String json = new Gson().toJson(mocktests);
        response.getWriter().write(json);
    }
}
