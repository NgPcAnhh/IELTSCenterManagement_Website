package dao;

import util.ConnecttoSQL;
import util.PasswordEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginSTU {

    // Phương thức kiểm tra thông tin đăng nhập
    public String checkLogin(String loginName, String password) {
        String query = "SELECT ID, Password FROM account WHERE Login_name = ?";
        String studentId = null;

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, loginName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String encryptedPassword = rs.getString("password");
                    String decryptedPassword = PasswordEncryptor.decrypt(encryptedPassword); // Decrypt password

                    if (decryptedPassword.equals(password)) {
                        studentId = rs.getString("id");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentId;
    }
    // Phương thức cập nhật thời gian đăng nhập cuối cùng
    public boolean updateLatestLogin(String loginName, String latestLoginTime) {
        String updateQuery = "UPDATE account SET `latest-login` = ? WHERE Login_name = ? ;\n";
        boolean updateSuccess = false;

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, latestLoginTime);
            stmt.setString(2, loginName);

            int rowsAffected = stmt.executeUpdate();
            updateSuccess = rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    // Phương thức ghi log đăng nhập vào bảng login_tracking
    public boolean insertLoginTracking(String studentId, String loginName, String password, String timeIn) {
        String insertQuery = "INSERT INTO login_tracking (id, login_name, password, time_in) VALUES (?, ?, ?, ?)";
        boolean insertSuccess = false;

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, studentId);
            stmt.setString(2, loginName);
            stmt.setString(3, password);
            stmt.setString(4, timeIn);

            int rowsInserted = stmt.executeUpdate();
            insertSuccess = rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return insertSuccess;
    }
}
