package service;

import dao.PublisherDAO;
import model.Publisher;
import model.Session;

import java.util.List;

public class PublisherService {
    private PublisherDAO publisherDAO = new PublisherDAO();

    public List<Publisher> getAll() {
        return publisherDAO.getAll();
    }

    public int add(Publisher p) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_CREATE"))
            throw new AccessDeniedException("Không có quyền thêm NXB");
        return publisherDAO.insert(p);
    }

    public boolean update(Publisher p) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_UPDATE"))
            throw new AccessDeniedException("Không có quyền sửa NXB");
        return publisherDAO.update(p);
    }

    public boolean delete(int id) throws AccessDeniedException {
        if (!Session.hasPermission("BOOK_DELETE"))
            throw new AccessDeniedException("Không có quyền xóa NXB");
        return publisherDAO.delete(id);
    }

    public static class AccessDeniedException extends Exception {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}