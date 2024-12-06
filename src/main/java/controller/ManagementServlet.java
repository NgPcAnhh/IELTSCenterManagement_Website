package controller;

import com.google.gson.Gson;
import dao.Management;
import model.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ManagementServlet", urlPatterns = {"/listAccounts", "/searchAccount", "/changePassword", "/deleteAccount"})
public class ManagementServlet extends HttpServlet {

    private Management managementDao = new Management();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if ("/listAccounts".equals(action)) {
                List<Account> accounts = managementDao.getAllAccounts();
                String json = new Gson().toJson(accounts);
                out.print(json);
            } else if ("/searchAccount".equals(action)) {
                String id = request.getParameter("id");
                List<Account> accounts = managementDao.findAccountsById(id); // Gọi phương thức mới trong DAO
                if (!accounts.isEmpty()) {
                    out.print(new Gson().toJson(accounts));
                } else {
                    out.print(new Gson().toJson(Map.of("error", "No accounts found")));
                }
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if ("/changePassword".equals(action)) {
                String id = request.getParameter("id");
                String newPassword = request.getParameter("newPassword");
                boolean success = managementDao.changePassword(id, newPassword);
                out.print(new Gson().toJson(Map.of("success", success)));
            } else if ("/deleteAccount".equals(action)) {
                String id = request.getParameter("id");
                boolean success = managementDao.deleteAccount(id);
                out.print(new Gson().toJson(Map.of("success", success)));
            }
        }
    }
}
