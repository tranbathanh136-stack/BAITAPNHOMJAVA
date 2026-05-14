package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener {

    private User currentUser;

    private JLabel lblTieuDe;
    private JLabel lblNguoiDung;

    private JButton btnSach;
    private JButton btnKhachHang;
    private JButton btnHoaDon;
    private JButton btnNhanVien;
    private JButton btnThongKe;
    private JButton btnDangXuat;

    public MainFrame(User user) {
        currentUser = user;
        GUI();
    }

    public void GUI() {

        setTitle("Hệ thống quản lý nhà sách");
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTieuDe = new JLabel(
                "HỆ THỐNG QUẢN LÝ NHÀ SÁCH",
                JLabel.CENTER
        );

        lblTieuDe.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        lblNguoiDung = new JLabel(
                "Xin chào: "
                        + currentUser.getFullName()
                        + " - Vai trò: "
                        + currentUser.getRoleName(),
                JLabel.CENTER
        );

        btnSach = new JButton("Quản lý sách");
        btnKhachHang = new JButton("Quản lý khách hàng");
        btnHoaDon = new JButton("Bán sách");
        btnNhanVien = new JButton("Quản lý nhân viên");
        btnThongKe = new JButton("Thống kê");
        btnDangXuat = new JButton("Đăng xuất");

        btnSach.addActionListener(this);
        btnKhachHang.addActionListener(this);
        btnHoaDon.addActionListener(this);
        btnNhanVien.addActionListener(this);
        btnThongKe.addActionListener(this);
        btnDangXuat.addActionListener(this);

        JPanel pnChucNang = new JPanel(
                new GridLayout(0, 2, 15, 15)
        );

        pnChucNang.add(btnSach);
        pnChucNang.add(btnKhachHang);
        pnChucNang.add(btnHoaDon);

        if ("MANAGER".equalsIgnoreCase(currentUser.getRoleName())) {
            pnChucNang.add(btnNhanVien);
            pnChucNang.add(btnThongKe);
        }

        JPanel pnDuoi = new JPanel(
                new FlowLayout()
        );

        pnDuoi.add(btnDangXuat);

        JPanel pnThongTin = new JPanel(
                new BorderLayout()
        );

        pnThongTin.add(lblNguoiDung, BorderLayout.CENTER);
        pnThongTin.add(pnDuoi, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));

        add(lblTieuDe, BorderLayout.NORTH);
        add(pnChucNang, BorderLayout.CENTER);
        add(pnThongTin, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSach) {

            new SachFrame(currentUser);

        } else if (e.getSource() == btnKhachHang) {

            new KhachHangFrame();

        } else if (e.getSource() == btnHoaDon) {

            new HoaDonFrame(currentUser);

        } else if (e.getSource() == btnNhanVien) {

            new NhanVienFrame();

        } else if (e.getSource() == btnThongKe) {

            new ThongKeFrame();

        } else if (e.getSource() == btnDangXuat) {

            new LoginFrame();
            dispose();
        }
    }
}