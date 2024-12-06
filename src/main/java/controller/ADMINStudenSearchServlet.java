package controller;

import com.google.gson.Gson;
import dao.ADMINStudentSearch;
import model.Students; // Đổi từ model.Student thành model.Students

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ADMINStudentSearchServlet", urlPatterns = {"/admin-student-search"})
public class ADMINStudenSearchServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String studentId = request.getParameter("id");

        ADMINStudentSearch dao = new ADMINStudentSearch();
        Students student = dao.searchStudentById(studentId); // Đổi Student thành Students

        PrintWriter out = response.getWriter();
        if (student != null) {
            String jsonResponse = gson.toJson(student);
            out.print(jsonResponse);
        } else {
            out.print("{}"); // Return empty object if no student found
        }
        out.flush();
    }
}
