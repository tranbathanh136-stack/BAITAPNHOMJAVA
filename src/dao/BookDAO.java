package dao;

import model.Author;
import model.Book;
import util.DBConnection;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private AuthorDAO authorDAO = new AuthorDAO();

    // Lấy tất cả sách, kèm tên category, publisher và danh sách tác giả
    public List<Book> getAll() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT b.*, c.category_name, p.publisher_name " +
                "FROM books b " +
                "LEFT JOIN categories c ON b.category_id = c.category_id " +
                "LEFT JOIN publishers p ON b.publisher_id = p.publisher_id";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = mapRow(rs);
                book.setAuthors(authorDAO.getAuthorsByBookId(book.getBookId()));
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Book getById(int id) {
        String sql = "SELECT b.*, c.category_name, p.publisher_name " +
                "FROM books b " +
                "LEFT JOIN categories c ON b.category_id = c.category_id " +
                "LEFT JOIN publishers p ON b.publisher_id = p.publisher_id " +
                "WHERE b.book_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book book = mapRow(rs);
                book.setAuthors(authorDAO.getAuthorsByBookId(book.getBookId()));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(Book book) {
        String sql = "INSERT INTO books (title, isbn, category_id, publisher_id, publication_year, " +
                "price, selling_price, quantity, description, image_path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setBookParams(ps, book);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int bookId = rs.getInt(1);
                // Cập nhật liên kết tác giả
                if (book.getAuthors() != null) {
                    List<Integer> authorIds = new ArrayList<>();
                    for (Author a : book.getAuthors()) {
                        authorIds.add(a.getAuthorId());
                    }
                    authorDAO.updateBookAuthors(bookId, authorIds);
                }
                return bookId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean update(Book book) {
        String sql = "UPDATE books SET title=?, isbn=?, category_id=?, publisher_id=?, " +
                "publication_year=?, price=?, selling_price=?, quantity=?, " +
                "description=?, image_path=? WHERE book_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setBookParams(ps, book);
            ps.setInt(11, book.getBookId());
            boolean updated = ps.executeUpdate() > 0;
            if (updated && book.getAuthors() != null) {
                List<Integer> authorIds = new ArrayList<>();
                for (Author a : book.getAuthors()) {
                    authorIds.add(a.getAuthorId());
                }
                authorDAO.updateBookAuthors(book.getBookId(), authorIds);
            }
            return updated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        // Xóa liên kết trước (do ON DELETE CASCADE đã cấu hình, nhưng cứ xóa rõ ràng)
        authorDAO.updateBookAuthors(id, new ArrayList<>()); // xóa hết liên kết
        String sql = "DELETE FROM books WHERE book_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tìm kiếm sách theo nhiều tiêu chí (các tham số null thì bỏ qua)
    public List<Book> search(String keyword, Integer categoryId, Integer publisherId,
            BigDecimal minPrice, BigDecimal maxPrice, Integer authorId) {
        List<Book> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT b.*, c.category_name, p.publisher_name " +
                        "FROM books b " +
                        "LEFT JOIN categories c ON b.category_id = c.category_id " +
                        "LEFT JOIN publishers p ON b.publisher_id = p.publisher_id " +
                        "LEFT JOIN book_authors ba ON b.book_id = ba.book_id " +
                        "WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (b.title LIKE ? OR b.isbn LIKE ?) ");
            String kw = "%" + keyword + "%";
            params.add(kw);
            params.add(kw);
        }
        if (categoryId != null) {
            sql.append("AND b.category_id = ? ");
            params.add(categoryId);
        }
        if (publisherId != null) {
            sql.append("AND b.publisher_id = ? ");
            params.add(publisherId);
        }
        if (minPrice != null) {
            sql.append("AND b.selling_price >= ? ");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append("AND b.selling_price <= ? ");
            params.add(maxPrice);
        }
        if (authorId != null) {
            sql.append("AND ba.author_id = ? ");
            params.add(authorId);
        }

        sql.append("ORDER BY b.title");

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = mapRow(rs);
                book.setAuthors(authorDAO.getAuthorsByBookId(book.getBookId()));
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Helper map một dòng ResultSet thành Book (chưa có authors)
    private Book mapRow(ResultSet rs) throws SQLException {
        Book b = new Book();
        b.setBookId(rs.getInt("book_id"));
        b.setTitle(rs.getString("title"));
        b.setIsbn(rs.getString("isbn"));
        b.setCategoryId(rs.getInt("category_id"));
        b.setCategoryName(rs.getString("category_name"));
        b.setPublisherId(rs.getInt("publisher_id"));
        b.setPublisherName(rs.getString("publisher_name"));
        b.setPublicationYear(rs.getInt("publication_year"));
        b.setPrice(rs.getBigDecimal("price"));
        b.setSellingPrice(rs.getBigDecimal("selling_price"));
        b.setQuantity(rs.getInt("quantity"));
        b.setDescription(rs.getString("description"));
        b.setImagePath(rs.getString("image_path"));
        return b;
    }

    private void setBookParams(PreparedStatement ps, Book book) throws SQLException {
        ps.setString(1, book.getTitle());
        ps.setString(2, book.getIsbn());
        if (book.getCategoryId() > 0)
            ps.setInt(3, book.getCategoryId());
        else
            ps.setNull(3, Types.INTEGER);
        if (book.getPublisherId() > 0)
            ps.setInt(4, book.getPublisherId());
        else
            ps.setNull(4, Types.INTEGER);
        ps.setInt(5, book.getPublicationYear());
        ps.setBigDecimal(6, book.getPrice());
        ps.setBigDecimal(7, book.getSellingPrice());
        ps.setInt(8, book.getQuantity());
        ps.setString(9, book.getDescription());
        ps.setString(10, book.getImagePath());
    }

    // Cập nhật số lượng sách (tăng/giảm). Nếu connection được truyền vào, dùng
    // connection đó (transaction)
    public boolean updateQuantity(int bookId, int quantityChange, Connection conn) throws SQLException {
        String sql = "UPDATE books SET quantity = quantity + ? WHERE book_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, quantityChange); // số âm nếu giảm
        ps.setInt(2, bookId);
        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    // Overload để gọi độc lập (không transaction)
    public boolean updateQuantity(int bookId, int quantityChange) {
        try (Connection conn = DBConnection.getConnection()) {
            return updateQuantity(bookId, quantityChange, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}