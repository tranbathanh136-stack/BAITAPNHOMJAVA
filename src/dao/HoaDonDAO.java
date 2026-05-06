package dao;

import model.HoaDon;
import java.sql.*;

public class HoaDonDAO {

    public int them(HoaDon hd) {
        int maHoaDon = -1;

        String sql = "INSERT INTO invoices(customer_id, employee_id, total_amount, final_amount) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, hd.getMaKhachHang());
            ps.setInt(2, hd.getMaNhanVien());
            ps.setDouble(3, hd.getTongTien());
            ps.setDouble(4, hd.getTongTien());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                maHoaDon = rs.getInt(1);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return maHoaDon;
    }
}