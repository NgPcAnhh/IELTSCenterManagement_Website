package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

public class Personalin4 {

    public JSONObject getPersonalInformation(String id) {
        String sql = "SELECT s.Student_name, s.Date_birth, s.ID, s.Phone_number, s.Gmail, s.Parent_name " +
                "FROM account acc " +
                "INNER JOIN student s ON acc.ID = s.ID " +
                "WHERE acc.ID = ?";
        JSONObject studentData = new JSONObject();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                studentData.put("studentName", resultSet.getString("Student_name"));
                studentData.put("dateOfBirth", resultSet.getString("Date_birth"));
                studentData.put("studentId", resultSet.getString("ID"));
                studentData.put("phoneNumber", resultSet.getString("Phone_number"));
                studentData.put("email", resultSet.getString("Gmail"));
                studentData.put("parentName", resultSet.getString("Parent_name"));
            } else {
                studentData.put("message", "Dữ liệu không tồn tại.");
            }

        } catch (SQLException e) {
            studentData.put("error", "Lỗi khi kết nối hoặc truy vấn: " + e.getMessage());
        }
        return studentData;
    }
}
