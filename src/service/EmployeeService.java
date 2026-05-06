package service;

import dao.EmployeeDAO;
import model.Employee;
import model.Session;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        employeeDAO = new EmployeeDAO();
    }

    public List<Employee> getAllEmployees() throws AccessDeniedException {
        if (!Session.hasPermission("EMPLOYEE_MANAGE")) {
            throw new AccessDeniedException("Bạn không có quyền quản lý nhân viên");
        }
        return employeeDAO.getAll();
    }

    public Employee getEmployeeById(int id) throws AccessDeniedException {
        if (!Session.hasPermission("EMPLOYEE_MANAGE")) {
            throw new AccessDeniedException("Bạn không có quyền quản lý nhân viên");
        }
        return employeeDAO.getById(id);
    }

    public int createEmployee(Employee emp, String rawPassword) throws AccessDeniedException, SQLException {
        if (!Session.hasPermission("EMPLOYEE_MANAGE")) {
            throw new AccessDeniedException("Bạn không có quyền thêm nhân viên");
        }
        // Validate (có thể thêm)
        return employeeDAO.create(emp, rawPassword);
    }

    public boolean updateEmployee(Employee emp) throws AccessDeniedException, SQLException {
        if (!Session.hasPermission("EMPLOYEE_MANAGE")) {
            throw new AccessDeniedException("Bạn không có quyền sửa nhân viên");
        }
        return employeeDAO.update(emp);
    }

    public boolean resetPassword(int userId, String newPassword) throws AccessDeniedException {
        if (!Session.hasPermission("EMPLOYEE_MANAGE")) {
            throw new AccessDeniedException("Bạn không có quyền reset mật khẩu");
        }
        return employeeDAO.resetPassword(userId, newPassword);
    }

    public boolean deleteEmployee(int employeeId, int userId) throws AccessDeniedException {
        if (!Session.hasPermission("EMPLOYEE_MANAGE")) {
            throw new AccessDeniedException("Bạn không có quyền xóa nhân viên");
        }
        return employeeDAO.delete(employeeId, userId);
    }

    public static class AccessDeniedException extends Exception {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}