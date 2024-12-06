package dao;

import model.Ranking;
import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StuRankingDao {

    public Ranking getStudentRanking(String userId) {
        Ranking ranking = null;

        // SQL đã sửa để tương thích với JDBC và MySQL
        String sql =
                "WITH AvgOverall AS ( " +
                        "    SELECT id, AVG(overall) AS avg_overall " +
                        "    FROM mock_test " +
                        "    GROUP BY id " +
                        "), " +
                        "RankedStudents AS ( " +
                        "    SELECT id, avg_overall, " +
                        "           RANK() OVER (ORDER BY avg_overall DESC) AS student_rank, " +
                        "           COUNT(*) OVER () AS total_students " +
                        "    FROM AvgOverall " +
                        "), " +
                        "Comparison AS ( " +
                        "    SELECT r.id, r.avg_overall, r.student_rank, r.total_students, " +
                        "           (SELECT COUNT(*) FROM RankedStudents rs WHERE rs.avg_overall > r.avg_overall) / r.total_students * 100 AS better_than, " +
                        "           (SELECT COUNT(*) FROM RankedStudents rs WHERE rs.avg_overall < r.avg_overall) / r.total_students * 100 AS worse_than " +
                        "    FROM RankedStudents r " +
                        ") " +
                        "SELECT id, " +
                        "       avg_overall AS overall, " +
                        "       student_rank AS ranking, " +
                        "       ROUND(better_than, 2) AS better_than, " +
                        "       ROUND(worse_than, 2) AS worse_than " +
                        "FROM Comparison " +
                        "WHERE id = ?;";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Truyền tham số userId vào câu SQL
            preparedStatement.setString(1, userId);

            // Thực thi câu truy vấn và lấy dữ liệu
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ranking = new Ranking(
                            resultSet.getString("id"),
                            resultSet.getFloat("overall"),
                            resultSet.getInt("ranking"),
                            resultSet.getFloat("better_than"),
                            resultSet.getFloat("worse_than")
                    );
                } else {
                    System.out.println("No ranking data found for userId: " + userId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ranking;
    }
}
