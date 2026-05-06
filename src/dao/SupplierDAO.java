package dao;

import model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public List<Supplier> layTatCa() {
        List<Supplier> ds = new ArrayList<>();

        String sql = "SELECT * FROM suppliers";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Supplier s = new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("supplier_name"),
                        rs.getString("contact_person"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
                ds.add(s);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }
}