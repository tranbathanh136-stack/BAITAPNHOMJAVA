package view;

import model.NhanVien;
import service.NhanVienService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class NhanVienFrame extends JFrame implements ActionListener, MouseListener {

    private NhanVienService nhanVienService;

    private JTextField txtMaNhanVien;
    private JTextField txtMaUser;
    private JTextField txtHoTen;
    private JTextField txtSoDienThoai;
    private JTextField txtEmail;
    private JTextField txtDiaChi;
    private JTextField txtChucVu;
    private JTextField txtTimKiem;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLamMoi;
    private JButton btnTim;

    private JTable bang;
    private DefaultTableModel model;

    public NhanVienFrame() {
        nhanVienService = new NhanVienService();

        GUI();
        loadData();
    }

    public void GUI() {
        setTitle("Quan ly nhan vien");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtMaNhanVien = new JTextField();
        txtMaNhanVien.setEditable(false);

        txtMaUser = new JTextField();
        txtHoTen = new JTextField();
        txtSoDienThoai = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();
        txtChucVu = new JTextField();
        txtTimKiem = new JTextField();

        JPanel pnInput = new JPanel(new GridLayout(7, 2, 10, 10));

        pnInput.add(new JLabel("Ma nhan vien"));
        pnInput.add(txtMaNhanVien);

        pnInput.add(new JLabel("Ma user"));
        pnInput.add(txtMaUser);

        pnInput.add(new JLabel("Ho ten"));
        pnInput.add(txtHoTen);

        pnInput.add(new JLabel("So dien thoai"));
        pnInput.add(txtSoDienThoai);

        pnInput.add(new JLabel("Email"));
        pnInput.add(txtEmail);

        pnInput.add(new JLabel("Dia chi"));
        pnInput.add(txtDiaChi);

        pnInput.add(new JLabel("Chuc vu"));
        pnInput.add(txtChucVu);

        btnThem = new JButton("Them");
        btnSua = new JButton("Sua");
        btnXoa = new JButton("Xoa");
        btnLamMoi = new JButton("Lam moi");
        btnTim = new JButton("Tim");

        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnTim.addActionListener(this);

        JPanel pnBtn = new JPanel();
        pnBtn.add(btnThem);
        pnBtn.add(btnSua);
        pnBtn.add(btnXoa);
        pnBtn.add(btnLamMoi);

        JPanel pnTim = new JPanel(new BorderLayout(10, 10));
        pnTim.add(new JLabel("Tim nhan vien"), BorderLayout.WEST);
        pnTim.add(txtTimKiem, BorderLayout.CENTER);
        pnTim.add(btnTim, BorderLayout.EAST);

        model = new DefaultTableModel();

        model.addColumn("Ma NV");
        model.addColumn("Ma user");
        model.addColumn("Ho ten");
        model.addColumn("SDT");
        model.addColumn("Email");
        model.addColumn("Dia chi");
        model.addColumn("Chuc vu");

        bang = new JTable(model);
        bang.addMouseListener(this);

        JPanel pnTren = new JPanel(new BorderLayout());
        JLabel lblTieuDe = new JLabel("QUAN LY NHAN VIEN", JLabel.CENTER);
        pnTren.add(lblTieuDe, BorderLayout.NORTH);
        pnTren.add(pnInput, BorderLayout.CENTER);
        pnTren.add(pnTim, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(pnTren, BorderLayout.NORTH);
        add(new JScrollPane(bang), BorderLayout.CENTER);
        add(pnBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void loadData() {
        model.setRowCount(0);

        List<NhanVien> ds = nhanVienService.layDanhSach();

        for (NhanVien nv : ds) {
            model.addRow(new Object[]{
                    nv.getMaNhanVien(),
                    nv.getMaUser(),
                    nv.getHoTen(),
                    nv.getSoDienThoai(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    nv.getChucVu()
            });
        }
    }

    public void timKiem() {
        model.setRowCount(0);

        List<NhanVien> ds = nhanVienService.timTheoTen(txtTimKiem.getText());

        for (NhanVien nv : ds) {
            model.addRow(new Object[]{
                    nv.getMaNhanVien(),
                    nv.getMaUser(),
                    nv.getHoTen(),
                    nv.getSoDienThoai(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    nv.getChucVu()
            });
        }
    }

    public boolean validateInput() {
        if (txtMaUser.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Ma user khong duoc rong");
            return false;
        }

        if (txtHoTen.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Ho ten khong duoc rong");
            return false;
        }

        try {
            Integer.parseInt(txtMaUser.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ma user phai la so");
            return false;
        }

        return true;
    }

    public NhanVien getForm() {
        NhanVien nv = new NhanVien();

        if (!txtMaNhanVien.getText().equals("")) {
            nv.setMaNhanVien(Integer.parseInt(txtMaNhanVien.getText()));
        }

        nv.setMaUser(Integer.parseInt(txtMaUser.getText()));
        nv.setHoTen(txtHoTen.getText());
        nv.setSoDienThoai(txtSoDienThoai.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setChucVu(txtChucVu.getText());

        return nv;
    }

    public void reset() {
        txtMaNhanVien.setText("");
        txtMaUser.setText("");
        txtHoTen.setText("");
        txtSoDienThoai.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtChucVu.setText("");
        txtTimKiem.setText("");
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnThem) {
            if (!validateInput()) return;

            NhanVien nv = getForm();

            if (nhanVienService.themNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Them thanh cong");
                loadData();
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "Them that bai");
            }
        }

        if (e.getSource() == btnSua) {
            if (txtMaNhanVien.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Chon nhan vien de sua");
                return;
            }

            if (!validateInput()) return;

            NhanVien nv = getForm();

            if (nhanVienService.suaNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Sua thanh cong");
                loadData();
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "Sua that bai");
            }
        }

        if (e.getSource() == btnXoa) {
            if (txtMaNhanVien.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Chon nhan vien de xoa");
                return;
            }

            int ma = Integer.parseInt(txtMaNhanVien.getText());

            if (nhanVienService.xoaNhanVien(ma)) {
                JOptionPane.showMessageDialog(this, "Xoa thanh cong");
                loadData();
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "Xoa that bai");
            }
        }

        if (e.getSource() == btnLamMoi) {
            reset();
            loadData();
        }

        if (e.getSource() == btnTim) {
            timKiem();
        }
    }

    public void mouseClicked(MouseEvent e) {
        int row = bang.getSelectedRow();

        txtMaNhanVien.setText(model.getValueAt(row, 0).toString());
        txtMaUser.setText(model.getValueAt(row, 1).toString());
        txtHoTen.setText(model.getValueAt(row, 2).toString());
        txtSoDienThoai.setText(model.getValueAt(row, 3).toString());
        txtEmail.setText(model.getValueAt(row, 4).toString());
        txtDiaChi.setText(model.getValueAt(row, 5).toString());
        txtChucVu.setText(model.getValueAt(row, 6).toString());
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}