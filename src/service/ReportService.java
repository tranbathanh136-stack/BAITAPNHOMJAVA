package service;

import dao.ReportDAO;
import model.InventoryStatus;
import model.SalesByDay;
import model.Session;
import model.TopSellingBook;

import java.sql.Date;
import java.util.List;

public class ReportService {
    private ReportDAO reportDAO;

    public ReportService() {
        reportDAO = new ReportDAO();
    }

    public List<SalesByDay> getSalesByDay(java.util.Date from, java.util.Date to) throws AccessDeniedException {
        if (!Session.hasPermission("STATISTIC_VIEW")) {
            throw new AccessDeniedException("Bạn không có quyền xem thống kê");
        }
        Date sqlFrom = new Date(from.getTime());
        Date sqlTo = new Date(to.getTime());
        return reportDAO.getSalesByDay(sqlFrom, sqlTo);
    }

    public List<TopSellingBook> getTopSellingBooks(int limit) throws AccessDeniedException {
        if (!Session.hasPermission("STATISTIC_VIEW")) {
            throw new AccessDeniedException("Bạn không có quyền xem thống kê");
        }
        return reportDAO.getTopSellingBooks(limit);
    }

    public List<InventoryStatus> getInventoryStatus() throws AccessDeniedException {
        if (!Session.hasPermission("STATISTIC_VIEW")) {
            throw new AccessDeniedException("Bạn không có quyền xem thống kê");
        }
        return reportDAO.getInventoryStatus();
    }

    public static class AccessDeniedException extends Exception {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}