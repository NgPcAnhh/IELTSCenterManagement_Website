package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import util.ConnecttoSQL;

@WebServlet("/mockTestData")
public class MockTestHandler extends HttpServlet {

    // Model class for MockTest
    public static class MockTest {
        private String time;
        private String idTest;
        private int reading;
        private int listening;
        private int writing;
        private int speaking;
        private int overall;
        private String feedbackR;
        private String feedbackL;
        private String feedbackW;
        private String feedbackS;

        // Getters and Setters for each attribute
        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }

        public String getIdTest() { return idTest; }
        public void setIdTest(String idTest) { this.idTest = idTest; }

        public int getReading() { return reading; }
        public void setReading(int reading) { this.reading = reading; }

        public int getListening() { return listening; }
        public void setListening(int listening) { this.listening = listening; }

        public int getWriting() { return writing; }
        public void setWriting(int writing) { this.writing = writing; }

        public int getSpeaking() { return speaking; }
        public void setSpeaking(int speaking) { this.speaking = speaking; }

        public int getOverall() { return overall; }
        public void setOverall(int overall) { this.overall = overall; }

        public String getFeedbackR() { return feedbackR; }
        public void setFeedbackR(String feedbackR) { this.feedbackR = feedbackR; }

        public String getFeedbackL() { return feedbackL; }
        public void setFeedbackL(String feedbackL) { this.feedbackL = feedbackL; }

        public String getFeedbackW() { return feedbackW; }
        public void setFeedbackW(String feedbackW) { this.feedbackW = feedbackW; }

        public String getFeedbackS() { return feedbackS; }
        public void setFeedbackS(String feedbackS) { this.feedbackS = feedbackS; }
    }

    // Data Access Object (DAO) method for fetching mock test data
    private List<MockTest> getMockTestData() throws SQLException {
        List<MockTest> mockTests = new ArrayList<>();
        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM mock_test")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MockTest test = new MockTest();
                test.setTime(rs.getString("Time"));
                test.setIdTest(rs.getString("ID Test"));
                test.setReading(rs.getInt("Reading"));
                test.setListening(rs.getInt("Listening"));
                test.setWriting(rs.getInt("Writing"));
                test.setSpeaking(rs.getInt("Speaking"));
                test.setOverall(rs.getInt("Overall"));
                test.setFeedbackR(rs.getString("FeedbackR"));
                test.setFeedbackL(rs.getString("FeedbackL"));
                test.setFeedbackW(rs.getString("FeedbackW"));
                test.setFeedbackS(rs.getString("FeedbackS"));
                mockTests.add(test);
            }
        }
        return mockTests;
    }

    // Handles GET request to fetch and respond with mock test data in JSON
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            List<MockTest> mockTests = getMockTestData();
            String json = new Gson().toJson(mockTests);
            out.print(json);
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
