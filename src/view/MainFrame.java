package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener {
    private User currentUser;

    private JLabel lblTieuDe, lblNguoiDung;
    private JButton btnSach, btnKhachHang, btnHoaDon, btnNhapSach;
    private JButton btnNhanVien, btnThongKe, btnDangXuat;

    public MainFrame(User user) {
        currentUser = user;
        GUI();
        phanQuyen();
    }

    public void GUI() {
        setTitle("Hệ thống quản lý nhà sách");
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTieuDe = new JLabel("HỆ THỐNG QUẢN LÝ NHÀ SÁCH", JLabel.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));

        lblNguoiDung = new JLabel(
                "Xin chào: " + currentUser.getFullName() + " - Vai trò: " + currentUser.getRoleName(),
                JLabel.CENTER
        );

        btnSach = new JButton("Quản lý sách");
        btnKhachHang = new JButton("Quản lý khách hàng");
        btnHoaDon = new JButton("Bán sách");
        btnNhapSach = new JButton("Nhập sách");
        btnNhanVien = new JButton("Quản lý nhân viên");
        btnThongKe = new JButton("Thống kê");
        btnDangXuat = new JButton("Đăng xuất");

        btnSach.addActionListener(this);
        btnKhachHang.addActionListener(this);
        btnHoaDon.addActionListener(this);
        btnNhapSach.addActionListener(this);
        btnNhanVien.addActionListener(this);
        btnThongKe.addActionListener(this);
        btnDangXuat.addActionListener(this);

        JPanel pnChucNang = new JPanel(new GridLayout(3, 2, 15, 15));
        pnChucNang.add(btnSach);
        pnChucNang.add(btnKhachHang);
        pnChucNang.add(btnHoaDon);
        pnChucNang.add(btnNhapSach);
        pnChucNang.add(btnNhanVien);
        pnChucNang.add(btnThongKe);

        JPanel pnDuoi = new JPanel(new FlowLayout());
        pnDuoi.add(btnDangXuat);

        JPanel pnThongTin = new JPanel(new BorderLayout());
        pnThongTin.add(lblNguoiDung, BorderLayout.CENTER);
        pnThongTin.add(pnDuoi, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(lblTieuDe, BorderLayout.NORTH);
        add(pnChucNang, BorderLayout.CENTER);
        add(pnThongTin, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void phanQuyen() {
        if ("STAFF".equalsIgnoreCase(currentUser.getRoleName())) {
            btnNhapSach.setEnabled(false);
            btnNhanVien.setEnabled(false);
            btnThongKe.setEnabled(false);
        }
    }

    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnSach) {
        new SachFrame(currentUser);
    } else if (e.getSource() == btnKhachHang) {
        new KhachHangFrame();
    } else if (e.getSource() == btnHoaDon) {
        new HoaDonFrame(currentUser);
    } else if (e.getSource() == btnNhapSach) {
        new NhapSachFrame(currentUser);
    } else if (e.getSource() == btnDangXuat) {
        new LoginFrame();
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Chức năng đang được phát triển");
    }
}
}