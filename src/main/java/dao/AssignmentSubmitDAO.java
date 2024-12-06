package dao;

import util.ConnecttoSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AssignmentSubmitDAO {

    public boolean submitAssignment(String studentId, String hwId, String filePathOrLink) {
        String sql = "UPDATE assignment SET hand_on = ?, checking = 'done', submit_time = NOW() WHERE HW_ID = ? AND student_id = ?";

        try (Connection conn = ConnecttoSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, filePathOrLink);
            pstmt.setString(2, hwId);
            pstmt.setString(3, studentId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
