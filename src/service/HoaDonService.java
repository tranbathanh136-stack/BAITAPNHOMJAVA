package service;

import dao.HoaDonChiTietDAO;
import dao.HoaDonDAO;
import dao.SachDAO;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.Sach;

import java.util.List;

public class HoaDonService {

    private HoaDonDAO hoaDonDAO;
    private HoaDonChiTietDAO hoaDonChiTietDAO;
    private SachDAO sachDAO;

    public HoaDonService() {

        hoaDonDAO = new HoaDonDAO();
        hoaDonChiTietDAO = new HoaDonChiTietDAO();
        sachDAO = new SachDAO();
    }

    public boolean taoHoaDonNhieuSach(
            int maKhachHang,
            int maNhanVien,
            List<Sach> dsSach,
            List<Integer> dsSoLuong
    ) {

        try {

            double tongTien = 0;

            for (int i = 0; i < dsSach.size(); i++) {

                Sach sach = dsSach.get(i);

                int soLuong = dsSoLuong.get(i);

                tongTien = tongTien + sach.getGiaBan() * soLuong;
            }

            HoaDon hd = new HoaDon(
                    maKhachHang,
                    maNhanVien,
                    tongTien
            );

            int maHoaDon = hoaDonDAO.them(hd);

            if (maHoaDon <= 0) {
                return false;
            }

            for (int i = 0; i < dsSach.size(); i++) {

                Sach sach = dsSach.get(i);

                int soLuong = dsSoLuong.get(i);

                HoaDonChiTiet ct = new HoaDonChiTiet(
                        maHoaDon,
                        sach.getMaSach(),
                        soLuong,
                        sach.getGiaBan()
                );

                hoaDonChiTietDAO.them(ct);

                int soLuongMoi = sach.getSoLuong() - soLuong;

                sachDAO.capNhatSoLuong(
                        sach.getMaSach(),
                        soLuongMoi
                );
            }

            return true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}