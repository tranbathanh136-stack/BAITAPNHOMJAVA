package dao;

import model.NhanVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public List<NhanVien> layTatCa() {
        List<NhanVien> ds = new ArrayList<NhanVien>();

        String sql = "SELECT * FROM employees";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt("employee_id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("position")
                );

                ds.add(nv);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    public List<NhanVien> timTheoTen(String tuKhoa) {
        List<NhanVien> ds = new ArrayList<NhanVien>();

        String sql = "SELECT * FROM employees WHERE full_name LIKE ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + tuKhoa + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt("employee_id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("position")
                );

                ds.add(nv);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    public int them(NhanVien nv) {
        int ketQua = 0;

        String sql = "INSERT INTO employees(user_id, full_name, phone, email, address, position) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, nv.getMaUser());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getSoDienThoai());
            ps.setString(4, nv.getEmail());
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getChucVu());

            ketQua = ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public int sua(NhanVien nv) {
        int ketQua = 0;

        String sql = "UPDATE employees SET user_id=?, full_name=?, phone=?, email=?, address=?, position=? "
                + "WHERE employee_id=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, nv.getMaUser());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getSoDienThoai());
            ps.setString(4, nv.getEmail());
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getChucVu());
            ps.setInt(7, nv.getMaNhanVien());

            ketQua = ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public int xoa(int maNhanVien) {
        int ketQua = 0;

        String sql = "DELETE FROM employees WHERE employee_id=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maNhanVien);
            ketQua = ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }
}