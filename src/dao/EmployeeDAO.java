package dao;

import model.Employee;
import util.DBConnection;
import util.PasswordUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // Lấy tất cả nhân viên (join users và employees)
    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT e.employee_id, e.user_id, u.username, u.full_name, u.role_id, r.role_name, " +
                "e.phone, e.email, e.address, e.position " +
                "FROM employees e " +
                "JOIN users u ON e.user_id = u.user_id " +
                "JOIN roles r ON u.role_id = r.role_id";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Employee getById(int employeeId) {
        String sql = "SELECT e.employee_id, e.user_id, u.username, u.full_name, u.role_id, r.role_name, " +
                "e.phone, e.email, e.address, e.position " +
                "FROM employees e " +
                "JOIN users u ON e.user_id = u.user_id " +
                "JOIN roles r ON u.role_id = r.role_id " +
                "WHERE e.employee_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tạo nhân viên mới (bao gồm tạo user và employee record) – dùng transaction
    public int create(Employee emp, String rawPassword) throws SQLException {
        Connection conn = null;
        PreparedStatement psUser = null;
        PreparedStatement psEmp = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Chèn user
            String userSql = "INSERT INTO users (username, password, full_name, role_id) VALUES (?, ?, ?, ?)";
            psUser = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            psUser.setString(1, emp.getUsername());
            psUser.setString(2, PasswordUtils.hashPassword(rawPassword)); // mã hóa mật khẩu
            psUser.setString(3, emp.getFullName());
            psUser.setInt(4, emp.getRoleId());
            psUser.executeUpdate();
            ResultSet rs = psUser.getGeneratedKeys();
            int userId;
            if (rs.next()) {
                userId = rs.getInt(1);
            } else {
                throw new SQLException("Không thể tạo user");
            }

            // 2. Chèn employee
            String empSql = "INSERT INTO employees (user_id, phone, email, address, position) VALUES (?, ?, ?, ?, ?)";
            psEmp = conn.prepareStatement(empSql, Statement.RETURN_GENERATED_KEYS);
            psEmp.setInt(1, userId);
            psEmp.setString(2, emp.getPhone());
            psEmp.setString(3, emp.getEmail());
            psEmp.setString(4, emp.getAddress());
            psEmp.setString(5, emp.getPosition());
            psEmp.executeUpdate();
            rs = psEmp.getGeneratedKeys();
            int employeeId;
            if (rs.next()) {
                employeeId = rs.getInt(1);
            } else {
                throw new SQLException("Không thể tạo employee");
            }

            conn.commit();
            return employeeId;
        } catch (SQLException e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (psUser != null)
                psUser.close();
            if (psEmp != null)
                psEmp.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public boolean update(Employee emp) throws SQLException {
        Connection conn = null;
        PreparedStatement psUser = null;
        PreparedStatement psEmp = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Cập nhật thông tin user (full_name, role_id) – username giữ nguyên
            String userSql = "UPDATE users SET full_name=?, role_id=? WHERE user_id=?";
            psUser = conn.prepareStatement(userSql);
            psUser.setString(1, emp.getFullName());
            psUser.setInt(2, emp.getRoleId());
            psUser.setInt(3, emp.getUserId());
            psUser.executeUpdate();

            // Cập nhật employee
            String empSql = "UPDATE employees SET phone=?, email=?, address=?, position=? WHERE employee_id=?";
            psEmp = conn.prepareStatement(empSql);
            psEmp.setString(1, emp.getPhone());
            psEmp.setString(2, emp.getEmail());
            psEmp.setString(3, emp.getAddress());
            psEmp.setString(4, emp.getPosition());
            psEmp.setInt(5, emp.getEmployeeId());
            int rows = psEmp.executeUpdate();

            conn.commit();
            return rows > 0;
        } catch (SQLException e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (psUser != null)
                psUser.close();
            if (psEmp != null)
                psEmp.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // Reset mật khẩu – truyền vào userId và plainPassword
    public boolean resetPassword(int userId, String newPlainPassword) {
        String sql = "UPDATE users SET password=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, PasswordUtils.hashPassword(newPlainPassword));
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa nhân viên (xóa employee trước, user sau, hoặc dùng ON DELETE CASCADE nếu
    // đã định nghĩa)
    public boolean delete(int employeeId, int userId) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            // Xóa employee
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM employees WHERE employee_id=?");
            ps1.setInt(1, employeeId);
            ps1.executeUpdate();
            ps1.close();
            // Xóa user
            PreparedStatement ps2 = conn.prepareStatement("DELETE FROM users WHERE user_id=?");
            ps2.setInt(1, userId);
            ps2.executeUpdate();
            ps2.close();
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return false;
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setEmployeeId(rs.getInt("employee_id"));
        e.setUserId(rs.getInt("user_id"));
        e.setUsername(rs.getString("username"));
        e.setFullName(rs.getString("full_name"));
        e.setRoleId(rs.getInt("role_id"));
        e.setRoleName(rs.getString("role_name"));
        e.setPhone(rs.getString("phone"));
        e.setEmail(rs.getString("email"));
        e.setAddress(rs.getString("address"));
        e.setPosition(rs.getString("position"));
        return e;
    }
}