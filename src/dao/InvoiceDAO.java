package dao;

import model.Invoice; // sẽ tạo model Invoice bên dưới
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    // Chèn hóa đơn mới, trả về ID. Dùng connection truyền vào để transaction
    public int insert(Invoice invoice, Connection conn) throws SQLException {
        String sql = "INSERT INTO invoices (customer_id, employee_id, total_amount, discount, final_amount) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        if (invoice.getCustomerId() != null) {
            ps.setInt(1, invoice.getCustomerId());
        } else {
            ps.setNull(1, Types.INTEGER);
        }
        ps.setInt(2, invoice.getEmployeeId());
        ps.setBigDecimal(3, invoice.getTotalAmount());
        ps.setBigDecimal(4, invoice.getDiscount());
        ps.setBigDecimal(5, invoice.getFinalAmount());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        int id = -1;
        if (rs.next()) {
            id = rs.getInt(1);
        }
        ps.close();
        return id;
    }

    // Lấy hóa đơn theo ID
    public Invoice getById(int invoiceId) {
        String sql = "SELECT * FROM invoices WHERE invoice_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả hóa đơn
    public List<Invoice> getAll() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM invoices";
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

    private Invoice mapRow(ResultSet rs) throws SQLException {
        Invoice inv = new Invoice();
        inv.setInvoiceId(rs.getInt("invoice_id"));
        inv.setCustomerId(rs.getInt("customer_id"));
        if (rs.wasNull())
            inv.setCustomerId(null);
        inv.setEmployeeId(rs.getInt("employee_id"));
        inv.setInvoiceDate(rs.getTimestamp("invoice_date").toLocalDateTime());
        inv.setTotalAmount(rs.getBigDecimal("total_amount"));
        inv.setDiscount(rs.getBigDecimal("discount"));
        inv.setFinalAmount(rs.getBigDecimal("final_amount"));
        return inv;
    }
}