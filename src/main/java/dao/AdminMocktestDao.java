package dao;

import org.json.JSONArray;
import org.json.JSONObject;
import util.ConnecttoSQL;
import java.sql.*;

public class AdminMocktestDao {

    public JSONArray getAllMockTests() {
        String query = "SELECT  * from mock_test";

        JSONArray mockTests = new JSONArray();

        try (Connection conn = ConnecttoSQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                JSONObject mockTest = new JSONObject();
                mockTest.put("id", rs.getString("id"));
                mockTest.put("idTest", rs.getString("id_test"));
                mockTest.put("time", rs.getString("time"));
                mockTest.put("reading", rs.getDouble("reading"));
                mockTest.put("listening", rs.getDouble("listening"));
                mockTest.put("writing", rs.getDouble("writing"));
                mockTest.put("speaking", rs.getDouble("speaking"));
                mockTest.put("overall", rs.getDouble("overall"));
                mockTest.put("feedback_r", rs.getString("feedback_r"));
                mockTest.put("feedback_l", rs.getString("feedback_l"));
                mockTest.put("feedback_w", rs.getString("feedback_w"));
                mockTest.put("feedback_s", rs.getString("feedback_s"));
                mockTests.put(mockTest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mockTests;
    }

    public JSONObject getMockTestByIdAndIdTest(String id, String idTest) {
        String query = "SELECT * FROM mock_test WHERE id = ? AND id_test = ?";
        JSONObject mockTest = new JSONObject();

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.setString(2, idTest);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mockTest.put("id", rs.getString("id"));
                mockTest.put("idTest", rs.getString("id_test"));
                mockTest.put("time", rs.getString("time"));
                mockTest.put("reading", rs.getDouble("reading"));
                mockTest.put("listening", rs.getDouble("listening"));
                mockTest.put("writing", rs.getDouble("writing"));
                mockTest.put("speaking", rs.getDouble("speaking"));
                mockTest.put("overall", rs.getDouble("overall"));
                mockTest.put("feedback_r", rs.getString("feedback_r"));
                mockTest.put("feedback_l", rs.getString("feedback_l"));
                mockTest.put("feedback_w", rs.getString("feedback_w"));
                mockTest.put("feedback_s", rs.getString("feedback_s"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mockTest;
    }

    public boolean updateMockTestScore(String id, String idTest, String field, double value) {
        String query = "UPDATE mock_test SET " + field + " = ? WHERE id = ? AND id_test = ?";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, value);
            stmt.setString(2, id);
            stmt.setString(3, idTest);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMockTestFeedback(String id, String idTest, String feedbackR, String feedbackL, String feedbackW, String feedbackS) {
        String query = "UPDATE mock_test SET feedback_r = ?, feedback_l = ?, feedback_w = ?, feedback_s = ? WHERE id = ? AND id_test = ?";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, feedbackR);
            stmt.setString(2, feedbackL);
            stmt.setString(3, feedbackW);
            stmt.setString(4, feedbackS);
            stmt.setString(5, id);
            stmt.setString(6, idTest);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addMockTest(String id, String idTest, Date time, double reading, double listening, double writing, double speaking, double overall, String feedbackR, String feedbackL, String feedbackW, String feedbackS) {
        String query = "INSERT INTO mock_test (id, id_test, time, reading, listening, writing, speaking, overall, feedback_r, feedback_l, feedback_w, feedback_s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.setString(2, idTest);
            stmt.setDate(3, time);
            stmt.setDouble(4, reading);
            stmt.setDouble(5, listening);
            stmt.setDouble(6, writing);
            stmt.setDouble(7, speaking);
            stmt.setDouble(8, overall);
            stmt.setString(9, feedbackR);
            stmt.setString(10, feedbackL);
            stmt.setString(11, feedbackW);
            stmt.setString(12, feedbackS);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
