package service;

import dao.KhachHangDAO;
import model.KhachHang;

import java.util.List;

public class KhachHangService {
    private KhachHangDAO khachHangDAO;

    public KhachHangService() {
        khachHangDAO = new KhachHangDAO();
    }

    public List<KhachHang> layDanhSach() {
        return khachHangDAO.layTatCa();
    }

    public boolean themKhachHang(KhachHang kh) {
        return khachHangDAO.them(kh) > 0;
    }

    public boolean suaKhachHang(KhachHang kh) {
        return khachHangDAO.sua(kh) > 0;
    }

    public boolean xoaKhachHang(int maKhachHang) {
        return khachHangDAO.xoa(maKhachHang) > 0;
    }
}