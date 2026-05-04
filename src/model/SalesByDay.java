package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesByDay {
    private LocalDate saleDate;
    private int totalInvoices;
    private BigDecimal totalRevenue;
    private BigDecimal totalProfit; // có thể tính sau

    // constructors, getters, setters
    public SalesByDay() {
    }

    public SalesByDay(LocalDate saleDate, int totalInvoices, BigDecimal totalRevenue) {
        this.saleDate = saleDate;
        this.totalInvoices = totalInvoices;
        this.totalRevenue = totalRevenue;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public int getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(int totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }
}