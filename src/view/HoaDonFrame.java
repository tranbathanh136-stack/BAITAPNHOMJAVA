package view;

import model.KhachHang;
import model.Sach;
import model.User;
import service.HoaDonService;
import service.KhachHangService;
import service.SachService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class HoaDonFrame extends JFrame implements ActionListener {
    private User currentUser;

    private JLabel lblTieuDe, lblThongTin, lblKhachHang, lblSach, lblSoLuong;
    private JComboBox<KhachHang> cbKhachHang;
    private JComboBox<Sach> cbSach;
    private JTextField txtSoLuong;
    private JButton btnThanhToan, btnDong;

    private HoaDonService hoaDonService;
    private SachService sachService;
    private KhachHangService khachHangService;

    public HoaDonFrame(User user) {
        currentUser = user;
        hoaDonService = new HoaDonService();
        sachService = new SachService();
        khachHangService = new KhachHangService();

        GUI();
        loadKhachHang();
        loadSach();
    }

    public void GUI() {
        setTitle("Bán sách");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblTieuDe = new JLabel("BÁN SÁCH", JLabel.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));

        lblThongTin = new JLabel("Nhân viên: " + currentUser.getFullName(), JLabel.CENTER);

        lblKhachHang = new JLabel("Khách hàng:");
        lblSach = new JLabel("Chọn sách:");
        lblSoLuong = new JLabel("Số lượng:");

        cbKhachHang = new JComboBox<KhachHang>();
        cbSach = new JComboBox<Sach>();
        txtSoLuong = new JTextField();

        btnThanhToan = new JButton("Thanh toán");
        btnDong = new JButton("Đóng");

        btnThanhToan.addActionListener(this);
        btnDong.addActionListener(this);

        JPanel pnNhap = new JPanel(new GridLayout(3, 2, 10, 10));
        pnNhap.add(lblKhachHang);
        pnNhap.add(cbKhachHang);
        pnNhap.add(lblSach);
        pnNhap.add(cbSach);
        pnNhap.add(lblSoLuong);
        pnNhap.add(txtSoLuong);

        JPanel pnNut = new JPanel(new FlowLayout());
        pnNut.add(btnThanhToan);
        pnNut.add(btnDong);

        JPanel pnTren = new JPanel(new BorderLayout());
        pnTren.add(lblTieuDe, BorderLayout.NORTH);
        pnTren.add(lblThongTin, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(pnTren, BorderLayout.NORTH);
        add(pnNhap, BorderLayout.CENTER);
        add(pnNut, BorderLayout.SOUTH);

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
    }

    public boolean kiemTraDuLieu() {
        if (cbKhachHang.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa có khách hàng");
            return false;
        }

        if (cbSach.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa có sách để bán");
            return false;
        }

        if (txtSoLuong.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống");
            return false;
        }

        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
                return false;
            }

            Sach s = (Sach) cbSach.getSelectedItem();

            if (soLuong > s.getSoLuong()) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ");
            return false;
        }

        return true;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThanhToan) {
            if (!kiemTraDuLieu()) {
                return;
            }

            KhachHang kh = (KhachHang) cbKhachHang.getSelectedItem();
            Sach s = (Sach) cbSach.getSelectedItem();
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            if (hoaDonService.taoHoaDon(kh.getMaKhachHang(), currentUser.getUserId(), s, soLuong)) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công");
                txtSoLuong.setText("");
                loadSach();
            } else {
                JOptionPane.showMessageDialog(this, "Thanh toán thất bại");
            }
        }

        if (e.getSource() == btnDong) {
            dispose();
        }
    }
}