package model;

public class Employee {
    private int employeeId;
    private int userId;
    private String username;
    private String fullName;
    private String roleName;
    private int roleId;
    private String phone;
    private String email;
    private String address;
    private String position;

    // Constructors
    public Employee() {
    }

    public Employee(int employeeId, int userId, String username, String fullName,
            int roleId, String roleName, String phone, String email,
            String address, String position) {
        this.employeeId = employeeId;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.roleId = roleId;
        this.roleName = roleName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.position = position;
    }

    // Getters và Setters đầy đủ
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return fullName + " (" + username + ")";
    }
}