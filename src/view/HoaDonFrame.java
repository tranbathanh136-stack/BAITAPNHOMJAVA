package view;

import model.KhachHang;
import model.NhaXuatBan;
import model.Sach;
import model.TheLoai;
import model.User;

import service.HoaDonService;
import service.KhachHangService;
import service.NhaXuatBanService;
import service.SachService;
import service.TheLoaiService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonFrame extends JFrame implements ActionListener, MouseListener {

    private User currentUser;

    private JTextField txtSDTKhachHang;
    private JButton btnTimKhachHang;
    private JLabel lblKhachHang;

    private JComboBox<TheLoai> cbTheLoai;
    private JComboBox<NhaXuatBan> cbNXB;

    private JTextField txtTimSach;
    private JTextField txtSoLuong;

    private JLabel lblNhanVien;
    private JLabel lblSachDangChon;
    private JLabel lblGia;
    private JLabel lblTon;
    private JLabel lblTongTien;

    private JButton btnThemGio;
    private JButton btnXoaDong;
    private JButton btnThanhToan;
    private JButton btnDong;

    private JTable tblSach;
    private JTable tblGioHang;

    private DefaultTableModel modelSach;
    private DefaultTableModel modelGioHang;

    private HoaDonService hoaDonService;
    private SachService sachService;
    private KhachHangService khachHangService;
    private TheLoaiService theLoaiService;
    private NhaXuatBanService nxbService;

    private List<Sach> dsSachDangHienThi;
    private List<Sach> dsSachTrongGio;
    private List<Integer> dsSoLuong;

    private Sach sachDangChon;
    private KhachHang khachHangDangChon;

    public HoaDonFrame(User user) {

        currentUser = user;

        hoaDonService = new HoaDonService();
        sachService = new SachService();
        khachHangService = new KhachHangService();
        theLoaiService = new TheLoaiService();
        nxbService = new NhaXuatBanService();

        dsSachDangHienThi = new ArrayList<Sach>();
        dsSachTrongGio = new ArrayList<Sach>();
        dsSoLuong = new ArrayList<Integer>();

        GUI();

        loadComboBoxLoc();
        locSach();
    }

    public void GUI() {

        setTitle("Ban sach");
        setSize(1100, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTieuDe = new JLabel("BAN SACH", JLabel.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 28));

        lblNhanVien = new JLabel(
                "Nhan vien: " + currentUser.getFullName(),
                JLabel.CENTER
        );

        txtSDTKhachHang = new JTextField();
        btnTimKhachHang = new JButton("Tim");

        lblKhachHang = new JLabel("Chua chon khach hang");

        cbTheLoai = new JComboBox<TheLoai>();
        cbNXB = new JComboBox<NhaXuatBan>();

        txtTimSach = new JTextField();
        txtSoLuong = new JTextField();

        lblSachDangChon = new JLabel("Sach dang chon: Chua chon");
        lblGia = new JLabel("Gia ban: 0");
        lblTon = new JLabel("Ton kho: 0");
        lblTongTien = new JLabel("Tong tien: 0");

        btnThemGio = new JButton("Them vao gio");
        btnXoaDong = new JButton("Xoa dong");
        btnThanhToan = new JButton("Thanh toan");
        btnDong = new JButton("Dong");

        btnThemGio.addActionListener(this);
        btnXoaDong.addActionListener(this);
        btnThanhToan.addActionListener(this);
        btnDong.addActionListener(this);
        btnTimKhachHang.addActionListener(this);

        cbTheLoai.addActionListener(this);
        cbNXB.addActionListener(this);

        txtTimSach.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                locSach();
            }

            public void removeUpdate(DocumentEvent e) {
                locSach();
            }

            public void changedUpdate(DocumentEvent e) {
                locSach();
            }
        });

        JPanel pnTitle = new JPanel(new GridLayout(2, 1));

        pnTitle.add(lblTieuDe);
        pnTitle.add(lblNhanVien);

        JPanel pnKhachHang = new JPanel(new GridLayout(2, 3, 10, 10));

        pnKhachHang.setBorder(
                BorderFactory.createTitledBorder("Thong tin khach hang")
        );

        pnKhachHang.add(new JLabel("SDT:"));
        pnKhachHang.add(txtSDTKhachHang);
        pnKhachHang.add(btnTimKhachHang);

        pnKhachHang.add(new JLabel("Khach hang:"));
        pnKhachHang.add(lblKhachHang);
        pnKhachHang.add(new JLabel(""));

        JPanel pnLoc = new JPanel(new GridLayout(3, 2, 10, 10));

        pnLoc.setBorder(
                BorderFactory.createTitledBorder("Loc sach")
        );

        pnLoc.add(new JLabel("Ten sach:"));
        pnLoc.add(txtTimSach);

        pnLoc.add(new JLabel("The loai:"));
        pnLoc.add(cbTheLoai);

        pnLoc.add(new JLabel("Nha xuat ban:"));
        pnLoc.add(cbNXB);

        JPanel pnTopContent = new JPanel(new BorderLayout(10, 10));

        pnTopContent.add(pnKhachHang, BorderLayout.NORTH);
        pnTopContent.add(pnLoc, BorderLayout.CENTER);

        modelSach = new DefaultTableModel();

        modelSach.addColumn("Ma sach");
        modelSach.addColumn("Ten sach");
        modelSach.addColumn("Gia ban");
        modelSach.addColumn("Ton kho");

        tblSach = new JTable(modelSach);

        tblSach.addMouseListener(this);

        JPanel pnDanhSachSach = new JPanel(new BorderLayout());

        pnDanhSachSach.setBorder(
                BorderFactory.createTitledBorder("Danh sach sach")
        );

        pnDanhSachSach.add(
                new JScrollPane(tblSach),
                BorderLayout.CENTER
        );

        JPanel pnChonSach = new JPanel(new GridLayout(2, 4, 10, 10));

        pnChonSach.setBorder(
                BorderFactory.createTitledBorder("Them vao gio")
        );

        pnChonSach.add(lblSachDangChon);
        pnChonSach.add(lblGia);
        pnChonSach.add(lblTon);
        pnChonSach.add(new JLabel(""));

        pnChonSach.add(new JLabel("So luong:"));
        pnChonSach.add(txtSoLuong);
        pnChonSach.add(btnThemGio);
        pnChonSach.add(new JLabel(""));

        JPanel pnSachVaChon = new JPanel(new BorderLayout(10, 10));

        pnSachVaChon.add(pnDanhSachSach, BorderLayout.CENTER);
        pnSachVaChon.add(pnChonSach, BorderLayout.SOUTH);

        modelGioHang = new DefaultTableModel();

        modelGioHang.addColumn("Ma sach");
        modelGioHang.addColumn("Ten sach");
        modelGioHang.addColumn("So luong");
        modelGioHang.addColumn("Gia ban");
        modelGioHang.addColumn("Thanh tien");

        tblGioHang = new JTable(modelGioHang);

        JPanel pnGioHang = new JPanel(new BorderLayout());

        pnGioHang.setBorder(
                BorderFactory.createTitledBorder("Gio hang")
        );

        pnGioHang.add(
                new JScrollPane(tblGioHang),
                BorderLayout.CENTER
        );

        JPanel pnCenter = new JPanel(new GridLayout(2, 1, 10, 10));

        pnCenter.add(pnSachVaChon);
        pnCenter.add(pnGioHang);

        JPanel pnNut = new JPanel(new FlowLayout());

        pnNut.add(btnXoaDong);
        pnNut.add(btnThanhToan);
        pnNut.add(btnDong);

        JPanel pnDuoi = new JPanel(new BorderLayout());

        pnDuoi.add(lblTongTien, BorderLayout.NORTH);
        pnDuoi.add(pnNut, BorderLayout.SOUTH);

        JPanel pnNoiDung = new JPanel(new BorderLayout(10, 10));

        pnNoiDung.add(pnTopContent, BorderLayout.NORTH);
        pnNoiDung.add(pnCenter, BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));

        add(pnTitle, BorderLayout.NORTH);
        add(pnNoiDung, BorderLayout.CENTER);
        add(pnDuoi, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void loadComboBoxLoc() {

        cbTheLoai.removeAllItems();
        cbNXB.removeAllItems();

        cbTheLoai.addItem(new TheLoai(0, "Tat ca"));
        cbNXB.addItem(new NhaXuatBan(0, "Tat ca"));

        List<TheLoai> dsTheLoai =
                theLoaiService.layDanhSach();

        for (TheLoai tl : dsTheLoai) {
            cbTheLoai.addItem(tl);
        }

        List<NhaXuatBan> dsNXB =
                nxbService.layDanhSach();

        for (NhaXuatBan nxb : dsNXB) {
            cbNXB.addItem(nxb);
        }
    }

    public void timKhachHang() {

        String sdt =
                txtSDTKhachHang.getText().trim();

        if (sdt.equals("")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nhap so dien thoai"
            );

            return;
        }

        KhachHang kh =
                khachHangService.timTheoSDT(sdt);

        if (kh == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Khong tim thay khach hang"
            );

            return;
        }

        khachHangDangChon = kh;

        lblKhachHang.setText(
                kh.getHoTen()
                        + " - "
                        + kh.getSoDienThoai()
        );
    }

    public void locSach() {

        modelSach.setRowCount(0);

        dsSachDangHienThi.clear();

        String tenSach =
                txtTimSach.getText();

        int maTheLoai = 0;
        int maNXB = 0;

        TheLoai tl =
                (TheLoai) cbTheLoai.getSelectedItem();

        NhaXuatBan nxb =
                (NhaXuatBan) cbNXB.getSelectedItem();

        if (tl != null) {
            maTheLoai = tl.getMaTheLoai();
        }

        if (nxb != null) {
            maNXB = nxb.getMaNXB();
        }

        List<Sach> ds =
                sachService.timKiem(
                        tenSach,
                        maTheLoai,
                        maNXB
                );

        for (Sach s : ds) {

            dsSachDangHienThi.add(s);

            modelSach.addRow(new Object[]{
                    s.getMaSach(),
                    s.getTenSach(),
                    s.getGiaBan(),
                    s.getSoLuong()
            });
        }

        resetSachDangChon();
    }

    public void resetSachDangChon() {

        sachDangChon = null;

        lblSachDangChon.setText(
                "Sach dang chon: Chua chon"
        );

        lblGia.setText("Gia ban: 0");
        lblTon.setText("Ton kho: 0");
    }

    public void chonSachTuBang() {

        int row = tblSach.getSelectedRow();

        if (row < 0) {
            return;
        }

        sachDangChon =
                dsSachDangHienThi.get(row);

        lblSachDangChon.setText(
                "Sach dang chon: "
                        + sachDangChon.getTenSach()
        );

        lblGia.setText(
                "Gia ban: "
                        + sachDangChon.getGiaBan()
        );

        lblTon.setText(
                "Ton kho: "
                        + sachDangChon.getSoLuong()
        );
    }

    public boolean kiemTraThemGio() {

        if (sachDangChon == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Chon sach trong bang truoc"
            );

            return false;
        }

        if (txtSoLuong.getText().trim().equals("")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nhap so luong"
            );

            return false;
        }

        try {

            int soLuong =
                    Integer.parseInt(
                            txtSoLuong.getText()
                    );

            if (soLuong <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "So luong phai > 0"
                );

                return false;
            }

            if (soLuong > sachDangChon.getSoLuong()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vuot qua ton kho"
                );

                return false;
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "So luong khong hop le"
            );

            return false;
        }

        return true;
    }

    public void themVaoGio() {

        if (!kiemTraThemGio()) {
            return;
        }

        int soLuong =
                Integer.parseInt(
                        txtSoLuong.getText()
                );

        for (int i = 0; i < dsSachTrongGio.size(); i++) {

            if (dsSachTrongGio.get(i).getMaSach()
                    == sachDangChon.getMaSach()) {

                int slMoi =
                        dsSoLuong.get(i) + soLuong;

                if (slMoi > sachDangChon.getSoLuong()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Vuot ton kho"
                    );

                    return;
                }

                dsSoLuong.set(i, slMoi);

                loadGioHang();

                txtSoLuong.setText("");

                return;
            }
        }

        dsSachTrongGio.add(sachDangChon);
        dsSoLuong.add(soLuong);

        loadGioHang();

        txtSoLuong.setText("");
    }

    public void loadGioHang() {

        modelGioHang.setRowCount(0);

        double tongTien = 0;

        for (int i = 0; i < dsSachTrongGio.size(); i++) {

            Sach s = dsSachTrongGio.get(i);

            int soLuong = dsSoLuong.get(i);

            double thanhTien =
                    soLuong * s.getGiaBan();

            tongTien += thanhTien;

            modelGioHang.addRow(new Object[]{
                    s.getMaSach(),
                    s.getTenSach(),
                    soLuong,
                    s.getGiaBan(),
                    thanhTien
            });
        }

        lblTongTien.setText(
                "Tong tien: " + tongTien
        );
    }

    public void xoaDong() {

        int row =
                tblGioHang.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Chon dong can xoa"
            );

            return;
        }

        dsSachTrongGio.remove(row);
        dsSoLuong.remove(row);

        loadGioHang();
    }

    public void thanhToan() {

        if (khachHangDangChon == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Chua chon khach hang"
            );

            return;
        }

        if (dsSachTrongGio.size() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Gio hang rong"
            );

            return;
        }

        boolean ketQua =
                hoaDonService.taoHoaDonNhieuSach(
                        khachHangDangChon.getMaKhachHang(),
                        currentUser.getUserId(),
                        dsSachTrongGio,
                        dsSoLuong
                );

        if (ketQua) {

            JOptionPane.showMessageDialog(
                    this,
                    "Thanh toan thanh cong"
            );

            dsSachTrongGio.clear();
            dsSoLuong.clear();

            loadGioHang();

            locSach();

            txtSoLuong.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Thanh toan that bai"
            );
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnTimKhachHang) {
            timKhachHang();
        }

        if (e.getSource() == cbTheLoai
                || e.getSource() == cbNXB) {

            locSach();
        }

        if (e.getSource() == btnThemGio) {
            themVaoGio();
        }

        if (e.getSource() == btnXoaDong) {
            xoaDong();
        }

        if (e.getSource() == btnThanhToan) {
            thanhToan();
        }

        if (e.getSource() == btnDong) {
            dispose();
        }
    }

    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == tblSach) {
            chonSachTuBang();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}