package service;

import dao.SupplierDAO;
import model.Supplier;
import security.Session;
import java.util.List;

public class SupplierService {
    private SupplierDAO supplierDAO = new SupplierDAO();

    public List<Supplier> getAll() {
        return supplierDAO.getAll();
    }

    public int add(Supplier s) throws AccessDeniedException {
        if (!Session.hasPermission("IMPORT"))
            throw new AccessDeniedException("Không có quyền thêm nhà cung cấp");
        return supplierDAO.insert(s);
    }

    public boolean update(Supplier s) throws AccessDeniedException {
        if (!Session.hasPermission("IMPORT"))
            throw new AccessDeniedException("Không có quyền sửa nhà cung cấp");
        return supplierDAO.update(s);
    }

    public boolean delete(int id) throws AccessDeniedException {
        if (!Session.hasPermission("IMPORT"))
            throw new AccessDeniedException("Không có quyền xóa nhà cung cấp");
        return supplierDAO.delete(id);
    }

    public static class AccessDeniedException extends Exception {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}