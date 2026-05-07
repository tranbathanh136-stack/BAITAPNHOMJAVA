package dao;

import model.TheLoai;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TheLoaiDAO {

    public List<TheLoai> layTatCa() {
        List<TheLoai> ds = new ArrayList<TheLoai>();

        String sql = "SELECT * FROM categories";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TheLoai tl = new TheLoai(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );

                ds.add(tl);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }
}