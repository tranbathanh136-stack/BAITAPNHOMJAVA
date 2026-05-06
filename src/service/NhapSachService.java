package service;

import dao.*;
import model.*;

public class NhapSachService {
    private PhieuNhapDAO pnDAO;
    private ChiTietNhapDAO ctDAO;
    private SachDAO sachDAO;

    public NhapSachService() {
        pnDAO = new PhieuNhapDAO();
        ctDAO = new ChiTietNhapDAO();
        sachDAO = new SachDAO();
    }

    public boolean nhap(int maNCC, int maNV, Sach s, int soLuong, double giaNhap) {

        try {
            double tongTien = soLuong * giaNhap;

            // thêm phiếu nhập
            PhieuNhap pn = new PhieuNhap(maNCC, maNV, tongTien);

            int maPN = pnDAO.them(pn);

            if (maPN <= 0) {
                return false;
            }

            // thêm chi tiết nhập
            ChiTietNhap ct = new ChiTietNhap(
                    maPN,
                    s.getMaSach(),
                    soLuong,
                    giaNhap
            );

            ctDAO.them(ct);

            
            int soLuongMoi = s.getSoLuong() + soLuong;

            if (sachDAO.capNhatSoLuong(s.getMaSach(), soLuongMoi) > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}