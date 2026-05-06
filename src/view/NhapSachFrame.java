package view;

import model.*;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NhapSachFrame extends JFrame implements ActionListener {
    private JComboBox<Supplier> cbNCC;
    private JComboBox<Sach> cbSach;
    private JTextField txtSoLuong, txtGia;
    private JButton btnNhap, btnDong;

    private NhapSachService nhapSachService;
    private SupplierService supplierService;
    private SachService sachService;
    private User currentUser;

    public NhapSachFrame(User user) {
        currentUser = user;
        nhapSachService = new NhapSachService();
        supplierService = new SupplierService();
        sachService = new SachService();

        GUI();
        loadData();
    }

    public void GUI() {
        setTitle("Nhập sách");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cbNCC = new JComboBox<Supplier>();
        cbSach = new JComboBox<Sach>();
        txtSoLuong = new JTextField();
        txtGia = new JTextField();

        btnNhap = new JButton("Nhập");
        btnDong = new JButton("Đóng");

        btnNhap.addActionListener(this);
        btnDong.addActionListener(this);

        JPanel pnNhap = new JPanel(new GridLayout(4, 2, 10, 10));
        pnNhap.add(new JLabel("Nhà cung cấp:"));
        pnNhap.add(cbNCC);
        pnNhap.add(new JLabel("Sách:"));
        pnNhap.add(cbSach);
        pnNhap.add(new JLabel("Số lượng:"));
        pnNhap.add(txtSoLuong);
        pnNhap.add(new JLabel("Giá nhập:"));
        pnNhap.add(txtGia);

        JPanel pnNut = new JPanel(new FlowLayout());
        pnNut.add(btnNhap);
        pnNut.add(btnDong);

        setLayout(new BorderLayout(10, 10));
        add(new JLabel("NHẬP SÁCH", JLabel.CENTER), BorderLayout.NORTH);
        add(pnNhap, BorderLayout.CENTER);
        add(pnNut, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void loadData() {
        cbNCC.removeAllItems();
        cbSach.removeAllItems();

        for (Supplier s : supplierService.layDanhSach()) {
            cbNCC.addItem(s);
        }

        for (Sach s : sachService.layDanhSach()) {
            cbSach.addItem(s);
        }
    }

    public boolean kiemTraDuLieu() {
        if (cbNCC.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa có nhà cung cấp");
            return false;
        }

        if (cbSach.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa có sách");
            return false;
        }

        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            double giaNhap = Double.parseDouble(txtGia.getText());

            if (soLuong <= 0 || giaNhap <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng và giá nhập phải lớn hơn 0");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng hoặc giá nhập không hợp lệ");
            return false;
        }

        return true;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNhap) {
            if (!kiemTraDuLieu()) {
                return;
            }

            Supplier ncc = (Supplier) cbNCC.getSelectedItem();
            Sach sach = (Sach) cbSach.getSelectedItem();

            int soLuong = Integer.parseInt(txtSoLuong.getText());
            double giaNhap = Double.parseDouble(txtGia.getText());

            if (nhapSachService.nhap(ncc.getSupplierId(), currentUser.getUserId(), sach, soLuong, giaNhap)) {
                JOptionPane.showMessageDialog(this, "Nhập sách thành công");
                txtSoLuong.setText("");
                txtGia.setText("");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Nhập sách thất bại");
            }
        }

        if (e.getSource() == btnDong) {
            dispose();
        }
    }
}