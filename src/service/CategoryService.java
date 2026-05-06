package service;

import dao.CategoryDAO;
import model.Category;
import model.Session;

import java.util.List;

public class CategoryService {
    private CategoryDAO categoryDAO = new CategoryDAO();

    public List<Category> getAll() {
        return categoryDAO.getAll(); // Không yêu cầu quyền (dùng để hiển thị combobox)
    }

    public int add(Category c) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_CREATE")) {
            throw new AccessDeniedException("Bạn không có quyền thêm thể loại");
        }
        return categoryDAO.insert(c);
    }

    public boolean update(Category c) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_UPDATE")) {
            throw new AccessDeniedException("Bạn không có quyền sửa thể loại");
        }
        return categoryDAO.update(c);
    }

    public boolean delete(int id) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_DELETE")) {
            throw new AccessDeniedException("Bạn không có quyền xóa thể loại");
        }
        return categoryDAO.delete(id);
    }

    public static class AccessDeniedException extends Exception {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}