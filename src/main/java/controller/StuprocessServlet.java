package controller;

import dao.ProcessSTU;
import org.json.JSONArray;
import org.json.JSONObject;
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

@WebServlet("/stuprocess")
public class StuprocessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy session hiện tại
        HttpSession session = request.getSession(false);
        String userId = (String) session.getAttribute("userId");

        // Kiểm tra nếu userId không tồn tại trong session
        if (userId == null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            try (PrintWriter out = response.getWriter()) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("success", false);
                jsonResponse.put("message", "User not logged in.");
                out.print(jsonResponse.toString());
                out.flush();
            }
            return;
        }

        ProcessSTU processSTU = new ProcessSTU();
        List<Map<String, Object>> courses = processSTU.getCoursesForStudent(userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONArray jsonCourses = new JSONArray();
        for (Map<String, Object> course : courses) {
            JSONObject jsonCourse = new JSONObject(course);
            jsonCourses.put(jsonCourse);
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("courses", jsonCourses);

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
