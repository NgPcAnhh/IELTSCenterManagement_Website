package dao;

import model.Students;
import util.ConnecttoSQL;

import java.sql.*;

public class ADMINStudentSearch {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Students searchStudentById(String studentId) {
        String query = "SELECT * FROM student WHERE id = ?";
        try {
            conn = new ConnecttoSQL().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, studentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Students student = new Students();
                student.setStudent_name(rs.getString("student_name"));
                student.setDate_birth(rs.getTimestamp("date_birth"));
                student.setId(rs.getString("id"));
                student.setPhone_number(rs.getString("phone_number"));
                student.setGmail(rs.getString("gmail"));
                student.setParent_name(rs.getString("parent_name"));
                student.setParent_number(rs.getString("parent_number"));
                student.setMa_mon(rs.getString("ma_mon"));
                student.setMa_mon_1(rs.getString("ma_mon_1"));
                student.setMa_mon_2(rs.getString("ma_mon_2"));
                student.setSs1(rs.getString("ss1"));
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}