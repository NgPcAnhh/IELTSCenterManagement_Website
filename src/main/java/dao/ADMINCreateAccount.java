package dao;

import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.HashMap;

public class ADMINCreateAccount {

    private static final Map<String, Integer> PRICE_MAP = new HashMap<>();
    static {
        PRICE_MAP.put("MT0", 50000);
        PRICE_MAP.put("OC1", 299000);
        PRICE_MAP.put("OS1", 999000);
        PRICE_MAP.put("OS2", 1499000);
        PRICE_MAP.put("OW1", 999000);
        PRICE_MAP.put("OW2", 1499000);
        PRICE_MAP.put("RL1", 1999000);
        PRICE_MAP.put("RL2", 1999000);
        PRICE_MAP.put("RL3", 1999000);
        PRICE_MAP.put("RL4", 2499000);
        PRICE_MAP.put("RL5", 2499000);
        PRICE_MAP.put("RL6", 2999000);
        PRICE_MAP.put("RT6", 9999000);
        PRICE_MAP.put("RT7", 4999000);
        PRICE_MAP.put("SA1", 1999000);
        PRICE_MAP.put("SB1", 3999000);
        PRICE_MAP.put("SB2", 3999000);
        PRICE_MAP.put("SS1", 99000);
        PRICE_MAP.put("WA1", 1999000);
        PRICE_MAP.put("WB1", 3999000);
        PRICE_MAP.put("WB2", 3999000);
    }

    public void saveAccountAndBills(Map<String, String> data) throws SQLException {
        String insertStudent = "INSERT INTO student (student_name, date_birth, id, phone_number, gmail, parent_name, parent_number, ma_mon, ma_mon_1, ma_mon_2, ss1) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertAccount = "INSERT INTO account (Login_name, Password, ID) VALUES (?, ?, ?)";
        String insertBills = "INSERT INTO bills (ma_mon_hoc, student_id, time, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnecttoSQL.getConnection()) {
            connection.setAutoCommit(false);

            try {
                // Lưu học sinh
                try (PreparedStatement ps = connection.prepareStatement(insertStudent)) {
                    ps.setString(1, data.getOrDefault("studentName", null));
                    ps.setString(2, data.get("dateOfBirth") != null ? data.get("dateOfBirth") : null);
                    ps.setString(3, data.get("studentId"));
                    ps.setString(4, data.getOrDefault("phoneNumber", null));
                    ps.setString(5, data.getOrDefault("email", null));
                    ps.setString(6, data.getOrDefault("parentName", null));
                    ps.setString(7, data.getOrDefault("parentPhoneNumber", null));
                    ps.setString(8, data.getOrDefault("subject1", null));
                    ps.setString(9, data.getOrDefault("subject2", null));
                    ps.setString(10, data.getOrDefault("subject3", null));
                    ps.setString(11, data.getOrDefault("ss1", null));
                    ps.executeUpdate();
                }

                // Lưu tài khoản
                try (PreparedStatement ps = connection.prepareStatement(insertAccount)) {
                    ps.setString(1, data.get("accountUsername"));
                    ps.setString(2, data.get("accountPassword"));
                    ps.setString(3, data.get("studentId"));
                    ps.executeUpdate();
                }

                // Lưu hóa đơn
                String[] subjects = {data.get("subject1"), data.get("subject2"), data.get("subject3")};
                try (PreparedStatement ps = connection.prepareStatement(insertBills)) {
                    // Thêm hóa đơn cho các môn học
                    for (String subject : subjects) {
                        if (subject != null && PRICE_MAP.containsKey(subject)) {
                            ps.setString(1, subject);
                            ps.setString(2, data.get("studentId"));
                            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // Thời gian hiện tại
                            ps.setInt(4, PRICE_MAP.get(subject));
                            ps.executeUpdate();
                        }
                    }

                    // Thêm hóa đơn cho SS1 nếu cần
                    String ss1Status = data.get("ss1");
                    if ("YES".equalsIgnoreCase(ss1Status)) {
                        ps.setString(1, "SS1");
                        ps.setString(2, data.get("studentId"));
                        ps.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // Thời gian hiện tại
                        ps.setInt(4, PRICE_MAP.get("SS1"));
                        ps.executeUpdate();
                    }
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }
}
