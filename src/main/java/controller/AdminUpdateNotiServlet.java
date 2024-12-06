package controller;

import com.google.gson.Gson;
import dao.AdminUpdateNotiDao;
import model.Noti;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/AdminUpdateNoti")
public class AdminUpdateNotiServlet extends HttpServlet {
    private final AdminUpdateNotiDao dao = new AdminUpdateNotiDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Gson gson = new Gson();
        Noti noti = gson.fromJson(request.getReader(), Noti.class);

        boolean result = dao.updateNotification(noti);

        response.getWriter().write(gson.toJson(result));
    }
}
