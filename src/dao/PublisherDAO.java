package dao;

import model.Publisher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO {

    public List<Publisher> getAll() {
        List<Publisher> list = new ArrayList<>();
        String sql = "SELECT * FROM publishers";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Publisher(rs.getInt("publisher_id"),
                        rs.getString("publisher_name"),
                        rs.getString("address"),
                        rs.getString("phone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Publisher getById(int id) {
        String sql = "SELECT * FROM publishers WHERE publisher_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Publisher(rs.getInt("publisher_id"),
                        rs.getString("publisher_name"),
                        rs.getString("address"),
                        rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(Publisher pub) {
        String sql = "INSERT INTO publishers (publisher_name, address, phone) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pub.getPublisherName());
            ps.setString(2, pub.getAddress());
            ps.setString(3, pub.getPhone());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean update(Publisher pub) {
        String sql = "UPDATE publishers SET publisher_name=?, address=?, phone=? WHERE publisher_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pub.getPublisherName());
            ps.setString(2, pub.getAddress());
            ps.setString(3, pub.getPhone());
            ps.setInt(4, pub.getPublisherId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM publishers WHERE publisher_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}