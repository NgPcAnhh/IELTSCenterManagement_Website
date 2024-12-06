package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



public class STU_CLASS {

    // Phương thức lấy danh sách mã môn của một học sinh theo id học sinh
    public List<String> getMaMonByStudentId(String studentId) {
        List<String> maMonList = new ArrayList<>();
        String query = "SELECT ma_mon, ma_mon_1, ma_mon_2 FROM student WHERE id = ?";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    maMonList.add(rs.getString("ma_mon"));
                    maMonList.add(rs.getString("ma_mon_1"));
                    maMonList.add(rs.getString("ma_mon_2"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maMonList;
    }

    // Phương thức lấy danh sách id học sinh tham gia một mã môn cụ thể
    public List<Map<String, String>> getStudentListByMaMon(String maMon) {
        List<Map<String, String>> studentList = new ArrayList<>();
        String query = "SELECT id, date_birth, student_name, phone_number, gmail FROM student WHERE (ma_mon = ? OR ma_mon_1 = ? OR ma_mon_2 = ?) AND id LIKE 'STU-K5%'";
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maMon);
            stmt.setString(2, maMon);
            stmt.setString(3, maMon);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> student = new HashMap<>();
                    student.put("id", rs.getString("id"));
                    student.put("date_birth", rs.getString("date_birth"));
                    student.put("student_name", rs.getString("student_name"));
                    student.put("phone_number", rs.getString("phone_number"));
                    student.put("gmail", rs.getString("gmail"));
                    studentList.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentList;
    }
}
