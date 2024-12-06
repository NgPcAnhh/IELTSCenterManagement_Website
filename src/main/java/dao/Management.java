package dao;

import model.Account;
import util.ConnecttoSQL;
import util.PasswordEncryptor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Management {

    // Lấy danh sách tất cả các tài khoản
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT ID, Login_name, Password FROM account" +
                "limit 50";

        try (Connection conn = ConnecttoSQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("ID");
                String loginName = rs.getString("Login_name");
                String encryptedPassword = rs.getString("Password");
                String decryptedPassword = PasswordEncryptor.decrypt(encryptedPassword); // Giải mã mật khẩu

                Account account = new Account(id, loginName, decryptedPassword);
                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }

        return accounts;
    }

    // Tìm kiếm tài khoản theo ID (Hỗ trợ tìm kiếm với LIKE)
    public List<Account> findAccountsById(String id) {
        String sql = "SELECT ID, Login_name, Password FROM account WHERE ID LIKE ?";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + id + "%"); // Sử dụng LIKE với ký tự đại diện
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String accountId = rs.getString("ID");
                    String loginName = rs.getString("Login_name");
                    String password = rs.getString("Password");
                    accounts.add(new Account(accountId, loginName, password));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }


    // Đổi mật khẩu cho tài khoản theo ID
    public boolean changePassword(String id, String newPassword) {
        String sql = "UPDATE account SET Password = ? WHERE ID = ?";
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String encryptedPassword = PasswordEncryptor.encrypt(newPassword); // Mã hóa mật khẩu mới
            stmt.setString(1, encryptedPassword);
            stmt.setString(2, id);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
        return false;
    }

    // Xóa tài khoản theo ID
    public boolean deleteAccount(String id) {
        String sql = "DELETE FROM account WHERE ID = ?";
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
