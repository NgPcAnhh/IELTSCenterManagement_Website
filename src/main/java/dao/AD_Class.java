package dao;

import model.Student;
import util.ConnecttoSQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AD_Class {

    public List<Student> getStudentsBySubject(String subjectCode) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT student_name, date_birth, id, phone_number, gmail, parent_number \n" +
                "FROM student \n" +
                "WHERE id LIKE 'STU-K5%' AND (ma_mon = ? OR ma_mon_1 = ? OR ma_mon_2 = ?);\n";


        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, subjectCode);
            pstmt.setString(2, subjectCode);
            pstmt.setString(3, subjectCode);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStudentName(rs.getString("student_name"));
                student.setDateOfBirth(rs.getDate("date_birth"));
                student.setId(rs.getString("id"));
                student.setPhoneNumber(rs.getString("phone_number"));
                student.setGmail(rs.getString("gmail"));

                student.setParentNumber(rs.getString("parent_number"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
