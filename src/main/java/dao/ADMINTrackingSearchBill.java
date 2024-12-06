package dao;

import util.ConnecttoSQL;
import model.Bills;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ADMINTrackingSearchBill {
    public List<Bills> searchBills(String query1, String query2) {
        List<Bills> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE student_id LIKE ? OR ma_mon_hoc LIKE ?";

        try (Connection connection = ConnecttoSQL.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, query1);
            ps.setString(2, query2);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bills bill = new Bills();
                bill.setMaMonHoc(rs.getString("ma_mon_hoc"));
                bill.setStudentId(rs.getString("student_id"));
                bill.setTime(rs.getString("time"));
                bill.setPrice(rs.getDouble("price"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
}
