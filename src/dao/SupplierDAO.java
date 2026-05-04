package dao;

import model.Supplier;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public List<Supplier> getAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
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

    public Supplier getById(int id) {
        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(Supplier s) {
        String sql = "INSERT INTO suppliers (supplier_name, contact_person, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getSupplierName());
            ps.setString(2, s.getContactPerson());
            ps.setString(3, s.getPhone());
            ps.setString(4, s.getAddress());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean update(Supplier s) {
        String sql = "UPDATE suppliers SET supplier_name=?, contact_person=?, phone=?, address=? WHERE supplier_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSupplierName());
            ps.setString(2, s.getContactPerson());
            ps.setString(3, s.getPhone());
            ps.setString(4, s.getAddress());
            ps.setInt(5, s.getSupplierId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM suppliers WHERE supplier_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Supplier mapRow(ResultSet rs) throws SQLException {
        return new Supplier(
                rs.getInt("supplier_id"),
                rs.getString("supplier_name"),
                rs.getString("contact_person"),
                rs.getString("phone"),
                rs.getString("address"));
    }
}