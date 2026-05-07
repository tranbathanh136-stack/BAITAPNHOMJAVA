package service;

import dao.TheLoaiDAO;
import model.TheLoai;

import java.util.List;

public class TheLoaiService {
    private TheLoaiDAO theLoaiDAO;

    public TheLoaiService() {
        theLoaiDAO = new TheLoaiDAO();
    }

    public List<TheLoai> layDanhSach() {
        return theLoaiDAO.layTatCa();
    }
}