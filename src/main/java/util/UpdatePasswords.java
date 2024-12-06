package util;

import util.PasswordEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdatePasswords {

    public static void main(String[] args) {
        String selectQuery = "SELECT ID FROM account";
        String updateQuery = "UPDATE account SET Password = ? WHERE ID = ?";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             ResultSet rs = selectStmt.executeQuery()) {

            while (rs.next()) {
                String userId = rs.getString("id");
                String encryptedPassword = PasswordEncryptor.encrypt("1"); // Mã hóa mật khẩu hiện tại

                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, encryptedPassword);
                    updateStmt.setString(2, userId);
                    updateStmt.executeUpdate();
                }
            }
            System.out.println("Updated all passwords successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
