package model;

import java.math.BigDecimal;

public class InventoryStatus {
    private int bookId;
    private String title;
    private int currentQuantity;
    private BigDecimal sellingPrice;

    // constructors, getters, setters
    public InventoryStatus() {
    }

    public InventoryStatus(int bookId, String title, int currentQuantity, BigDecimal sellingPrice) {
        this.bookId = bookId;
        this.title = title;
        this.currentQuantity = currentQuantity;
        this.sellingPrice = sellingPrice;
    }

    // getters & setters...
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

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}