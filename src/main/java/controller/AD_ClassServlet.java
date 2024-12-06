package controller;

import com.google.gson.Gson;
import dao.AD_Class;

import model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getStudentsBySubject")
public class AD_ClassServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectCode = request.getParameter("subjectCode");
        AD_Class studentDAO = new AD_Class();
        List<Student> students = studentDAO.getStudentsBySubject(subjectCode);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new Gson().toJson(students);
        response.getWriter().write(json);
    }
}
