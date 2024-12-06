package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessSTU {

    public List<Map<String, Object>> getCoursesForStudent(String studentId) {
        List<Map<String, Object>> courses = new ArrayList<>();

        String query = "SELECT b.ma_mon_hoc, b.time AS registration_date, "
                + "CASE WHEN g.Qua_trinh = '1 tháng' THEN DATE_FORMAT(DATE_ADD(b.time, INTERVAL 1 MONTH), '%Y-%m-01') "
                + "WHEN g.Qua_trinh = '3 tháng' THEN CASE WHEN DATE(b.time) <= CONCAT(YEAR(b.time), '-01-15') THEN CONCAT(YEAR(b.time), '-01-15') "
                + "WHEN DATE(b.time) <= CONCAT(YEAR(b.time), '-04-15') THEN CONCAT(YEAR(b.time), '-04-15') "
                + "WHEN DATE(b.time) <= CONCAT(YEAR(b.time), '-07-15') THEN CONCAT(YEAR(b.time), '-07-15') "
                + "WHEN DATE(b.time) <= CONCAT(YEAR(b.time), '-10-15') THEN CONCAT(YEAR(b.time), '-10-15') "
                + "ELSE CONCAT(YEAR(b.time) + 1, '-01-15') END "

                + "WHEN g.Qua_trinh = '6 tháng' THEN CASE WHEN DATE(b.time) <= CONCAT(YEAR(b.time), '-01-15') THEN CONCAT(YEAR(b.time), '-01-15') "
                + "WHEN DATE(b.time) <= CONCAT(YEAR(b.time), '-07-15') THEN CONCAT(YEAR(b.time), '-07-15') "
                + "ELSE CONCAT(YEAR(b.time) + 1, '-01-15') END "
                + "ELSE b.time END AS start_date, "
                + "CASE WHEN g.Qua_trinh = '1 tháng' THEN 30 WHEN g.Qua_trinh = '3 tháng' THEN 90 WHEN g.Qua_trinh = '6 tháng' THEN 180 ELSE NULL END AS duration_days "

                + "FROM bills b JOIN bangia g ON b.ma_mon_hoc = g.Ma_mon_hoc "
                + "WHERE b.student_id = ? AND b.ma_mon_hoc NOT IN ('SS1', 'MT0')";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("ma_mon_hoc", rs.getString("ma_mon_hoc"));
                course.put("registration_date", rs.getDate("registration_date"));
                course.put("start_date", rs.getDate("start_date"));
                course.put("duration_days", rs.getInt("duration_days"));
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }
}
