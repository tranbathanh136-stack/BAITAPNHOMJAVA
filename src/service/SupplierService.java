package service;

import dao.SupplierDAO;
import model.Supplier;

import java.util.List;

public class SupplierService {
    private SupplierDAO supplierDAO;

    public SupplierService() {
        supplierDAO = new SupplierDAO();
    }

    public List<Supplier> layDanhSach() {
        return supplierDAO.layTatCa();
    }
}