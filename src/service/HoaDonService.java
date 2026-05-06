package service;

import dao.HoaDonDAO;
import dao.HoaDonChiTietDAO;
import dao.SachDAO;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.Sach;

public class HoaDonService {
    private HoaDonDAO hoaDonDAO;
    private HoaDonChiTietDAO chiTietDAO;
    private SachDAO sachDAO;

    public HoaDonService() {
        hoaDonDAO = new HoaDonDAO();
        chiTietDAO = new HoaDonChiTietDAO();
        sachDAO = new SachDAO();
    }

    public boolean taoHoaDon(int maKhachHang, int maNhanVien, Sach sach, int soLuong) {
        try {
            if (sach == null) {
                return false;
            }

            if (soLuong <= 0 || soLuong > sach.getSoLuong()) {
                return false;
            }

            double tongTien = sach.getGiaBan() * soLuong;

            HoaDon hoaDon = new HoaDon(maKhachHang, maNhanVien, tongTien);
            int maHoaDon = hoaDonDAO.them(hoaDon);

            if (maHoaDon <= 0) {
                return false;
            }

            HoaDonChiTiet chiTiet = new HoaDonChiTiet(maHoaDon, sach.getMaSach(), soLuong, sach.getGiaBan());
            chiTietDAO.them(chiTiet);

            sach.setSoLuong(sach.getSoLuong() - soLuong);
            sachDAO.sua(sach);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}