package service;

import dao.SachDAO;
import model.Sach;

import java.util.List;

public class SachService {
    private SachDAO sachDAO;

    public SachService() {
        sachDAO = new SachDAO();
    }

    public List<Sach> layDanhSach() {
        return sachDAO.layTatCa();
    }

    public boolean themSach(Sach s) {
        return sachDAO.them(s) > 0;
    }

    public boolean suaSach(Sach s) {
        return sachDAO.sua(s) > 0;
    }

    public boolean xoaSach(int maSach) {
        return sachDAO.xoa(maSach) > 0;
    }
}