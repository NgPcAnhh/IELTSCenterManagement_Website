package controller;

import com.google.gson.JsonObject;
import dao.ADMINLibraryUpdateDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ADMINLibraryUpdate")
public class ADMINLibraryUpdateServlet extends HttpServlet {
    private final ADMINLibraryUpdateDao dao = new ADMINLibraryUpdateDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        try {
            JsonObject requestData = new com.google.gson.JsonParser().parse(request.getReader()).getAsJsonObject();

            String id = requestData.get("id").getAsString();
            String title = requestData.get("title").getAsString();
            String author = requestData.get("author").getAsString();
            String description = requestData.has("description") ? requestData.get("description").getAsString() : null;
            String categories = requestData.get("categories").getAsString();
            String uploadTime = requestData.get("upload_time").getAsString();
            String file_path = requestData.get("file_path").getAsString();

            boolean success = dao.saveBook(id, title, author, description, categories, uploadTime, file_path);

            if (success) {
                jsonResponse.addProperty("status", "success");
            } else {
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "Failed to save book!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", e.getMessage());
        }

        out.print(jsonResponse);
        out.flush();
    }
}
