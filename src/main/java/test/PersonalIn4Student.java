//package test;
//
//import util.ConnecttoSQL;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class PersonalIn4Student {
//
//    // Phương thức để thực hiện truy vấn SQL và trả về kết quả
//    public void getPersonalInformation(String loginName, String password) {
//        // Khai báo câu lệnh SQL với các tham số đầu vào
//        String sql = SELECT s.`Student_name`, s.`Date_birth`, s.ID, s.`Phone_number`, s.Gmail, s.`Parent_name`
//        FROM student s
//        INNER JOIN account acc ON s.ID = acc.ID
//        WHERE acc.`Login_name` = ? AND acc.Password = ?;
//
//        // Sử dụng kết nối từ lớp ConnecttoSQL để thực hiện truy vấn
//        try (Connection conn = ConnecttoSQL.getConnection()) {
//            // Chuẩn bị câu lệnh SQL với các tham số (loginName và password)
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, loginName);  // Thiết lập tham số loginName
//            preparedStatement.setString(2, password);   // Thiết lập tham số password
//
//            // Thực thi truy vấn và nhận kết quả
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            // Xử lý kết quả truy vấn
//            while (resultSet.next()) {
//                String studentName = resultSet.getString(1); // Lấy cột theo vị trí
//                String dateOfBirth = resultSet.getString(2);
//                String studentID = resultSet.getString(3);
//                String phoneNumber = resultSet.getString(4);
//                String email = resultSet.getString(5);
//                String parentName = resultSet.getString(6);
//
//                // In kết quả (hoặc bạn có thể xử lý theo cách khác)
//                System.out.println("Student Name: " + studentName);
//                System.out.println("Date of Birth: " + dateOfBirth);
//                System.out.println("Student ID: " + studentID);
//                System.out.println("Phone Number: " + phoneNumber);
//                System.out.println("Email: " + email);
//                System.out.println("Parent's Name: " + parentName);
//                System.out.println("=========================================");
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Lỗi khi kết nối hoặc truy vấn: " + e.getMessage());
//        }
//    }
//
//    // Phương thức main để kiểm tra truy vấn
//    public static void main(String[] args) {
//        // Khai báo login name và password mặc định
//        String loginName = "yourLoginName";  // Nhập giá trị login name
//        String password = "yourPassword";    // Nhập giá trị password
//
//        // Tạo đối tượng PersonalIn4Student và gọi phương thức với loginName và password mặc định
//        PersonalIn4Student personalIn4Student = new PersonalIn4Student();
//        personalIn4Student.getPersonalInformation(loginName, password);  // Gọi phương thức với tham số
//    }
//}
