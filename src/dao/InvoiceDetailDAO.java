package dao;

import model.InvoiceDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO {

    // Chèn một dòng chi tiết, dùng connection truyền vào
    public void insert(InvoiceDetail detail, Connection conn) throws SQLException {
        String sql = "INSERT INTO invoice_details (invoice_id, book_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, detail.getInvoiceId());
        ps.setInt(2, detail.getBookId());
        ps.setInt(3, detail.getQuantity());
        ps.setBigDecimal(4, detail.getUnitPrice());
        ps.executeUpdate();
        ps.close();
    }

    // Lấy chi tiết theo mã hóa đơn
    public List<InvoiceDetail> getByInvoiceId(int invoiceId) {
        List<InvoiceDetail> list = new ArrayList<>();
        String sql = "SELECT d.*, b.title as book_title FROM invoice_details d JOIN books b ON d.book_id = b.book_id WHERE d.invoice_id = ?";
        try (Connection conn = util.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InvoiceDetail detail = new InvoiceDetail();
                detail.setDetailId(rs.getInt("detail_id"));
                detail.setInvoiceId(rs.getInt("invoice_id"));
                detail.setBookId(rs.getInt("book_id"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setUnitPrice(rs.getBigDecimal("unit_price"));
                detail.setSubtotal(rs.getBigDecimal("subtotal"));
                detail.setBookTitle(rs.getString("book_title")); // tạm thêm
                list.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}