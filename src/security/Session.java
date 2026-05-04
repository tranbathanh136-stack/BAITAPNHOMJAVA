package security;

import model.User;

public class Session {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // Kiểm tra vai trò hiện tại
    public static String getRole() {
        if (currentUser != null) {
            return currentUser.getRoleName();
        }
        return null;
    }

    // Kiểm tra quyền cụ thể (sẽ phát triển thêm ở các ngày sau)
    public static boolean hasPermission(String permission) {
        if (currentUser == null)
            return false;
        String role = currentUser.getRoleName();

        switch (permission) {
            case "BOOK_CREATE":
            case "BOOK_DELETE":
            case "BOOK_UPDATE_PRICE":
            case "IMPORT":
                return "MANAGER".equalsIgnoreCase(role);
            case "EMPLOYEE_MANAGE":
            case "STATISTIC_VIEW":
                return "MANAGER".equalsIgnoreCase(role);
            case "BOOK_UPDATE":
                // cả 2 role đều có quyền sửa, nhưng Staff bị hạn chế sửa giá (sẽ kiểm tra
                // riêng)
                return true;
            case "SELL":
                return true;
            case "CUSTOMER_MANAGE":
                return true;
            default:
                return false;
        }
    }
}