package dao;

import model.ImportInvoice;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImportInvoiceDAO {

    public int insert(ImportInvoice inv, Connection conn) throws SQLException {
        String sql = "INSERT INTO import_invoices (supplier_id, employee_id, total_cost) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, inv.getSupplierId());
        ps.setInt(2, inv.getEmployeeId());
        ps.setBigDecimal(3, inv.getTotalCost());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        int id = -1;
        if (rs.next())
            id = rs.getInt(1);
        ps.close();
        return id;
    }

    public ImportInvoice getById(int importId) {
        String sql = "SELECT * FROM import_invoices WHERE import_id = ?";
        try (Connection conn = util.DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, importId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ImportInvoice> getAll() {
        List<ImportInvoice> list = new ArrayList<>();
        String sql = "SELECT * FROM import_invoices";
        try (Connection conn = util.DBConnection.getConnection();
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

    private ImportInvoice mapRow(ResultSet rs) throws SQLException {
        ImportInvoice inv = new ImportInvoice();
        inv.setImportId(rs.getInt("import_id"));
        inv.setSupplierId(rs.getInt("supplier_id"));
        inv.setEmployeeId(rs.getInt("employee_id"));
        inv.setImportDate(rs.getTimestamp("import_date").toLocalDateTime());
        inv.setTotalCost(rs.getBigDecimal("total_cost"));
        return inv;
    }
}