package controller;

import dao.ADMINBillsDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/adminGetLatestBills")
public class ADMINBillsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Tạo instance của DAO và lấy danh sách bills mới nhất
        ADMINBillsDAO billsDAO = new ADMINBillsDAO();
        JSONArray latestBills = billsDAO.getLatestBills();

        // Tạo đối tượng JSON chứa danh sách bills và trạng thái thành công
        JSONObject responseJson = new JSONObject();
        responseJson.put("status", "success");
        responseJson.put("bills", latestBills);

        // Gửi JSON response về client
        try (PrintWriter out = response.getWriter()) {
            out.print(responseJson.toString());
            out.flush();
        }
    }
}
