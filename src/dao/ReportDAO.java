package dao;

import model.InventoryStatus;
import model.SalesByDay;
import model.TopSellingBook;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    // Doanh thu theo ngày
    public List<SalesByDay> getSalesByDay(java.sql.Date from, java.sql.Date to) {
        List<SalesByDay> list = new ArrayList<>();
        String sql = "SELECT DATE(invoice_date) as sale_date, COUNT(*) as total_invoices, SUM(final_amount) as total_revenue "
                +
                "FROM invoices " +
                "WHERE invoice_date BETWEEN ? AND ? " +
                "GROUP BY DATE(invoice_date) ORDER BY sale_date";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, from);
            ps.setDate(2, to);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SalesByDay s = new SalesByDay();
                s.setSaleDate(rs.getDate("sale_date").toLocalDate());
                s.setTotalInvoices(rs.getInt("total_invoices"));
                s.setTotalRevenue(rs.getBigDecimal("total_revenue"));
                // Profit sẽ tính sau hoặc lấy từ cột khác
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Sách bán chạy (theo số lượng)
    public List<TopSellingBook> getTopSellingBooks(int limit) {
        List<TopSellingBook> list = new ArrayList<>();
        String sql = "SELECT b.book_id, b.title, SUM(d.quantity) as total_qty, SUM(d.subtotal) as total_revenue " +
                "FROM invoice_details d JOIN books b ON d.book_id = b.book_id " +
                "GROUP BY d.book_id ORDER BY total_qty DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TopSellingBook t = new TopSellingBook();
                t.setBookId(rs.getInt("book_id"));
                t.setTitle(rs.getString("title"));
                t.setTotalQuantitySold(rs.getInt("total_qty"));
                t.setTotalRevenue(rs.getBigDecimal("total_revenue"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tồn kho hiện tại
    public List<InventoryStatus> getInventoryStatus() {
        List<InventoryStatus> list = new ArrayList<>();
        String sql = "SELECT book_id, title, quantity, selling_price FROM books ORDER BY title";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                InventoryStatus inv = new InventoryStatus(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("selling_price"));
                list.add(inv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}