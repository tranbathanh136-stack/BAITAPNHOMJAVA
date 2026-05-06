package dao;

import model.ChiTietNhap;
import java.sql.*;

public class ChiTietNhapDAO {

    public void them(ChiTietNhap ct) {
        String sql = "INSERT INTO import_details(import_id, book_id, quantity, import_price) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ct.getMaPN());
            ps.setInt(2, ct.getMaSach());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getGiaNhap());

            ps.executeUpdate();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}