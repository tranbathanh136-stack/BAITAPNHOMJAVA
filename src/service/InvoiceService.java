package service;

import dao.BookDAO;
import dao.InvoiceDAO;
import dao.InvoiceDetailDAO;
import model.*;
import security.Session;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InvoiceService {

    private InvoiceDAO invoiceDAO;
    private InvoiceDetailDAO invoiceDetailDAO;
    private BookDAO bookDAO;

    public InvoiceService() {
        invoiceDAO = new InvoiceDAO();
        invoiceDetailDAO = new InvoiceDetailDAO();
        bookDAO = new BookDAO();
    }

    /**
     * Tạo hóa đơn bán hàng từ giỏ hàng.
     * 
     * @param cart       giỏ hàng chứa các CartItem
     * @param customerId mã khách hàng, có thể null (khách lẻ)
     * @param employeeId mã nhân viên bán hàng
     * @param discount   số tiền giảm giá (>=0)
     * @return Invoice đã tạo
     * @throws Exception nếu có lỗi nghiệp vụ (không đủ hàng, không quyền, ...)
     */
    public Invoice createInvoice(Cart cart, Integer customerId, int employeeId, BigDecimal discount) throws Exception {
        // 1. Kiểm tra quyền
        if (!Session.hasPermission("SELL")) {
            throw new AccessDeniedException("Bạn không có quyền bán hàng");
        }

        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng trống");
        }

        // 2. Kiểm tra tồn kho và khóa giá bán
        for (CartItem item : cart.getItems()) {
            Book book = bookDAO.getById(item.getBookId());
            if (book == null) {
                throw new Exception("Sách mã " + item.getBookId() + " không tồn tại");
            }
            if (book.getQuantity() < item.getQuantity()) {
                throw new Exception(
                        "Sách '" + book.getTitle() + "' không đủ số lượng (còn " + book.getQuantity() + ")");
            }
            // Cập nhật đơn giá trong CartItem bằng giá bán hiện tại từ DB (phòng trường hợp
            // giá thay đổi)
            item.setUnitPrice(book.getSellingPrice());
        }

        // 3. Tính tổng tiền
        BigDecimal totalAmount = cart.getTotalAmount();
        if (discount == null)
            discount = BigDecimal.ZERO;
        BigDecimal finalAmount = totalAmount.subtract(discount);
        if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Số tiền sau giảm giá không thể âm");
        }

        // 4. Thực hiện transaction
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // a. Tạo hóa đơn
            Invoice invoice = new Invoice();
            invoice.setCustomerId(customerId);
            invoice.setEmployeeId(employeeId);
            invoice.setTotalAmount(totalAmount);
            invoice.setDiscount(discount);
            invoice.setFinalAmount(finalAmount);
            int invoiceId = invoiceDAO.insert(invoice, conn);
            if (invoiceId <= 0)
                throw new SQLException("Không tạo được hóa đơn");
            invoice.setInvoiceId(invoiceId);

            // b. Thêm chi tiết hóa đơn và cập nhật tồn kho
            for (CartItem item : cart.getItems()) {
                InvoiceDetail detail = new InvoiceDetail();
                detail.setInvoiceId(invoiceId);
                detail.setBookId(item.getBookId());
                detail.setQuantity(item.getQuantity());
                detail.setUnitPrice(item.getUnitPrice());
                invoiceDetailDAO.insert(detail, conn);

                // Giảm số lượng sách
                bookDAO.updateQuantity(item.getBookId(), -item.getQuantity(), conn);
            }

            // c. Commit
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

    public Invoice getInvoiceById(int invoiceId) {
        return invoiceDAO.getById(invoiceId);
    }

    public List<InvoiceDetail> getInvoiceDetails(int invoiceId) {
        return invoiceDetailDAO.getByInvoiceId(invoiceId);
    }

    // Exception tùy chỉnh
    public static class AccessDeniedException extends Exception {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}