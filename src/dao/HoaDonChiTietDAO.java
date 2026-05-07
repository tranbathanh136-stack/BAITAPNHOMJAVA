package dao;

import model.HoaDonChiTiet;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HoaDonChiTietDAO {

    public int them(HoaDonChiTiet ct) {
        int ketQua = 0;

        String sql = "INSERT INTO invoice_details(invoice_id, book_id, quantity, unit_price) "
                + "VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ct.getMaHoaDon());
            ps.setInt(2, ct.getMaSach());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getDonGia());

            ketQua = ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }
}