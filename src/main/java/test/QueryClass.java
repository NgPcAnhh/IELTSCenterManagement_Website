package test;

import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryClass {

    // Phương thức để thực hiện truy vấn
    public static void main(String[] args) {
        String ID = "STU-K5-003"; // Bạn sẽ tự nhập giá trị ID học sinh ở đây
        Connection conn = null; // Đối tượng kết nối tới cơ sở dữ liệu
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Kết nối tới cơ sở dữ liệu
            conn = ConnecttoSQL.getConnection(); // Giả sử bạn đã có class ConnecttoSQL cho việc kết nối

            // Câu lệnh SQL 2: Kiểm tra mã môn mà học sinh đăng ký
            String sqlCheckCourses = "SELECT ID, [Mã môn], [Mã môn.1], [Mã môn.2] FROM student WHERE ID = ?";
            stmt = conn.prepareStatement(sqlCheckCourses);
            stmt.setString(1, ID); // Set giá trị ID của học sinh
            rs = stmt.executeQuery();

            // Biến để lưu các mã môn không null
            String[] courseCodes = new String[3];
            int courseCount = 0;

            // Lấy kết quả truy vấn SQL 2 và in ra
            if (rs.next()) {
                System.out.println("Kết quả của câu lệnh SQL 2 (Thông tin mã môn học sinh đã đăng ký):");
                System.out.println("Student ID: " + rs.getString("ID"));
                courseCodes[0] = rs.getString("Mã môn");
                courseCodes[1] = rs.getString("Mã môn.1");
                courseCodes[2] = rs.getString("Mã môn.2");

                // In ra các mã môn
                System.out.println("Mã môn 1: " + (courseCodes[0] != null ? courseCodes[0] : "Không đăng ký"));
                System.out.println("Mã môn 2: " + (courseCodes[1] != null ? courseCodes[1] : "Không đăng ký"));
                System.out.println("Mã môn 3: " + (courseCodes[2] != null ? courseCodes[2] : "Không đăng ký"));

                // Kiểm tra số mã môn không null
                for (String course : courseCodes) {
                    if (course != null) {
                        courseCount++;
                    }
                }
                System.out.println("-----------------------------------");
            }

            // Đóng resultSet sau khi truy vấn SQL 2
            rs.close();
            stmt.close();

            // Thực hiện câu lệnh SQL 1 dựa trên số môn học đã đăng ký
            String sqlQueryClass = "SELECT register.[Class's ID], register.ID, register.[Full name], "
                    + "register.[Date/birth], register.Email, student.[Parent's name], student.[Parent's number] "
                    + "FROM register INNER JOIN student ON register.ID = student.ID "
                    + "WHERE [Class's ID] = ?";

            // Dùng chung PreparedStatement cho các truy vấn SQL 1
            for (int i = 0; i < courseCount; i++) {
                if (courseCodes[i] != null) {
                    stmt = conn.prepareStatement(sqlQueryClass);
                    stmt.setString(1, courseCodes[i]); // Set giá trị mã lớp từ mã môn
                    rs = stmt.executeQuery();

                    // In ra kết quả của từng truy vấn SQL 1
                    System.out.println("Kết quả của câu lệnh SQL 1 cho mã môn " + courseCodes[i] + ":");
                    while (rs.next()) {
                        System.out.println("Class ID: " + rs.getString("Class's ID"));
                        System.out.println("Student ID: " + rs.getString("ID"));
                        System.out.println("Full name: " + rs.getString("Full name"));
                        System.out.println("Date of birth: " + rs.getString("Date/birth"));
                        System.out.println("Email: " + rs.getString("Email"));
                        System.out.println("Parent's name: " + rs.getString("Parent's name"));
                        System.out.println("Parent's number: " + rs.getString("Parent's number"));
                        System.out.println("-------------------------------");
                    }

                    // Đóng resultSet sau mỗi lần thực hiện câu lệnh SQL 1
                    rs.close();
                }
                stmt.close(); // Đóng PreparedStatement sau mỗi lần query
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
