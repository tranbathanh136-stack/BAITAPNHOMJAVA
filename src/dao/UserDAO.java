package dao;

import model.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Tìm user theo username và trả về đầy đủ thông tin (có role name)
    public User findByUsername(String username) {
        String sql = "SELECT u.user_id, u.username, u.password, u.full_name, u.role_id, r.role_name " +
                "FROM users u JOIN roles r ON u.role_id = r.role_id " +
                "WHERE u.username = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getInt("role_id"),
                        rs.getString("role_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}