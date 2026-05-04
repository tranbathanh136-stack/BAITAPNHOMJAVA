package dao;

import model.ImportDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImportDetailDAO {

    public void insert(ImportDetail detail, Connection conn) throws SQLException {
        String sql = "INSERT INTO import_details (import_id, book_id, quantity, import_price) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, detail.getImportId());
        ps.setInt(2, detail.getBookId());
        ps.setInt(3, detail.getQuantity());
        ps.setBigDecimal(4, detail.getImportPrice());
        ps.executeUpdate();
        ps.close();
    }

    public List<ImportDetail> getByImportId(int importId) {
        List<ImportDetail> list = new ArrayList<>();
        String sql = "SELECT d.*, b.title as book_title FROM import_details d JOIN books b ON d.book_id = b.book_id WHERE d.import_id = ?";
        try (Connection conn = util.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, importId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ImportDetail detail = new ImportDetail();
                detail.setDetailId(rs.getInt("detail_id"));
                detail.setImportId(rs.getInt("import_id"));
                detail.setBookId(rs.getInt("book_id"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setImportPrice(rs.getBigDecimal("import_price"));
                detail.setSubtotal(rs.getBigDecimal("subtotal"));
                list.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}