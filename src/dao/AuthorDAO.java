package dao;

import model.Author;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    public List<Author> getAll() {
        List<Author> list = new ArrayList<>();
        String sql = "SELECT * FROM authors";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Author(rs.getInt("author_id"),
                        rs.getString("author_name"),
                        rs.getString("biography")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Author getById(int id) {
        String sql = "SELECT * FROM authors WHERE author_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Author(rs.getInt("author_id"),
                        rs.getString("author_name"),
                        rs.getString("biography"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(Author author) {
        String sql = "INSERT INTO authors (author_name, biography) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, author.getAuthorName());
            ps.setString(2, author.getBiography());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean update(Author author) {
        String sql = "UPDATE authors SET author_name=?, biography=? WHERE author_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getAuthorName());
            ps.setString(2, author.getBiography());
            ps.setInt(3, author.getAuthorId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM authors WHERE author_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách tác giả của một cuốn sách
    public List<Author> getAuthorsByBookId(int bookId) {
        List<Author> list = new ArrayList<>();
        String sql = "SELECT a.* FROM authors a JOIN book_authors ba ON a.author_id = ba.author_id WHERE ba.book_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Author(rs.getInt("author_id"),
                        rs.getString("author_name"),
                        rs.getString("biography")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm liên kết sách - tác giả (xóa hết cũ rồi thêm mới khi cập nhật)
    public void updateBookAuthors(int bookId, List<Integer> authorIds) {
        Connection conn = null;
        PreparedStatement psDelete = null;
        PreparedStatement psInsert = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Xóa liên kết cũ
            psDelete = conn.prepareStatement("DELETE FROM book_authors WHERE book_id=?");
            psDelete.setInt(1, bookId);
            psDelete.executeUpdate();

            // Thêm liên kết mới
            if (authorIds != null && !authorIds.isEmpty()) {
                psInsert = conn.prepareStatement("INSERT INTO book_authors (book_id, author_id) VALUES (?, ?)");
                for (int authorId : authorIds) {
                    psInsert.setInt(1, bookId);
                    psInsert.setInt(2, authorId);
                    psInsert.addBatch();
                }
                psInsert.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (psDelete != null)
                    psDelete.close();
            } catch (SQLException e) {
            }
            try {
                if (psInsert != null)
                    psInsert.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.setAutoCommit(true);
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }
}