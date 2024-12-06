package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.MockTest;
import util.ConnecttoSQL;

public class ProcesschartSTU {
    public List<MockTest> getTestScores(String studentId) {
        List<MockTest> testScores = new ArrayList<>();
        String query = "SELECT reading, listening, writing, speaking, overall,time FROM mock_test WHERE id = ?";  // cập nhật id và time

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, studentId); // sử dụng id thay cho student_id
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                MockTest mockTest = new MockTest();
                mockTest.setReading(rs.getFloat("reading"));
                mockTest.setListening(rs.getFloat("listening"));
                mockTest.setWriting(rs.getFloat("writing"));
                mockTest.setSpeaking(rs.getFloat("speaking"));
                mockTest.setOverall(rs.getFloat("overall"));
                mockTest.setTime(rs.getDate("time"));
                testScores.add(mockTest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testScores;
    }

}
