package model;

import java.math.BigDecimal;
import java.util.List;

public class Book {
    private int bookId;
    private String title;
    private String isbn;
    private int categoryId;
    private String categoryName; // để hiển thị
    private int publisherId;
    private String publisherName;
    private int publicationYear;
    private BigDecimal price;
    private BigDecimal sellingPrice;
    private int quantity;
    private String description;
    private String imagePath;
    private List<Author> authors; // danh sách tác giả

    public Book() {
    }

    public Book(int bookId, String title, String isbn, int categoryId, String categoryName,
            int publisherId, String publisherName, int publicationYear,
            BigDecimal price, BigDecimal sellingPrice, int quantity,
            String description, String imagePath) {
        this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.publicationYear = publicationYear;
        this.price = price;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.description = description;
        this.imagePath = imagePath;
    }

    // Getters & Setters đầy đủ
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return title;
    }
}