package model;

import java.math.BigDecimal;

public class TopSellingBook {
    private int bookId;
    private String title;
    private int totalQuantitySold;
    private BigDecimal totalRevenue;

    // constructors, getters, setters
    public TopSellingBook() {
    }

    public TopSellingBook(int bookId, String title, int totalQuantitySold, BigDecimal totalRevenue) {
        this.bookId = bookId;
        this.title = title;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public void setTotalQuantitySold(int totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}