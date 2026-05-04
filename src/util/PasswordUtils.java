package util;

import org.mindrot.jbcrypt.BCrypt; // cần thư viện jbcrypt

public class PasswordUtils {
    // Hash mật khẩu
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Kiểm tra mật khẩu
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}