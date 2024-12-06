package test;

import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Glory {

    // Phương thức để thực hiện truy vấn tìm điểm Overall cao nhất và sắp xếp theo ID Test và số ký tự Feedback
    public static void main(String[] args) {
        Connection conn = null; // Đối tượng kết nối tới cơ sở dữ liệu
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Kết nối tới cơ sở dữ liệu
            conn = ConnecttoSQL.getConnection(); // Giả sử bạn đã có class ConnecttoSQL cho việc kết nối

            // Câu lệnh SQL để tìm Overall cao nhất và sắp xếp theo ID Test và số ký tự Feedback
            String sqlQuery = "WITH MaxOverall AS ( "
                    + "SELECT MAX(Overall) AS MaxScore FROM mock_test) "
                    + "SELECT mock_test.ID, student.[Student's name], mock_test.[ID Test], mock_test.Overall "
                    + "FROM mock_test "
                    + "INNER JOIN student ON mock_test.ID = student.ID "
                    + "WHERE mock_test.Overall = (SELECT MaxScore FROM MaxOverall) "
                    + "ORDER BY mock_test.[ID Test] ASC, "
                    + "LEN(ISNULL([Feedback-R], '')) + LEN(ISNULL([Feedback-L], '')) + LEN(ISNULL([Feedback-W], '')) + LEN(ISNULL([Feedback-S], '')) ASC;";

            stmt = conn.prepareStatement(sqlQuery);
            rs = stmt.executeQuery();

            // In kết quả của truy vấn
            while (rs.next()) {
                String id = rs.getString("ID");
                String studentName = rs.getString("Student's name");
                String idTest = rs.getString("ID Test");
                float overall = rs.getFloat("Overall");

                System.out.println("ID: " + id + ", Student's Name: " + studentName +
                        ", ID Test: " + idTest + ", Overall: " + overall);
                System.out.println("-----------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các tài nguyên
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
