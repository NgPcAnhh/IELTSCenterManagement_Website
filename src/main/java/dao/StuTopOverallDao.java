package dao;

import model.StudentTopOverall;
import util.ConnecttoSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StuTopOverallDao {
    // Phương thức lấy top 5 học sinh
    public List<StudentTopOverall> getTop5Students() {
        List<StudentTopOverall> topStudents = new ArrayList<>();
        String sql = "WITH AverageOverall AS (" +
                "    SELECT " +
                "        s.id AS student_id, " +
                "        s.student_name, " +
                "        AVG(mt.overall) AS average_overall, " +
                "        COUNT(mt.id_test) AS total_mock_tests " +
                "    FROM " +
                "        student s " +
                "    JOIN " +
                "        mock_test mt ON s.id = mt.id " +
                "    GROUP BY " +
                "        s.id, s.student_name" +
                ") " +
                "SELECT " +
                "    student_id, " +
                "    student_name, " +
                "    average_overall " +
                "FROM " +
                "    AverageOverall " +
                "ORDER BY " +
                "    average_overall DESC, " +
                "    total_mock_tests ASC " +
                "LIMIT 5;";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StudentTopOverall student = new StudentTopOverall();
                student.setStudentId(rs.getString("student_id"));
                student.setStudentName(rs.getString("student_name"));
                student.setAverageOverall(rs.getDouble("average_overall"));
                topStudents.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topStudents;
    }
}
