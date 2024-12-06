package controller;

import com.google.gson.Gson;
import dao.STULibraryDao;
import model.Library;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "STULibraryServlet", urlPatterns = {"/STULibrary"})
public class STULibraryServlet extends HttpServlet {

    private final STULibraryDao libraryDao = new STULibraryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thiết lập mã hóa UTF-8 cho request và response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // Lấy danh sách tài liệu từ DAO
        List<Library> libraries = libraryDao.getAllLibraries();

        // Chuyển danh sách sang JSON và trả về response
        Gson gson = new Gson();
        String json = gson.toJson(libraries);
        response.getWriter().write(json);
    }
}
