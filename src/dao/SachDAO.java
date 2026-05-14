package dao;

import model.Sach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SachDAO {

    public List<Sach> layTatCa() {

        List<Sach> ds = new ArrayList<Sach>();

        String sql = "SELECT * FROM books";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Sach s = new Sach(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getInt("category_id"),
                        rs.getInt("publisher_id"),
                        rs.getInt("publication_year"),
                        rs.getDouble("price"),
                        rs.getDouble("selling_price"),
                        rs.getInt("quantity")
                );

                ds.add(s);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return ds;
    }

    public int them(Sach s) {

        int ketQua = 0;

        String sql =
                "INSERT INTO books(title, category_id, publisher_id, publication_year, price, selling_price, quantity) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, s.getTenSach());
            ps.setInt(2, s.getMaTheLoai());
            ps.setInt(3, s.getMaNXB());
            ps.setInt(4, s.getNamXB());
            ps.setDouble(5, s.getGiaNhap());
            ps.setDouble(6, s.getGiaBan());
            ps.setInt(7, s.getSoLuong());

            ketQua = ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return ketQua;
    }

    public int sua(Sach s) {

        int ketQua = 0;

        String sql =
                "UPDATE books SET title=?, category_id=?, publisher_id=?, publication_year=?, price=?, selling_price=?, quantity=? "
                        + "WHERE book_id=?";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, s.getTenSach());
            ps.setInt(2, s.getMaTheLoai());
            ps.setInt(3, s.getMaNXB());
            ps.setInt(4, s.getNamXB());
            ps.setDouble(5, s.getGiaNhap());
            ps.setDouble(6, s.getGiaBan());
            ps.setInt(7, s.getSoLuong());
            ps.setInt(8, s.getMaSach());

            ketQua = ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return ketQua;
    }

    public int xoa(int maSach) {

        int ketQua = 0;

        String sql = "DELETE FROM books WHERE book_id=?";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maSach);

            ketQua = ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return ketQua;
    }

    public int capNhatSoLuong(int maSach, int soLuongMoi) {

        int ketQua = 0;

        String sql = "UPDATE books SET quantity=? WHERE book_id=?";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, soLuongMoi);
            ps.setInt(2, maSach);

            ketQua = ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return ketQua;
    }

    public List<Sach> timTheoTen(String tuKhoa) {

        List<Sach> ds = new ArrayList<Sach>();

        String sql = "SELECT * FROM books WHERE title LIKE ?";

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + tuKhoa + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Sach s = new Sach(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getInt("category_id"),
                        rs.getInt("publisher_id"),
                        rs.getInt("publication_year"),
                        rs.getDouble("price"),
                        rs.getDouble("selling_price"),
                        rs.getInt("quantity")
                );

                ds.add(s);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return ds;
    }

    public List<Sach> timKiem(
            String tenSach,
            int maTheLoai,
            int maNXB,
            int namXB
    ) {

        List<Sach> ds = new ArrayList<Sach>();

        String sql =
                "SELECT * FROM books WHERE title LIKE ?";

        if (maTheLoai > 0) {
            sql += " AND category_id = ?";
        }

        if (maNXB > 0) {
            sql += " AND publisher_id = ?";
        }

        if (namXB > 0) {
            sql += " AND publication_year = ?";
        }

        try {

            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            int index = 1;

            ps.setString(index++, "%" + tenSach + "%");

            if (maTheLoai > 0) {
                ps.setInt(index++, maTheLoai);
            }

            if (maNXB > 0) {
                ps.setInt(index++, maNXB);
            }

            if (namXB > 0) {
                ps.setInt(index++, namXB);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Sach s = new Sach(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getInt("category_id"),
                        rs.getInt("publisher_id"),
                        rs.getInt("publication_year"),
                        rs.getDouble("price"),
                        rs.getDouble("selling_price"),
                        rs.getInt("quantity")
                );

                ds.add(s);
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