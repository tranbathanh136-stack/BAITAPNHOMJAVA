package service;

import dao.AuthorDAO;
import dao.BookDAO;
import model.Author;
import model.Book;
import security.Session;

import java.math.BigDecimal;
import java.util.List;

public class BookService {

    private BookDAO bookDAO;
    private AuthorDAO authorDAO;

    public BookService() {
        bookDAO = new BookDAO();
        authorDAO = new AuthorDAO();
    }

    public List<Book> getAllBooks() {
        // không cần quyền, ai cũng xem được
        return bookDAO.getAll();
    }

    public Book getBookById(int id) {
        return bookDAO.getById(id);
    }

    public List<Book> searchBooks(String keyword, Integer categoryId, Integer publisherId,
            BigDecimal minPrice, BigDecimal maxPrice, Integer authorId) {
        return bookDAO.search(keyword, categoryId, publisherId, minPrice, maxPrice, authorId);
    }

    public int addBook(Book book) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_CREATE")) {
            throw new AccessDeniedException("Bạn không có quyền thêm sách");
        }
        return bookDAO.insert(book);
    }

    public boolean updateBook(Book book) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_UPDATE")) {
            throw new AccessDeniedException("Bạn không có quyền sửa sách");
        }

        // Nếu người dùng không có quyền sửa giá, kiểm tra xem giá có thay đổi không
        if (!Session.hasPermission("BOOK_UPDATE_PRICE")) {
            Book existing = bookDAO.getById(book.getBookId());
            if (existing != null &&
                    book.getSellingPrice().compareTo(existing.getSellingPrice()) != 0) {
                throw new AccessDeniedException("Bạn không có quyền thay đổi giá sách");
            }
        }

        return bookDAO.update(book);
    }

    public boolean deleteBook(int id) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_DELETE")) {
            throw new AccessDeniedException("Bạn không có quyền xóa sách");
        }
        return bookDAO.delete(id);
    }

    // Các phương thức tiện ích lấy danh sách category, publisher, author (không cần
    // kiểm tra quyền)
    public List<model.Category> getAllCategories() {
        return new dao.CategoryDAO().getAll();
    }

    public List<model.Publisher> getAllPublishers() {
        return new dao.PublisherDAO().getAll();
    }

    public List<Author> getAllAuthors() {
        return authorDAO.getAll();
    }

    // Exception tùy chỉnh
    public static class AccessDeniedException extends Exception {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}