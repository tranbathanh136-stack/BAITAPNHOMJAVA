package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(CartItem item) {
        // Nếu đã có sách cùng ID thì tăng số lượng
        for (CartItem ci : items) {
            if (ci.getBookId() == item.getBookId()) {
                ci.setQuantity(ci.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(int bookId) {
        items.removeIf(ci -> ci.getBookId() == bookId);
    }

    public void updateQuantity(int bookId, int newQuantity) {
        for (CartItem ci : items) {
            if (ci.getBookId() == bookId) {
                if (newQuantity <= 0) {
                    removeItem(bookId);
                } else {
                    ci.setQuantity(newQuantity);
                }
                return;
            }
        }
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public BigDecimal getTotalAmount() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : items) {
            total = total.add(ci.getSubtotal());
        }
        return total;
    }

    public int getTotalItems() {
        int total = 0;
        for (CartItem ci : items) {
            total += ci.getQuantity();
        }
        return total;
    }
}