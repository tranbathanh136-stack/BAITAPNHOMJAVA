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
                        rs.getString("isbn"),
                        rs.getInt("category_id"),
                        rs.getInt("publisher_id"),
                        rs.getInt("publication_year"),
                        rs.getDouble("price"),
                        rs.getDouble("selling_price"),
                        rs.getInt("quantity"),
                        rs.getString("description"),
                        rs.getString("image_path")
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

        String sql = "INSERT INTO books(title, isbn, category_id, publisher_id, publication_year, price, selling_price, quantity, description, image_path) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, s.getTenSach());
            ps.setString(2, s.getIsbn());
            ps.setInt(3, s.getMaTheLoai());
            ps.setInt(4, s.getMaNXB());
            ps.setInt(5, s.getNamXB());
            ps.setDouble(6, s.getGiaNhap());
            ps.setDouble(7, s.getGiaBan());
            ps.setInt(8, s.getSoLuong());
            ps.setString(9, s.getMoTa());
            ps.setString(10, s.getDuongDanAnh());

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

        String sql = "UPDATE books SET title=?, isbn=?, category_id=?, publisher_id=?, publication_year=?, price=?, selling_price=?, quantity=?, description=?, image_path=? "
                + "WHERE book_id=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, s.getTenSach());
            ps.setString(2, s.getIsbn());
            ps.setInt(3, s.getMaTheLoai());
            ps.setInt(4, s.getMaNXB());
            ps.setInt(5, s.getNamXB());
            ps.setDouble(6, s.getGiaNhap());
            ps.setDouble(7, s.getGiaBan());
            ps.setInt(8, s.getSoLuong());
            ps.setString(9, s.getMoTa());
            ps.setString(10, s.getDuongDanAnh());
            ps.setInt(11, s.getMaSach());

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
}