package service;

import dao.NhanVienDAO;
import model.NhanVien;

import java.util.List;

public class NhanVienService {

    private NhanVienDAO nhanVienDAO;

    public NhanVienService() {
        nhanVienDAO = new NhanVienDAO();
    }

    public List<NhanVien> layDanhSach() {
        return nhanVienDAO.layTatCa();
    }

    public List<NhanVien> timTheoTen(String tuKhoa) {
        return nhanVienDAO.timTheoTen(tuKhoa);
    }

    public boolean themNhanVien(NhanVien nv) {
        return nhanVienDAO.them(nv) > 0;
    }

    public boolean suaNhanVien(NhanVien nv) {
        return nhanVienDAO.sua(nv) > 0;
    }

    public boolean xoaNhanVien(int maNhanVien) {
        return nhanVienDAO.xoa(maNhanVien) > 0;
    }
}