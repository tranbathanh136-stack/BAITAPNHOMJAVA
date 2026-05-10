package service;

import dao.ThongKeDAO;

import java.util.List;

public class ThongKeService {

    private ThongKeDAO thongKeDAO;

    public ThongKeService() {
        thongKeDAO = new ThongKeDAO();
    }

    public double tinhDoanhThu(String tuNgay, String denNgay) {
        return thongKeDAO.tinhDoanhThu(tuNgay, denNgay);
    }

    public double tinhLoiNhuan(String tuNgay, String denNgay) {
        return thongKeDAO.tinhLoiNhuan(tuNgay, denNgay);
    }

    public int tongHoaDon(String tuNgay, String denNgay) {
        return thongKeDAO.tongHoaDon(tuNgay, denNgay);
    }

    public int tongSachBan(String tuNgay, String denNgay) {
        return thongKeDAO.tongSachBan(tuNgay, denNgay);
    }

    public List<Object[]> topSachBanChay(String tuNgay, String denNgay) {
        return thongKeDAO.topSachBanChay(tuNgay, denNgay);
    }
}