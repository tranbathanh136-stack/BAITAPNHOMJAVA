package view;

import model.KhachHang;
import model.Sach;
import model.User;
import service.HoaDonService;
import service.KhachHangService;
import service.SachService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonFrame extends JFrame implements ActionListener {
    private User currentUser;

    private JComboBox<KhachHang> cbKhachHang;
    private JComboBox<Sach> cbSach;

    private JTextField txtSoLuong;
    private JLabel lblNhanVien, lblGia, lblTon, lblTongTien;

    private JButton btnThemGio, btnXoaDong, btnThanhToan, btnDong;

    private JTable bang;
    private DefaultTableModel model;

    private HoaDonService hoaDonService;
    private SachService sachService;
    private KhachHangService khachHangService;

    private List<Sach> dsSachTrongGio;
    private List<Integer> dsSoLuong;

    public HoaDonFrame(User user) {
        currentUser = user;

        hoaDonService = new HoaDonService();
        sachService = new SachService();
        khachHangService = new KhachHangService();

        dsSachTrongGio = new ArrayList<Sach>();
        dsSoLuong = new ArrayList<Integer>();

        GUI();
        loadKhachHang();
        loadSach();
    }

    public void GUI() {
        setTitle("Ban sach");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTieuDe = new JLabel("BAN SACH", JLabel.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));

        lblNhanVien = new JLabel("Nhan vien: " + currentUser.getFullName(), JLabel.CENTER);

        cbKhachHang = new JComboBox<KhachHang>();
        cbSach = new JComboBox<Sach>();

        txtSoLuong = new JTextField();

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

        cbSach.addActionListener(this);

        JPanel pnInput = new JPanel(new GridLayout(5, 2, 10, 10));

        pnInput.add(new JLabel("Khach hang:"));
        pnInput.add(cbKhachHang);

        pnInput.add(new JLabel("Sach:"));
        pnInput.add(cbSach);

        pnInput.add(new JLabel("So luong:"));
        pnInput.add(txtSoLuong);

        pnInput.add(lblGia);
        pnInput.add(lblTon);

        pnInput.add(new JLabel(""));
        pnInput.add(btnThemGio);

        model = new DefaultTableModel();
        model.addColumn("Ma sach");
        model.addColumn("Ten sach");
        model.addColumn("So luong");
        model.addColumn("Gia ban");
        model.addColumn("Thanh tien");

        bang = new JTable(model);

        JPanel pnNut = new JPanel(new FlowLayout());
        pnNut.add(btnXoaDong);
        pnNut.add(btnThanhToan);
        pnNut.add(btnDong);

        JPanel pnTren = new JPanel(new BorderLayout());
        pnTren.add(lblTieuDe, BorderLayout.NORTH);
        pnTren.add(lblNhanVien, BorderLayout.CENTER);
        pnTren.add(pnInput, BorderLayout.SOUTH);

        JPanel pnDuoi = new JPanel(new BorderLayout());
        pnDuoi.add(lblTongTien, BorderLayout.NORTH);
        pnDuoi.add(pnNut, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(pnTren, BorderLayout.NORTH);
        add(new JScrollPane(bang), BorderLayout.CENTER);
        add(pnDuoi, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void loadKhachHang() {
        cbKhachHang.removeAllItems();

        List<KhachHang> ds = khachHangService.layDanhSach();

        for (KhachHang kh : ds) {
            cbKhachHang.addItem(kh);
        }
    }

    public void loadSach() {
        cbSach.removeAllItems();

        List<Sach> ds = sachService.layDanhSach();

        for (Sach s : ds) {
            cbSach.addItem(s);
        }

        hienThiThongTinSach();
    }

    public void hienThiThongTinSach() {
        Sach s = (Sach) cbSach.getSelectedItem();

        if (s != null) {
            lblGia.setText("Gia ban: " + s.getGiaBan());
            lblTon.setText("Ton kho: " + s.getSoLuong());
        }
    }

    public boolean kiemTraThemGio() {
        if (cbSach.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chua co sach");
            return false;
        }

        if (txtSoLuong.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "So luong khong duoc rong");
            return false;
        }

        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "So luong phai lon hon 0");
                return false;
            }

            Sach s = (Sach) cbSach.getSelectedItem();

            if (soLuong > s.getSoLuong()) {
                JOptionPane.showMessageDialog(this, "So luong ton khong du");
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

        Sach s = (Sach) cbSach.getSelectedItem();
        int soLuong = Integer.parseInt(txtSoLuong.getText());

        for (int i = 0; i < dsSachTrongGio.size(); i++) {
            if (dsSachTrongGio.get(i).getMaSach() == s.getMaSach()) {
                int soLuongMoi = dsSoLuong.get(i) + soLuong;

                if (soLuongMoi > s.getSoLuong()) {
                    JOptionPane.showMessageDialog(this, "Tong so luong vuot qua ton kho");
                    return;
                }

                dsSoLuong.set(i, soLuongMoi);
                loadGioHang();
                txtSoLuong.setText("");
                return;
            }
        }

        dsSachTrongGio.add(s);
        dsSoLuong.add(soLuong);

        loadGioHang();
        txtSoLuong.setText("");
    }

    public void loadGioHang() {
        model.setRowCount(0);

        double tongTien = 0;

        for (int i = 0; i < dsSachTrongGio.size(); i++) {
            Sach s = dsSachTrongGio.get(i);
            int soLuong = dsSoLuong.get(i);

            double thanhTien = soLuong * s.getGiaBan();
            tongTien = tongTien + thanhTien;

            model.addRow(new Object[]{
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
        int row = bang.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chon dong can xoa");
            return;
        }

        dsSachTrongGio.remove(row);
        dsSoLuong.remove(row);

        loadGioHang();
    }

    public void thanhToan() {
        if (cbKhachHang.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chua co khach hang");
            return;
        }

        if (dsSachTrongGio.size() == 0) {
            JOptionPane.showMessageDialog(this, "Gio hang dang rong");
            return;
        }

        KhachHang kh = (KhachHang) cbKhachHang.getSelectedItem();

        boolean ketQua = hoaDonService.taoHoaDonNhieuSach(
                kh.getMaKhachHang(),
                currentUser.getUserId(),
                dsSachTrongGio,
                dsSoLuong
        );

        if (ketQua) {
            JOptionPane.showMessageDialog(this, "Thanh toan thanh cong");

            dsSachTrongGio.clear();
            dsSoLuong.clear();

            loadGioHang();
            loadSach();

        } else {
            JOptionPane.showMessageDialog(this, "Thanh toan that bai");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cbSach) {
            hienThiThongTinSach();
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
}