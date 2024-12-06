package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ADMINDeleteAssignmentDAO {

    public boolean deleteAssignment(String hwId) {
        String query = "DELETE FROM assignment WHERE hw_id = ?";
        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, hwId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
