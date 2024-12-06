package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import util.ConnecttoSQL;

@WebServlet("/register")
public class MainWebRegister extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập UTF-8 để xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        // Đọc dữ liệu từ request body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        JSONObject jsonData = new JSONObject(sb.toString());

        // Lấy dữ liệu từ JSON
        String fullName = jsonData.optString("full_name");
        String phoneNumber = jsonData.optString("phone_number");
        String dateOfBirth = jsonData.optString("date_birth");
        String email = jsonData.optString("email");
        String classId = jsonData.optString("class_id");
        String moreInformation = jsonData.optString("more_information");

        // Kiểm tra nếu classId rỗng hoặc null (bắt buộc)
        if (classId == null || classId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Class ID is required.");
            return;
        }

        // Sử dụng kết nối từ ConnecttoSQL
        try (Connection connection = ConnecttoSQL.getConnection()) {
            String sql = "INSERT INTO register (full_name, phone_number, date_birth, email, class_id, more_information) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, fullName);
                stmt.setString(2, phoneNumber);
                stmt.setString(3, dateOfBirth); // Đảm bảo định dạng hợp lệ trong DB
                stmt.setString(4, email);
                stmt.setString(5, classId);
                stmt.setString(6, moreInformation);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Registration successful.");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Failed to register.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
