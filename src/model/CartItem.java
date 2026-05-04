package model;

import java.math.BigDecimal;

public class CartItem {
    private int bookId;
    private String bookTitle; // để hiển thị, có thể lấy từ Book
    private int quantity;
    private BigDecimal unitPrice; // selling_price tại thời điểm thêm

    public CartItem(int bookId, String bookTitle, int quantity, BigDecimal unitPrice) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters & Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}