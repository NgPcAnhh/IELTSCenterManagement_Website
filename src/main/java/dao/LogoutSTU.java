package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LogoutSTU {

    // Phương thức cập nhật thời gian logout (time_out) cho người dùng
    public boolean updateLogoutTime(String userId, String logoutTime) {
        String query = "UPDATE login_tracking SET time_out = ? WHERE id = ? ORDER BY time_in DESC LIMIT 1";
        boolean updateSuccess = false;

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, logoutTime);
            stmt.setString(2, userId);

            int rowsAffected = stmt.executeUpdate();
            updateSuccess = rowsAffected > 0;

            System.out.println("Rows affected: " + rowsAffected); // Log kiểm tra
        } catch (Exception e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }
}
