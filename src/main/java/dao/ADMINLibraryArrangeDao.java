package dao;

import model.Library;
import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ADMINLibraryArrangeDao {

    public List<Library> getAllLibraryItems() {
        List<Library> libraryList = new ArrayList<>();
        String sql = "SELECT id, title, author, description, category, upload_date, file_path FROM library";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                String id = rs.getString("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String description = rs.getString("description");
                String category = rs.getString("category");
                java.sql.Date uploadDate = rs.getDate("upload_date"); // Sử dụng java.sql.Date
                String filePath = rs.getString("file_path");

                // Tạo đối tượng Library và gán giá trị
                Library library = new Library();
                library.setId(id);
                library.setTitle(title);
                library.setAuthor(author);
                library.setDescription(description);
                library.setCategory(category);
                library.setUploadDate(uploadDate); // Lưu trữ ngày tải lên
                library.setFilePath(filePath);

                // Thêm vào danh sách
                libraryList.add(library);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return libraryList;
    }

    public boolean deleteLibraryItem(String id) {
        String sql = "DELETE FROM library WHERE id = ?";
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            return false;
        }
    }
}
