package service;

import dao.NhaXuatBanDAO;
import model.NhaXuatBan;

import java.util.List;

public class NhaXuatBanService {

    private NhaXuatBanDAO nxbDAO;

    public NhaXuatBanService() {
        nxbDAO = new NhaXuatBanDAO();
    }

    public List<NhaXuatBan> layDanhSach() {
        return nxbDAO.layTatCa();
    }
}