package view;

import model.KhachHang;
import service.KhachHangService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class KhachHangFrame extends JFrame implements ActionListener, MouseListener {
    private KhachHangService khachHangService;

    private JTextField txtMaKhachHang;
    private JTextField txtHoTen;
    private JTextField txtSoDienThoai;
    private JTextField txtEmail;
    private JTextField txtDiaChi;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLamMoi;
    private JButton btnDong;

    private JTable bang;
    private DefaultTableModel model;

    public KhachHangFrame() {
        khachHangService = new KhachHangService();

        GUI();
        loadData();
    }

    public void GUI() {
        setTitle("Quản lý khách hàng");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTieuDe = new JLabel("QUẢN LÝ KHÁCH HÀNG", JLabel.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));

        txtMaKhachHang = new JTextField();
        txtMaKhachHang.setEditable(false);

        txtHoTen = new JTextField();
        txtSoDienThoai = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();

        JPanel pnNhap = new JPanel(new GridLayout(5, 2, 10, 10));
        pnNhap.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnNhap.add(new JLabel("Mã khách hàng:"));
        pnNhap.add(txtMaKhachHang);

        pnNhap.add(new JLabel("Họ tên:"));
        pnNhap.add(txtHoTen);

        pnNhap.add(new JLabel("Số điện thoại:"));
        pnNhap.add(txtSoDienThoai);

        pnNhap.add(new JLabel("Email:"));
        pnNhap.add(txtEmail);

        pnNhap.add(new JLabel("Địa chỉ:"));
        pnNhap.add(txtDiaChi);

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");
        btnDong = new JButton("Đóng");

        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnDong.addActionListener(this);

        JPanel pnNut = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnNut.add(btnThem);
        pnNut.add(btnSua);
        pnNut.add(btnXoa);
        pnNut.add(btnLamMoi);
        pnNut.add(btnDong);

        model = new DefaultTableModel();
        model.addColumn("Mã");
        model.addColumn("Họ tên");
        model.addColumn("Số điện thoại");
        model.addColumn("Email");
        model.addColumn("Địa chỉ");

        bang = new JTable(model);
        bang.setRowHeight(24);
        bang.addMouseListener(this);

        JScrollPane scrollPane = new JScrollPane(bang);

        JPanel pnTren = new JPanel(new BorderLayout(10, 10));
        pnTren.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        pnTren.add(lblTieuDe, BorderLayout.NORTH);
        pnTren.add(pnNhap, BorderLayout.CENTER);

        JPanel pnGiua = new JPanel(new BorderLayout());
        pnGiua.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        pnGiua.add(scrollPane, BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));
        add(pnTren, BorderLayout.NORTH);
        add(pnGiua, BorderLayout.CENTER);
        add(pnNut, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void loadData() {
        model.setRowCount(0);

        List<KhachHang> ds = khachHangService.layDanhSach();

        for (KhachHang kh : ds) {
            model.addRow(new Object[]{
                    kh.getMaKhachHang(),
                    kh.getHoTen(),
                    kh.getSoDienThoai(),
                    kh.getEmail(),
                    kh.getDiaChi()
            });
        }
    }

    public boolean kiemTraDuLieu() {
        if (txtHoTen.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống");
            return false;
        }

        if (txtSoDienThoai.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống");
            return false;
        }

        return true;
    }

    public KhachHang layKhachHangTuForm() {
        KhachHang kh = new KhachHang();

        if (!txtMaKhachHang.getText().equals("")) {
            kh.setMaKhachHang(Integer.parseInt(txtMaKhachHang.getText()));
        }

        kh.setHoTen(txtHoTen.getText());
        kh.setSoDienThoai(txtSoDienThoai.getText());
        kh.setEmail(txtEmail.getText());
        kh.setDiaChi(txtDiaChi.getText());

        return kh;
    }

    public void lamMoiForm() {
        txtMaKhachHang.setText("");
        txtHoTen.setText("");
        txtSoDienThoai.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtHoTen.requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThem) {
            if (!kiemTraDuLieu()) {
                return;
            }

            KhachHang kh = layKhachHangTuForm();

            if (khachHangService.themKhachHang(kh)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
                loadData();
                lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại");
            }
        }

        if (e.getSource() == btnSua) {
            if (txtMaKhachHang.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Chọn khách hàng cần sửa");
                return;
            }

            if (!kiemTraDuLieu()) {
                return;
            }

            KhachHang kh = layKhachHangTuForm();

            if (khachHangService.suaKhachHang(kh)) {
                JOptionPane.showMessageDialog(this, "Sửa khách hàng thành công");
                loadData();
                lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa khách hàng thất bại");
            }
        }

        if (e.getSource() == btnXoa) {
            if (txtMaKhachHang.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Chọn khách hàng cần xóa");
                return;
            }

            int chon = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có muốn xóa khách hàng này không?"
            );

            if (chon == JOptionPane.YES_OPTION) {
                int maKhachHang = Integer.parseInt(txtMaKhachHang.getText());

                if (khachHangService.xoaKhachHang(maKhachHang)) {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công");
                    loadData();
                    lamMoiForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại");
                }
            }
        }

        if (e.getSource() == btnLamMoi) {
            lamMoiForm();
        }

        if (e.getSource() == btnDong) {
            dispose();
        }
    }

    public void mouseClicked(MouseEvent e) {
        int dong = bang.getSelectedRow();

        if (dong < 0) {
            return;
        }

        txtMaKhachHang.setText(model.getValueAt(dong, 0).toString());
        txtHoTen.setText(model.getValueAt(dong, 1).toString());
        txtSoDienThoai.setText(model.getValueAt(dong, 2).toString());
        txtEmail.setText(model.getValueAt(dong, 3).toString());
        txtDiaChi.setText(model.getValueAt(dong, 4).toString());
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