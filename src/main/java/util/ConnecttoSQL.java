// ConnecttoSQL.java
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnecttoSQL {
    private static final String URL = "jdbc:mysql://localhost:3306/Ptit_web_project";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Tải driver MySQL JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to MySQL database established successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to MySQL database.");
            e.printStackTrace();
        }
        return conn;
    }
}
