package controller;

import com.google.gson.Gson;
import dao.ADMINStudentUpdate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@WebServlet("/ADMINStudentUpdateServlet")
public class AdminStudentUpdateController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt mã hóa cho request và response
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Parse JSON từ request body
            Map<String, String> studentData = new Gson().fromJson(request.getReader(), Map.class);
            System.out.println("Received data: " + studentData);

            // Xử lý dữ liệu như bình thường
            String studentName = studentData.getOrDefault("student_name", "").trim();
            String dateBirth = studentData.getOrDefault("date_birth", "").trim();
            String id = studentData.getOrDefault("id", "").trim();

            // Kiểm tra các trường bắt buộc
            if (id.isEmpty() || studentName.isEmpty() || dateBirth.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Missing required fields\"}");
                return;
            }

            // Convert date to MySQL DATETIME
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateBirth = outputFormat.format(inputFormat.parse(dateBirth));

            // Gọi DAO
            ADMINStudentUpdate dao = new ADMINStudentUpdate();
            boolean isUpdated = dao.updateStudent(
                    studentName, dateBirth,
                    studentData.get("phone_number"),
                    studentData.get("gmail"),
                    studentData.get("parent_name"),
                    studentData.get("parent_number"),
                    studentData.get("ma_mon"),
                    studentData.get("ma_mon_1"),
                    studentData.get("ma_mon_2"),
                    studentData.get("ss1"), id
            );

            // Gửi phản hồi
            if (isUpdated) {
                response.getWriter().write("{\"message\": \"Student updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"message\": \"Failed to update student\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Internal server error: " + e.getMessage() + "\"}");
        }
    }
}
