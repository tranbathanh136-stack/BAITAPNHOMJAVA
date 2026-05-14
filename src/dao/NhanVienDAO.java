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

        String sql = "SELECT e.employee_id, e.user_id, u.username, u.password, "
                + "e.full_name, e.phone, e.email, e.address "
                + "FROM employees e "
                + "JOIN users u ON e.user_id = u.user_id";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt("employee_id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
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

        String sql = "SELECT e.employee_id, e.user_id, u.username, u.password, "
                + "e.full_name, e.phone, e.email, e.address "
                + "FROM employees e "
                + "JOIN users u ON e.user_id = u.user_id "
                + "WHERE e.full_name LIKE ? OR u.username LIKE ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + tuKhoa + "%");
            ps.setString(2, "%" + tuKhoa + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt("employee_id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
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

        String sqlUser = "INSERT INTO users(username, password, full_name, role_id) "
                + "VALUES (?, ?, ?, 2)";

        String sqlEmployee = "INSERT INTO employees(user_id, full_name, phone, email, address) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement psUser = conn.prepareStatement(
                    sqlUser,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            psUser.setString(1, nv.getUsername());
            psUser.setString(2, nv.getPassword());
            psUser.setString(3, nv.getHoTen());

            int kqUser = psUser.executeUpdate();

            if (kqUser > 0) {
                ResultSet rsKey = psUser.getGeneratedKeys();

                if (rsKey.next()) {
                    int userId = rsKey.getInt(1);

                    PreparedStatement psEmployee = conn.prepareStatement(sqlEmployee);

                    psEmployee.setInt(1, userId);
                    psEmployee.setString(2, nv.getHoTen());
                    psEmployee.setString(3, nv.getSoDienThoai());
                    psEmployee.setString(4, nv.getEmail());
                    psEmployee.setString(5, nv.getDiaChi());

                    ketQua = psEmployee.executeUpdate();

                    psEmployee.close();
                }

                rsKey.close();
            }

            if (ketQua > 0) {
                conn.commit();
            } else {
                conn.rollback();
            }

            psUser.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public int sua(NhanVien nv) {
        int ketQua = 0;

        String sqlUser = "UPDATE users SET username=?, password=?, full_name=? WHERE user_id=?";

        String sqlEmployee = "UPDATE employees SET full_name=?, phone=?, email=?, address=? "
                + "WHERE employee_id=?";

        try {
            Connection conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement psUser = conn.prepareStatement(sqlUser);

            psUser.setString(1, nv.getUsername());
            psUser.setString(2, nv.getPassword());
            psUser.setString(3, nv.getHoTen());
            psUser.setInt(4, nv.getMaUser());

            int kqUser = psUser.executeUpdate();

            PreparedStatement psEmployee = conn.prepareStatement(sqlEmployee);

            psEmployee.setString(1, nv.getHoTen());
            psEmployee.setString(2, nv.getSoDienThoai());
            psEmployee.setString(3, nv.getEmail());
            psEmployee.setString(4, nv.getDiaChi());
            psEmployee.setInt(5, nv.getMaNhanVien());

            int kqEmployee = psEmployee.executeUpdate();

            if (kqUser > 0 && kqEmployee > 0) {
                ketQua = 1;
                conn.commit();
            } else {
                conn.rollback();
            }

            psUser.close();
            psEmployee.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }

    public int xoa(int maNhanVien) {
        int ketQua = 0;

        String sqlSelect = "SELECT user_id FROM employees WHERE employee_id=?";
        String sqlDeleteEmployee = "DELETE FROM employees WHERE employee_id=?";
        String sqlDeleteUser = "DELETE FROM users WHERE user_id=?";

        try {
            Connection conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            int userId = 0;

            PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setInt(1, maNhanVien);

            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("user_id");
            }

            rs.close();
            psSelect.close();

            if (userId == 0 || userId == 1) {
                conn.rollback();
                conn.close();
                return 0;
            }

            PreparedStatement psEmployee = conn.prepareStatement(sqlDeleteEmployee);
            psEmployee.setInt(1, maNhanVien);

            int kqEmployee = psEmployee.executeUpdate();

            PreparedStatement psUser = conn.prepareStatement(sqlDeleteUser);
            psUser.setInt(1, userId);

            int kqUser = psUser.executeUpdate();

            if (kqEmployee > 0 && kqUser > 0) {
                ketQua = 1;
                conn.commit();
            } else {
                conn.rollback();
            }

            psEmployee.close();
            psUser.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ketQua;
    }
}