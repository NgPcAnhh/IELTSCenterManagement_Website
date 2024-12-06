// src/dao/BillDAO.java
package dao;

import model.Bill;
import util.ConnecttoSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ADRuevenue {

    public List<Bill> getBillsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM bills WHERE time BETWEEN ? AND ?";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, startDate.atStartOfDay().toString());
            statement.setString(2, endDate.atTime(23, 59, 59).toString());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setSubjectId(resultSet.getString("ma_mon_hoc"));
                bill.setStudentId(resultSet.getString("student_id"));
                bill.setTime(resultSet.getTimestamp("time").toLocalDateTime());
                bill.setPrice(resultSet.getInt("price"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
}
