package service;

import dao.*;
import model.*;
import security.Session;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ImportService {

    private ImportInvoiceDAO importInvoiceDAO;
    private ImportDetailDAO importDetailDAO;
    private BookDAO bookDAO;
    private SupplierDAO supplierDAO;

    public ImportService() {
        importInvoiceDAO = new ImportInvoiceDAO();
        importDetailDAO = new ImportDetailDAO();
        bookDAO = new BookDAO();
        supplierDAO = new SupplierDAO();
    }

    /**
     * Tạo phiếu nhập sách.
     * 
     * @param items      danh sách ImportDetail (chưa có importId)
     * @param supplierId nhà cung cấp
     * @param employeeId nhân viên lập phiếu (Manager)
     * @return ImportInvoice đã tạo
     * @throws Exception lỗi nghiệp vụ
     */
    public ImportInvoice createImport(List<ImportDetail> items, int supplierId, int employeeId) throws Exception {
        // Kiểm tra quyền
        if (!Session.hasPermission("IMPORT")) {
            throw new AccessDeniedException("Bạn không có quyền nhập sách");
        }

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Danh sách nhập trống");
        }

        // Kiểm tra nhà cung cấp tồn tại
        Supplier supplier = supplierDAO.getById(supplierId);
        if (supplier == null) {
            throw new Exception("Nhà cung cấp không tồn tại");
        }

        // Tính tổng chi phí (subtotal = quantity * importPrice)
        BigDecimal totalCost = BigDecimal.ZERO;
        for (ImportDetail item : items) {
            // Kiểm tra sách tồn tại
            Book book = bookDAO.getById(item.getBookId());
            if (book == null) {
                throw new Exception("Sách mã " + item.getBookId() + " không tồn tại");
            }
            if (item.getQuantity() <= 0) {
                throw new Exception("Số lượng nhập phải > 0");
            }
            item.setSubtotal(item.getImportPrice().multiply(new BigDecimal(item.getQuantity())));
            totalCost = totalCost.add(item.getSubtotal());
        }

        // Transaction
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Tạo phiếu nhập
            ImportInvoice invoice = new ImportInvoice();
            invoice.setSupplierId(supplierId);
            invoice.setEmployeeId(employeeId);
            invoice.setTotalCost(totalCost);
            int importId = importInvoiceDAO.insert(invoice, conn);
            invoice.setImportId(importId);

            // Lưu chi tiết và tăng số lượng sách
            for (ImportDetail item : items) {
                item.setImportId(importId);
                importDetailDAO.insert(item, conn);

                // Cập nhật số lượng sách (tăng)
                bookDAO.updateQuantity(item.getBookId(), item.getQuantity(), conn);
            }

            conn.commit();
            return invoice;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public ImportInvoice getImportById(int importId) {
        return importInvoiceDAO.getById(importId);
    }

    public List<ImportDetail> getImportDetails(int importId) {
        return importDetailDAO.getByImportId(importId);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAll();
    }

    public static class AccessDeniedException extends Exception {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}