package controller;

import dao.AssignmentSubmitDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/submitAssignment")
@MultipartConfig
public class SubmitAssignmentServlet extends HttpServlet {

    private static final String STORAGE_PATH = "HW-storage";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.getWriter().write("{\"status\":\"error\", \"message\":\"User not logged in.\"}");
            return;
        }

        String studentId = (String) session.getAttribute("userId");
        String hwId = request.getParameter("HW_id");
        String linkInput = request.getParameter("linkInput");

        String filePathOrLink = linkInput;  // Default to link if provided

        // Handle file upload if a file is selected
        Part filePart = request.getPart("fileInput");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = studentId + "_" + hwId + "_" + filePart.getSubmittedFileName();
            String storageDirPath = getServletContext().getRealPath("/") + STORAGE_PATH;

            // Ensure the storage directory exists
            File storageDir = new File(storageDirPath);
            if (!storageDir.exists()) storageDir.mkdirs();

            filePart.write(storageDirPath + File.separator + fileName);
            filePathOrLink = STORAGE_PATH + "/" + fileName;
        }

        // Update the assignment submission in the database
        AssignmentSubmitDAO dao = new AssignmentSubmitDAO();
        boolean isUpdated = dao.submitAssignment(studentId, hwId, filePathOrLink);

        try (PrintWriter out = response.getWriter()) {
            if (isUpdated) {
                out.write("{\"status\":\"success\", \"message\":\"Assignment submitted successfully.\"}");
            } else {
                out.write("{\"status\":\"error\", \"message\":\"Failed to submit assignment.\"}");
            }
        }
    }
}
