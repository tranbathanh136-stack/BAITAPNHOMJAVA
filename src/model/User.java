package model;

public class User {
    private int userId;
    private String username;
    private String password; // đã hash
    private String fullName;
    private int roleId;
    private String roleName; // join từ bảng roles, để hiển thị

    public User() {
    }

    public User(int userId, String username, String password, String fullName, int roleId, String roleName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    // Getters và Setters đầy đủ
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return fullName + " (" + roleName + ")";
    }
}