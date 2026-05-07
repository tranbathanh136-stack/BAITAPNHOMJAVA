package dao;

import model.NhaXuatBan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhaXuatBanDAO {

    public List<NhaXuatBan> layTatCa() {

        List<NhaXuatBan> ds = new ArrayList<>();

        String sql = "SELECT * FROM publishers";

        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                NhaXuatBan nxb = new NhaXuatBan(
                        rs.getInt("publisher_id"),
                        rs.getString("publisher_name")
                );

                ds.add(nxb);
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