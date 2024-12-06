package dao;

import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ADMINLibraryUpdateDao {
    public boolean saveBook(String id, String title, String author, String description, String categories, String uploadTime, String file_path) {
        String sql = "INSERT INTO library (id, title, author, description, category, upload_date, file_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setString(4, description);
            ps.setString(5, categories);
            ps.setString(6, uploadTime);
            ps.setString(7, file_path);


            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
