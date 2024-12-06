package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.ADMINLibraryArrangeDao;
import model.Library;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/ADMINLibraryArrange")
public class ADMINLibraryArrangeServlet extends HttpServlet {
    private final ADMINLibraryArrangeDao libraryDao = new ADMINLibraryArrangeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        List<Library> libraryList = libraryDao.getAllLibraryItems();
        JsonArray jsonArray = new JsonArray();

        // Định dạng ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Library library : libraryList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", library.getId());
            jsonObject.addProperty("title", library.getTitle());
            jsonObject.addProperty("author", library.getAuthor());
            jsonObject.addProperty("description", library.getDescription());
            jsonObject.addProperty("category", library.getCategory());
            jsonObject.addProperty("uploadDate", library.getUploadDate() != null ? dateFormat.format(library.getUploadDate()) : "Unknown");
            jsonObject.addProperty("filePath", library.getFilePath());
            jsonArray.add(jsonObject);
        }

        response.getWriter().write(jsonArray.toString());
    }
}
