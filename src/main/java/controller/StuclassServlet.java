package controller;

import dao.STU_CLASS;
import org.json.JSONObject;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/stuclass")
public class StuclassServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đọc dữ liệu JSON từ request
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        // Parse JSON
        JSONObject jsonRequest = new JSONObject(sb.toString());
        String action = jsonRequest.getString("action");

        STU_CLASS stuClassDAO = new STU_CLASS();
        JSONObject jsonResponse = new JSONObject();

        // Thiết lập phản hồi JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lấy session hiện tại
        HttpSession session = request.getSession(false);

        try (PrintWriter out = response.getWriter()) {
            // Kiểm tra nếu người dùng đã đăng nhập
            if (session != null && session.getAttribute("userId") != null) {
                String userId = (String) session.getAttribute("userId");

                if ("getMaMonByStudentId".equals(action)) {
                    // Lấy danh sách mã môn của sinh viên dựa trên userId từ session
                    List<String> maMonList = stuClassDAO.getMaMonByStudentId(userId);

                    JSONArray maMonArray = new JSONArray(maMonList);
                    jsonResponse.put("success", true);
                    jsonResponse.put("maMonList", maMonArray);

                } else if ("getStudentListByMaMon".equals(action)) {
                    // Lấy danh sách sinh viên trong lớp dựa trên mã môn
                    String maMon = jsonRequest.getString("maMon");
                    List<Map<String, String>> classList = stuClassDAO.getStudentListByMaMon(maMon);

                    JSONArray classArray = new JSONArray();
                    for (Map<String, String> student : classList) {
                        JSONObject studentJson = new JSONObject();
                        studentJson.put("id", student.get("id"));
                        studentJson.put("date_birth", student.get("date_birth"));
                        studentJson.put("student_name", student.get("student_name"));
                        studentJson.put("phone_number", student.get("phone_number"));
                        studentJson.put("gmail", student.get("gmail"));
                        classArray.put(studentJson);
                    }

                    jsonResponse.put("success", true);
                    jsonResponse.put("classList", classArray);

                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Hành động không hợp lệ.");
                }
            } else {
                // Nếu người dùng chưa đăng nhập
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Người dùng chưa đăng nhập.");
            }

            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
