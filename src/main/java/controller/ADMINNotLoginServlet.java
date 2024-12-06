package controller;

import com.google.gson.Gson;
import dao.ADMINNotLoginDao;
import model.NotLoginData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ADMINNotLogin")
public class ADMINNotLoginServlet extends HttpServlet {

    private final ADMINNotLoginDao adminNotLoginDao = new ADMINNotLoginDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        try {
            // Lấy danh sách học sinh không đăng nhập
            List<NotLoginData> notLoginList = adminNotLoginDao.getNotLoggedInStudents();

            // Chuyển danh sách thành JSON
            Gson gson = new Gson();
            String json = gson.toJson(notLoginList);

            // Gửi JSON về client
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal server error\"}");
        }
    }
}
;