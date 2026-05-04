package service;

import dao.AuthorDAO;
import model.Author;
import security.Session;
import java.util.List;

public class AuthorService {
    private AuthorDAO authorDAO = new AuthorDAO();

    public List<Author> getAll() {
        return authorDAO.getAll();
    }

    public int add(Author a) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_CREATE"))
            throw new AccessDeniedException("Không có quyền thêm tác giả");
        return authorDAO.insert(a);
    }

    public boolean update(Author a) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_UPDATE"))
            throw new AccessDeniedException("Không có quyền sửa tác giả");
        return authorDAO.update(a);
    }

    public boolean delete(int id) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_DELETE"))
            throw new AccessDeniedException("Không có quyền xóa tác giả");
        return authorDAO.delete(id);
    }

    public static class AccessDeniedException extends Exception {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}