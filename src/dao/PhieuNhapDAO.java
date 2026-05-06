package dao;

import model.PhieuNhap;
import java.sql.*;

public class PhieuNhapDAO {

    public int them(PhieuNhap pn) {
        int id = -1;

        String sql = "INSERT INTO import_invoices(supplier_id, employee_id, total_cost) VALUES (?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, pn.getMaNCC());
            ps.setInt(2, pn.getMaNV());
            ps.setDouble(3, pn.getTongTien());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }
}