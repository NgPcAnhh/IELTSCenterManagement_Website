package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ADMINCreateAssignmentDAO {

    // Lấy danh sách ID từ bảng student dựa vào mã môn học
    public List<String> getStudentIdsBySubject(String subjectCode) {
        List<String> studentIds = new ArrayList<>();
        String query = "SELECT id FROM student WHERE id LIKE 'STU-K5%' AND (ma_mon = ? OR ma_mon_1 = ? OR ma_mon_2 = ?)";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, subjectCode);
            statement.setString(2, subjectCode);
            statement.setString(3, subjectCode);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                studentIds.add(resultSet.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentIds;
    }

    // Tạo bài tập mới cho danh sách sinh viên với các trường giống nhau
    public boolean createAssignmentsForStudents(String hwId, String hwName, String teacherId, String deadline, String subjectCode) {
        List<String> studentIds = getStudentIdsBySubject(subjectCode);
        String insertQuery = "INSERT INTO assignment (hw_id, hw_name, teacher_id, deadline, student_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            for (String studentId : studentIds) {
                statement.setString(1, hwId);
                statement.setString(2, hwName);
                statement.setString(3, teacherId);
                statement.setString(4, deadline);
                statement.setString(5, studentId);
                statement.addBatch(); // Thêm vào batch để xử lý
            }
            statement.executeBatch(); // Thực hiện batch insert
            return true; // Nếu không có lỗi, trả về true
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
}
