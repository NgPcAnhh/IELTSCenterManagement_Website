package dao;

import model.Library;
import util.ConnecttoSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class STULibraryDao {

    public List<Library> getAllLibraries() {
        List<Library> libraries = new ArrayList<>();
        String sql = "SELECT id, title, author, description, category, upload_date, file_path FROM library";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Library library = new Library();
                library.setId(resultSet.getString("id"));
                library.setTitle(resultSet.getString("title"));
                library.setAuthor(resultSet.getString("author"));
                library.setDescription(resultSet.getString("description"));
                library.setCategory(resultSet.getString("category"));
                library.setUploadDate(resultSet.getTimestamp("upload_date"));
                library.setFilePath(resultSet.getString("file_path"));
                libraries.add(library);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libraries;
    }
}
