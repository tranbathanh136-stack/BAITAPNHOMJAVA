package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // private static final String URL =
    // "jdbc:sqlserver://localhost:1433;databaseName=BookStore;encrypt=false;integratedSecurity=true";

    // public static Connection getConnection() throws SQLException {
    // try {
    // // 👉 THÊM DÒNG NÀY
    // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    // } catch (ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // return DriverManager.getConnection(URL);
    // }

    // Sửa URL cho MySQL
    private static final String URL = "jdbc:mysql://localhost:3307/BookStore?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Tên đăng nhập MySQL của bạn
    private static final String PASSWORD = "123456"; // Mật khẩu MySQL của bạn

    public static Connection getConnection() throws SQLException {
        try {
            // Đăng ký driver MySQL (phiên bản mới dùng com.mysql.cj.jdbc.Driver)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Kết nối thành công!");
            System.out.println("Database: " + conn.getCatalog());
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
}