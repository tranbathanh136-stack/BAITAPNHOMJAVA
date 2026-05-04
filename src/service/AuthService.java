package service;

import dao.UserDAO;
import model.User;
import security.Session;
import util.PasswordUtils;

public class AuthService {
    private UserDAO userDAO;

    public AuthService() {
        userDAO = new UserDAO();
    }

    // Trả về đối tượng User nếu đăng nhập thành công, ngược lại null
    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null) {
            // Kiểm tra mật khẩu (dùng BCrypt)
            if (PasswordUtils.checkPassword(password, user.getPassword())) {
                // Lưu vào session
                Session.setCurrentUser(user);
                return user;
            }
        }
        return null;
    }

    public void logout() {
        Session.clear();
    }
}