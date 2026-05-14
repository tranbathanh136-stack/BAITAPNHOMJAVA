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

    private int maNhanVienDangChon = 0;
    private int maUserDangChon = 0;

    private JTextField txtUsername;
    private JTextField txtPassword;
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

    private List<NhanVien> dsNhanVien;

    public NhanVienFrame() {
        nhanVienService = new NhanVienService();

        GUI();
        loadData();
    }

    public void GUI() {
        setTitle("Quan ly nhan vien");
        setSize(1000, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTieuDe = new JLabel("QUAN LY NHAN VIEN", JLabel.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));

        txtUsername = new JTextField();
        txtPassword = new JTextField();
        txtHoTen = new JTextField();
        txtSoDienThoai = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();

        JPanel pnInput = new JPanel(new GridLayout(3, 4, 10, 10));
        pnInput.setBorder(BorderFactory.createTitledBorder("Thong tin nhan vien"));

        pnInput.add(new JLabel("Ten dang nhap:"));
        pnInput.add(txtUsername);

        pnInput.add(new JLabel("Mat khau:"));
        pnInput.add(txtPassword);

        pnInput.add(new JLabel("Ho ten:"));
        pnInput.add(txtHoTen);

        pnInput.add(new JLabel("So dien thoai:"));
        pnInput.add(txtSoDienThoai);

        pnInput.add(new JLabel("Email:"));
        pnInput.add(txtEmail);

        pnInput.add(new JLabel("Dia chi:"));
        pnInput.add(txtDiaChi);

        btnThem = new JButton("Them");
        btnSua = new JButton("Sua");
        btnXoa = new JButton("Xoa");
        btnLamMoi = new JButton("Lam moi");
        btnDong = new JButton("Dong");

        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnDong.addActionListener(this);

        JPanel pnBtn = new JPanel(new FlowLayout());
        pnBtn.add(btnThem);
        pnBtn.add(btnSua);
        pnBtn.add(btnXoa);
        pnBtn.add(btnLamMoi);
        pnBtn.add(btnDong);

        model = new DefaultTableModel();

        model.addColumn("Ten dang nhap");
        model.addColumn("Mat khau");
        model.addColumn("Ho ten");
        model.addColumn("SDT");
        model.addColumn("Email");
        model.addColumn("Dia chi");

        bang = new JTable(model);
        bang.setRowHeight(26);
        bang.addMouseListener(this);

        JPanel pnTren = new JPanel(new BorderLayout(10, 10));
        pnTren.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnTren.add(lblTieuDe, BorderLayout.NORTH);
        pnTren.add(pnInput, BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));
        add(pnTren, BorderLayout.NORTH);
        add(new JScrollPane(bang), BorderLayout.CENTER);
        add(pnBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void loadData() {
        model.setRowCount(0);

        dsNhanVien = nhanVienService.layDanhSach();

        for (NhanVien nv : dsNhanVien) {
            model.addRow(new Object[]{
                    nv.getUsername(),
                    nv.getPassword(),
                    nv.getHoTen(),
                    nv.getSoDienThoai(),
                    nv.getEmail(),
                    nv.getDiaChi()
            });
        }
    }

    public boolean validateInput() {
        if (txtUsername.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Ten dang nhap khong duoc rong");
            return false;
        }

        if (txtPassword.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Mat khau khong duoc rong");
            return false;
        }

        if (txtHoTen.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Ho ten khong duoc rong");
            return false;
        }

        return true;
    }

    public NhanVien getForm() {
        NhanVien nv = new NhanVien();

        nv.setMaNhanVien(maNhanVienDangChon);
        nv.setMaUser(maUserDangChon);

        nv.setUsername(txtUsername.getText());
        nv.setPassword(txtPassword.getText());
        nv.setHoTen(txtHoTen.getText());
        nv.setSoDienThoai(txtSoDienThoai.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDiaChi(txtDiaChi.getText());

        return nv;
    }

    public void reset() {
        maNhanVienDangChon = 0;
        maUserDangChon = 0;

        txtUsername.setText("");
        txtPassword.setText("");
        txtHoTen.setText("");
        txtSoDienThoai.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");

        bang.clearSelection();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnThem) {
            if (!validateInput()) {
                return;
            }

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
            if (maNhanVienDangChon == 0) {
                JOptionPane.showMessageDialog(this, "Chon nhan vien de sua");
                return;
            }

            if (!validateInput()) {
                return;
            }

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
            if (maNhanVienDangChon == 0) {
                JOptionPane.showMessageDialog(this, "Chon nhan vien de xoa");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Xac nhan xoa nhan vien nay?",
                    "Xac nhan",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            if (nhanVienService.xoaNhanVien(maNhanVienDangChon)) {
                JOptionPane.showMessageDialog(this, "Xoa thanh cong");
                loadData();
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "Xoa that bai. Khong the xoa tai khoan admin.");
            }
        }

        if (e.getSource() == btnLamMoi) {
            reset();
            loadData();
        }

        if (e.getSource() == btnDong) {
            dispose();
        }
    }

    public void mouseClicked(MouseEvent e) {
        int row = bang.getSelectedRow();

        if (row < 0) {
            return;
        }

        NhanVien nv = dsNhanVien.get(row);

        maNhanVienDangChon = nv.getMaNhanVien();
        maUserDangChon = nv.getMaUser();

        txtUsername.setText(nv.getUsername());
        txtPassword.setText(nv.getPassword());
        txtHoTen.setText(nv.getHoTen());
        txtSoDienThoai.setText(nv.getSoDienThoai());
        txtEmail.setText(nv.getEmail());
        txtDiaChi.setText(nv.getDiaChi());
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