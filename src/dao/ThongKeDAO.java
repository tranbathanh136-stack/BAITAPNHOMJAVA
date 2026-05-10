package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    public double tinhDoanhThu(String tuNgay, String denNgay) {
        double doanhThu = 0;

        String sql = "SELECT SUM(id.quantity * id.unit_price) AS doanh_thu " +
                "FROM invoice_details id " +
                "JOIN invoices i ON id.invoice_id = i.invoice_id " +
                "WHERE DATE(i.invoice_date) BETWEEN ? AND ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, tuNgay);
            ps.setString(2, denNgay);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                doanhThu = rs.getDouble("doanh_thu");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return doanhThu;
    }

    public double tinhLoiNhuan(String tuNgay, String denNgay) {
        double loiNhuan = 0;

        String sql = "SELECT SUM(id.quantity * (id.unit_price - b.price)) AS loi_nhuan " +
                "FROM invoice_details id " +
                "JOIN invoices i ON id.invoice_id = i.invoice_id " +
                "JOIN books b ON id.book_id = b.book_id " +
                "WHERE DATE(i.invoice_date) BETWEEN ? AND ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, tuNgay);
            ps.setString(2, denNgay);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                loiNhuan = rs.getDouble("loi_nhuan");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loiNhuan;
    }

    public int tongHoaDon(String tuNgay, String denNgay) {
        int tong = 0;

        String sql = "SELECT COUNT(*) AS tong_hd FROM invoices " +
                "WHERE DATE(invoice_date) BETWEEN ? AND ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, tuNgay);
            ps.setString(2, denNgay);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tong = rs.getInt("tong_hd");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tong;
    }

    public int tongSachBan(String tuNgay, String denNgay) {
        int tong = 0;

        String sql = "SELECT SUM(id.quantity) AS tong_sach " +
                "FROM invoice_details id " +
                "JOIN invoices i ON id.invoice_id = i.invoice_id " +
                "WHERE DATE(i.invoice_date) BETWEEN ? AND ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, tuNgay);
            ps.setString(2, denNgay);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tong = rs.getInt("tong_sach");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tong;
    }

    public List<Object[]> topSachBanChay(String tuNgay, String denNgay) {
        List<Object[]> ds = new ArrayList<Object[]>();

        String sql = "SELECT b.book_id, b.title, SUM(id.quantity) AS tong_ban, " +
                "SUM(id.quantity * id.unit_price) AS doanh_thu " +
                "FROM invoice_details id " +
                "JOIN invoices i ON id.invoice_id = i.invoice_id " +
                "JOIN books b ON id.book_id = b.book_id " +
                "WHERE DATE(i.invoice_date) BETWEEN ? AND ? " +
                "GROUP BY b.book_id, b.title " +
                "ORDER BY tong_ban DESC " +
                "LIMIT 5";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, tuNgay);
            ps.setString(2, denNgay);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getInt("tong_ban"),
                        rs.getDouble("doanh_thu")
                };

                ds.add(row);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }
}