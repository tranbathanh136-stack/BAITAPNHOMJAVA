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
import javax.swing.border.TitledBorder;
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

    private JTextField txtTimSach;
    private JTextField txtNamXB;
    private JComboBox<TheLoai> cbTheLoai;
    private JComboBox<NhaXuatBan> cbNXB;

    private JLabel lblSachDangChon;
    private JLabel lblGia;
    private JLabel lblTon;
    private JTextField txtSoLuong;
    private JButton btnThemGio;

    private JTable tblSach;
    private JTable tblGioHang;
    private DefaultTableModel modelSach;
    private DefaultTableModel modelGioHang;

    private JLabel lblTongTien;
    private JButton btnXoaDong;
    private JButton btnThanhToan;
    private JButton btnDong;

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
        dsSachTrongGio = new ArrayList<Integer>().isEmpty() ? new ArrayList<Sach>() : new ArrayList<Sach>();
        dsSoLuong = new ArrayList<Integer>();

        GUI();

        loadComboBoxLoc();
        locSach();
    }

    public void GUI() {
        setTitle("Ban sach");
        setSize(1200, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTieuDe = new JLabel("BAN SACH", JLabel.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel lblNhanVien = new JLabel(
                "Nhan vien: " + currentUser.getFullName(),
                JLabel.CENTER
        );
        lblNhanVien.setFont(new Font("Arial", Font.BOLD, 15));

        JPanel pnTieuDe = new JPanel(new GridLayout(2, 1));
        pnTieuDe.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnTieuDe.add(lblTieuDe);
        pnTieuDe.add(lblNhanVien);

        txtSDTKhachHang = new JTextField();
        btnTimKhachHang = new JButton("Tim");
        lblKhachHang = new JLabel("Chua chon khach hang");
        lblKhachHang.setFont(new Font("Arial", Font.BOLD, 13));

        txtTimSach = new JTextField();
        txtNamXB = new JTextField();
        cbTheLoai = new JComboBox<TheLoai>();
        cbNXB = new JComboBox<NhaXuatBan>();

        lblSachDangChon = new JLabel("Chua chon sach");
        lblSachDangChon.setFont(new Font("Arial", Font.BOLD, 14));

        lblGia = new JLabel("Gia ban: 0");
        lblTon = new JLabel("Ton kho: 0");

        txtSoLuong = new JTextField();
        btnThemGio = new JButton("Them vao gio");

        btnXoaDong = new JButton("Xoa dong");
        btnThanhToan = new JButton("Thanh toan");
        btnDong = new JButton("Dong");

        lblTongTien = new JLabel("Tong tien: 0");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 22));

        btnTimKhachHang.addActionListener(this);
        btnThemGio.addActionListener(this);
        btnXoaDong.addActionListener(this);
        btnThanhToan.addActionListener(this);
        btnDong.addActionListener(this);

        cbTheLoai.addActionListener(this);
        cbNXB.addActionListener(this);

        themSuKienLoc(txtTimSach);
        themSuKienLoc(txtNamXB);

        JPanel pnKhachHang = taoPanel("Thong tin khach hang");
        pnKhachHang.setLayout(new GridLayout(2, 1, 8, 8));

        JPanel pnSDT = new JPanel(new BorderLayout(8, 8));
        pnSDT.add(new JLabel("SDT:"), BorderLayout.WEST);
        pnSDT.add(txtSDTKhachHang, BorderLayout.CENTER);
        pnSDT.add(btnTimKhachHang, BorderLayout.EAST);

        JPanel pnTenKhach = new JPanel(new BorderLayout(8, 8));
        pnTenKhach.add(new JLabel("Khach hang:"), BorderLayout.WEST);
        pnTenKhach.add(lblKhachHang, BorderLayout.CENTER);

        pnKhachHang.add(pnSDT);
        pnKhachHang.add(pnTenKhach);

        JPanel pnSachDangChon = taoPanel("Sach dang chon");
        pnSachDangChon.setLayout(new GridLayout(5, 1, 8, 8));

        pnSachDangChon.add(lblSachDangChon);
        pnSachDangChon.add(lblGia);
        pnSachDangChon.add(lblTon);

        JPanel pnSoLuong = new JPanel(new BorderLayout(8, 8));
        pnSoLuong.add(new JLabel("So luong:"), BorderLayout.WEST);
        pnSoLuong.add(txtSoLuong, BorderLayout.CENTER);

        pnSachDangChon.add(pnSoLuong);
        pnSachDangChon.add(btnThemGio);

        JPanel pnTrai = new JPanel(new BorderLayout(10, 10));
        pnTrai.setPreferredSize(new Dimension(330, 520));
        pnTrai.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

        JPanel pnTraiTop = new JPanel();
        pnTraiTop.setLayout(new BoxLayout(pnTraiTop, BoxLayout.Y_AXIS));

        pnKhachHang.setMaximumSize(new Dimension(320, 130));
        pnSachDangChon.setMaximumSize(new Dimension(320, 230));

        pnTraiTop.add(pnKhachHang);
        pnTraiTop.add(Box.createVerticalStrut(12));
        pnTraiTop.add(pnSachDangChon);

        pnTrai.add(pnTraiTop, BorderLayout.NORTH);

        JPanel pnLocNgang = new JPanel(new GridLayout(2, 4, 8, 5));
        pnLocNgang.setBorder(BorderFactory.createEmptyBorder(5, 5, 8, 5));

        pnLocNgang.add(new JLabel("Ten sach:"));
        pnLocNgang.add(new JLabel("The loai:"));
        pnLocNgang.add(new JLabel("Nha xuat ban:"));
        pnLocNgang.add(new JLabel("Nam XB:"));

        pnLocNgang.add(txtTimSach);
        pnLocNgang.add(cbTheLoai);
        pnLocNgang.add(cbNXB);
        pnLocNgang.add(txtNamXB);

        modelSach = new DefaultTableModel();
        modelSach.addColumn("Ma sach");
        modelSach.addColumn("Ten sach");
        modelSach.addColumn("Nam XB");
        modelSach.addColumn("Gia ban");
        modelSach.addColumn("Ton kho");

        tblSach = new JTable(modelSach);
        tblSach.setRowHeight(26);
        tblSach.addMouseListener(this);

        JPanel pnDanhSachSach = taoPanel("Danh sach sach");
        pnDanhSachSach.setLayout(new BorderLayout(8, 8));
        pnDanhSachSach.add(pnLocNgang, BorderLayout.NORTH);
        pnDanhSachSach.add(new JScrollPane(tblSach), BorderLayout.CENTER);

        modelGioHang = new DefaultTableModel();
        modelGioHang.addColumn("Ma sach");
        modelGioHang.addColumn("Ten sach");
        modelGioHang.addColumn("So luong");
        modelGioHang.addColumn("Gia ban");
        modelGioHang.addColumn("Thanh tien");

        tblGioHang = new JTable(modelGioHang);
        tblGioHang.setRowHeight(26);

        JPanel pnGioHang = taoPanel("Gio hang");
        pnGioHang.setLayout(new BorderLayout());
        pnGioHang.setPreferredSize(new Dimension(800, 210));
        pnGioHang.add(new JScrollPane(tblGioHang), BorderLayout.CENTER);

        JPanel pnPhai = new JPanel(new BorderLayout(10, 10));
        pnPhai.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        pnPhai.add(pnDanhSachSach, BorderLayout.CENTER);
        pnPhai.add(pnGioHang, BorderLayout.SOUTH);

        JPanel pnNoiDung = new JPanel(new BorderLayout(10, 10));
        pnNoiDung.add(pnTrai, BorderLayout.WEST);
        pnNoiDung.add(pnPhai, BorderLayout.CENTER);

        JPanel pnNut = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnNut.add(btnXoaDong);
        pnNut.add(btnThanhToan);
        pnNut.add(btnDong);

        JPanel pnThanhToan = new JPanel(new BorderLayout());
        pnThanhToan.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        pnThanhToan.add(lblTongTien, BorderLayout.WEST);
        pnThanhToan.add(pnNut, BorderLayout.EAST);

        setLayout(new BorderLayout(10, 10));
        add(pnTieuDe, BorderLayout.NORTH);
        add(pnNoiDung, BorderLayout.CENTER);
        add(pnThanhToan, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JPanel taoPanel(String title) {
        JPanel panel = new JPanel();

        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleFont(new Font("Arial", Font.BOLD, 14));

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        border,
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)
                )
        );

        return panel;
    }

    public void themSuKienLoc(JTextField txt) {
        txt.getDocument().addDocumentListener(new DocumentListener() {
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
    }

    public void loadComboBoxLoc() {
        cbTheLoai.removeAllItems();
        cbNXB.removeAllItems();

        cbTheLoai.addItem(new TheLoai(0, "Tat ca"));
        cbNXB.addItem(new NhaXuatBan(0, "Tat ca"));

        List<TheLoai> dsTheLoai = theLoaiService.layDanhSach();
        for (TheLoai tl : dsTheLoai) {
            cbTheLoai.addItem(tl);
        }

        List<NhaXuatBan> dsNXB = nxbService.layDanhSach();
        for (NhaXuatBan nxb : dsNXB) {
            cbNXB.addItem(nxb);
        }
    }

    public void timKhachHang() {
        String sdt = txtSDTKhachHang.getText().trim();

        if (sdt.equals("")) {
            JOptionPane.showMessageDialog(this, "Nhap so dien thoai");
            return;
        }

        KhachHang kh = khachHangService.timTheoSDT(sdt);

        if (kh == null) {
            JOptionPane.showMessageDialog(this, "Khong tim thay khach hang");
            return;
        }

        khachHangDangChon = kh;

        lblKhachHang.setText(
                kh.getHoTen() + " - " + kh.getSoDienThoai()
        );
    }

    public void locSach() {
        if (modelSach == null || txtTimSach == null || txtNamXB == null) {
            return;
        }

        modelSach.setRowCount(0);
        dsSachDangHienThi.clear();

        String tenSach = txtTimSach.getText();

        int maTheLoai = 0;
        int maNXB = 0;
        int namXB = 0;

        TheLoai tl = (TheLoai) cbTheLoai.getSelectedItem();
        NhaXuatBan nxb = (NhaXuatBan) cbNXB.getSelectedItem();

        if (tl != null) {
            maTheLoai = tl.getMaTheLoai();
        }

        if (nxb != null) {
            maNXB = nxb.getMaNXB();
        }

        try {
            if (!txtNamXB.getText().trim().equals("")) {
                namXB = Integer.parseInt(txtNamXB.getText().trim());
            }
        } catch (Exception e) {
            namXB = 0;
        }

        List<Sach> ds = sachService.timKiem(
                tenSach,
                maTheLoai,
                maNXB,
                namXB
        );

        for (Sach s : ds) {
            dsSachDangHienThi.add(s);

            modelSach.addRow(new Object[]{
                    s.getMaSach(),
                    s.getTenSach(),
                    s.getNamXB(),
                    s.getGiaBan(),
                    s.getSoLuong()
            });
        }

        resetSachDangChon();
    }

    public void resetSachDangChon() {
        sachDangChon = null;
        lblSachDangChon.setText("Chua chon sach");
        lblGia.setText("Gia ban: 0");
        lblTon.setText("Ton kho: 0");
    }

    public void chonSachTuBang() {
        int row = tblSach.getSelectedRow();

        if (row < 0) {
            return;
        }

        sachDangChon = dsSachDangHienThi.get(row);

        lblSachDangChon.setText("Sach: " + sachDangChon.getTenSach());
        lblGia.setText("Gia ban: " + sachDangChon.getGiaBan());
        lblTon.setText("Ton kho: " + sachDangChon.getSoLuong());
    }

    public boolean kiemTraThemGio() {
        if (sachDangChon == null) {
            JOptionPane.showMessageDialog(this, "Chon sach trong bang truoc");
            return false;
        }

        if (txtSoLuong.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Nhap so luong");
            return false;
        }

        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "So luong phai > 0");
                return false;
            }

            if (soLuong > sachDangChon.getSoLuong()) {
                JOptionPane.showMessageDialog(this, "Vuot qua ton kho");
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "So luong khong hop le");
            return false;
        }

        return true;
    }

    public void themVaoGio() {
        if (!kiemTraThemGio()) {
            return;
        }

        int soLuong = Integer.parseInt(txtSoLuong.getText());

        for (int i = 0; i < dsSachTrongGio.size(); i++) {
            if (dsSachTrongGio.get(i).getMaSach() == sachDangChon.getMaSach()) {
                int soLuongMoi = dsSoLuong.get(i) + soLuong;

                if (soLuongMoi > sachDangChon.getSoLuong()) {
                    JOptionPane.showMessageDialog(this, "Tong so luong vuot ton kho");
                    return;
                }

                dsSoLuong.set(i, soLuongMoi);
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

            double thanhTien = soLuong * s.getGiaBan();
            tongTien += thanhTien;

            modelGioHang.addRow(new Object[]{
                    s.getMaSach(),
                    s.getTenSach(),
                    soLuong,
                    s.getGiaBan(),
                    thanhTien
            });
        }

        lblTongTien.setText("Tong tien: " + tongTien);
    }

    public void xoaDong() {
        int row = tblGioHang.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chon dong can xoa");
            return;
        }

        dsSachTrongGio.remove(row);
        dsSoLuong.remove(row);

        loadGioHang();
    }

    public void thanhToan() {
        if (khachHangDangChon == null) {
            JOptionPane.showMessageDialog(this, "Chua chon khach hang");
            return;
        }

        if (dsSachTrongGio.size() == 0) {
            JOptionPane.showMessageDialog(this, "Gio hang rong");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Xac nhan thanh toan?",
                "Xac nhan",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean ketQua = hoaDonService.taoHoaDonNhieuSach(
                khachHangDangChon.getMaKhachHang(),
                currentUser.getUserId(),
                dsSachTrongGio,
                dsSoLuong
        );

        if (ketQua) {
            JOptionPane.showMessageDialog(this, "Thanh toan thanh cong");

            dsSachTrongGio.clear();
            dsSoLuong.clear();

            loadGioHang();
            locSach();

            txtSoLuong.setText("");

        } else {
            JOptionPane.showMessageDialog(this, "Thanh toan that bai");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTimKhachHang) {
            timKhachHang();
        }

        if (e.getSource() == cbTheLoai || e.getSource() == cbNXB) {
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