package dao;

import java.sql.*;

public class DBConnection {
    // Địa chỉ kết nối MySQL (mặc định localhost:3307)
    private static final String URL = "jdbc:mysql://localhost:3307/java?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // tài khoản MySQL của bạn
    private static final String PASSWORD = ""; // mật khẩu (nếu có)

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Test kết nối
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Kết nối MySQL thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}