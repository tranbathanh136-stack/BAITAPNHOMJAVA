package view;

import model.Sach;
import model.User;
import model.TheLoai;
import model.NhaXuatBan;

import service.SachService;
import service.TheLoaiService;
import service.NhaXuatBanService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SachFrame extends JFrame implements ActionListener, MouseListener {
    private User nguoiDung;

    private SachService sachService;
    private TheLoaiService theLoaiService;
    private NhaXuatBanService nxbService;

    private JTextField txtMaSach;
    private JTextField txtTenSach;
    private JTextField txtNamXB;
    private JTextField txtGiaNhap;
    private JTextField txtGiaBan;
    private JTextField txtSoLuong;
    private JTextField txtTimKiem;

    private JComboBox<TheLoai> cbTheLoai;
    private JComboBox<NhaXuatBan> cbNXB;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLamMoi;
    private JButton btnTim;
    private JButton btnDong;

    private JTable bang;
    private DefaultTableModel model;

    public SachFrame(User user) {
        nguoiDung = user;

        sachService = new SachService();
        theLoaiService = new TheLoaiService();
        nxbService = new NhaXuatBanService();

        GUI();
        phanQuyen();
        loadComboBox();
        loadData();
    }

    public void GUI() {
        setTitle("Quan ly sach");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTieuDe = new JLabel("QUAN LY SACH", JLabel.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));

        txtMaSach = new JTextField();
        txtMaSach.setEditable(false);

        txtTenSach = new JTextField();
        txtNamXB = new JTextField();
        txtGiaNhap = new JTextField();
        txtGiaBan = new JTextField();
        txtSoLuong = new JTextField();
        txtTimKiem = new JTextField();

        cbTheLoai = new JComboBox<TheLoai>();
        cbNXB = new JComboBox<NhaXuatBan>();

        JPanel pnInput = new JPanel(new GridLayout(8, 2, 10, 10));
        pnInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnInput.add(new JLabel("Ma sach"));
        pnInput.add(txtMaSach);

        pnInput.add(new JLabel("Ten sach"));
        pnInput.add(txtTenSach);

        pnInput.add(new JLabel("The loai"));
        pnInput.add(cbTheLoai);

        pnInput.add(new JLabel("Nha xuat ban"));
        pnInput.add(cbNXB);

        pnInput.add(new JLabel("Nam XB"));
        pnInput.add(txtNamXB);

        pnInput.add(new JLabel("Gia nhap"));
        pnInput.add(txtGiaNhap);

        pnInput.add(new JLabel("Gia ban"));
        pnInput.add(txtGiaBan);

        pnInput.add(new JLabel("So luong"));
        pnInput.add(txtSoLuong);

        btnThem = new JButton("Them");
        btnSua = new JButton("Sua");
        btnXoa = new JButton("Xoa");
        btnLamMoi = new JButton("Lam moi");
        btnTim = new JButton("Tim");
        btnDong = new JButton("Dong");

        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnTim.addActionListener(this);
        btnDong.addActionListener(this);

        JPanel pnBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnBtn.add(btnThem);
        pnBtn.add(btnSua);
        pnBtn.add(btnXoa);
        pnBtn.add(btnLamMoi);
        pnBtn.add(btnDong);

        JPanel pnTim = new JPanel(new BorderLayout(10, 10));
        pnTim.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnTim.add(new JLabel("Tim sach"), BorderLayout.WEST);
        pnTim.add(txtTimKiem, BorderLayout.CENTER);
        pnTim.add(btnTim, BorderLayout.EAST);

        model = new DefaultTableModel();
        model.addColumn("Ma");
        model.addColumn("Ten");
        model.addColumn("The loai");
        model.addColumn("NXB");
        model.addColumn("Nam XB");
        model.addColumn("Gia nhap");
        model.addColumn("Gia ban");
        model.addColumn("So luong");

        bang = new JTable(model);
        bang.setRowHeight(24);
        bang.addMouseListener(this);

        JPanel pnNorth = new JPanel(new BorderLayout(10, 10));
        pnNorth.add(lblTieuDe, BorderLayout.NORTH);
        pnNorth.add(pnInput, BorderLayout.CENTER);
        pnNorth.add(pnTim, BorderLayout.SOUTH);

        JPanel pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        pnCenter.add(new JScrollPane(bang), BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));
        add(pnNorth, BorderLayout.NORTH);
        add(pnCenter, BorderLayout.CENTER);
        add(pnBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void phanQuyen() {
        if ("STAFF".equalsIgnoreCase(nguoiDung.getRoleName())) {
            btnXoa.setEnabled(false);
            txtGiaNhap.setEditable(false);
            txtGiaBan.setEditable(false);
        }
    }

    public void loadComboBox() {
        cbTheLoai.removeAllItems();
        cbNXB.removeAllItems();

        List<TheLoai> dsTheLoai = theLoaiService.layDanhSach();
        for (TheLoai tl : dsTheLoai) {
            cbTheLoai.addItem(tl);
        }

        List<NhaXuatBan> dsNXB = nxbService.layDanhSach();
        for (NhaXuatBan nxb : dsNXB) {
            cbNXB.addItem(nxb);
        }
    }

    public String layTenTheLoai(int ma) {
        for (int i = 0; i < cbTheLoai.getItemCount(); i++) {
            TheLoai tl = cbTheLoai.getItemAt(i);

            if (tl.getMaTheLoai() == ma) {
                return tl.getTenTheLoai();
            }
        }

        return "";
    }

    public String layTenNXB(int ma) {
        for (int i = 0; i < cbNXB.getItemCount(); i++) {
            NhaXuatBan nxb = cbNXB.getItemAt(i);

            if (nxb.getMaNXB() == ma) {
                return nxb.getTenNXB();
            }
        }

        return "";
    }

    public void chonComboBoxTheoTen(String tenTheLoai, String tenNXB) {
        for (int i = 0; i < cbTheLoai.getItemCount(); i++) {
            TheLoai tl = cbTheLoai.getItemAt(i);

            if (tl.getTenTheLoai().equals(tenTheLoai)) {
                cbTheLoai.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < cbNXB.getItemCount(); i++) {
            NhaXuatBan nxb = cbNXB.getItemAt(i);

            if (nxb.getTenNXB().equals(tenNXB)) {
                cbNXB.setSelectedIndex(i);
                break;
            }
        }
    }

    public void loadData() {
        model.setRowCount(0);

        List<Sach> ds = sachService.layDanhSach();

        for (Sach s : ds) {
            model.addRow(new Object[]{
                    s.getMaSach(),
                    s.getTenSach(),
                    layTenTheLoai(s.getMaTheLoai()),
                    layTenNXB(s.getMaNXB()),
                    s.getNamXB(),
                    s.getGiaNhap(),
                    s.getGiaBan(),
                    s.getSoLuong()
            });
        }
    }

    public void timKiem() {
        model.setRowCount(0);

        List<Sach> ds = sachService.timTheoTen(txtTimKiem.getText());

        for (Sach s : ds) {
            model.addRow(new Object[]{
                    s.getMaSach(),
                    s.getTenSach(),
                    layTenTheLoai(s.getMaTheLoai()),
                    layTenNXB(s.getMaNXB()),
                    s.getNamXB(),
                    s.getGiaNhap(),
                    s.getGiaBan(),
                    s.getSoLuong()
            });
        }
    }

    public boolean validateInput() {
        if (txtTenSach.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Ten sach khong duoc rong");
            return false;
        }

        if (cbTheLoai.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chua co the loai");
            return false;
        }

        if (cbNXB.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chua co nha xuat ban");
            return false;
        }

        try {
            Integer.parseInt(txtNamXB.getText());
            Double.parseDouble(txtGiaNhap.getText());
            Double.parseDouble(txtGiaBan.getText());
            Integer.parseInt(txtSoLuong.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nam XB, gia hoac so luong sai");
            return false;
        }

        return true;
    }

    public Sach getForm() {
        Sach s = new Sach();

        if (!txtMaSach.getText().equals("")) {
            s.setMaSach(Integer.parseInt(txtMaSach.getText()));
        }

        TheLoai tl = (TheLoai) cbTheLoai.getSelectedItem();
        NhaXuatBan nxb = (NhaXuatBan) cbNXB.getSelectedItem();

        s.setTenSach(txtTenSach.getText());
        s.setMaTheLoai(tl.getMaTheLoai());
        s.setMaNXB(nxb.getMaNXB());
        s.setNamXB(Integer.parseInt(txtNamXB.getText()));
        s.setGiaNhap(Double.parseDouble(txtGiaNhap.getText()));
        s.setGiaBan(Double.parseDouble(txtGiaBan.getText()));
        s.setSoLuong(Integer.parseInt(txtSoLuong.getText()));

        return s;
    }

    public void reset() {
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtNamXB.setText("");
        txtGiaNhap.setText("");
        txtGiaBan.setText("");
        txtSoLuong.setText("");
        txtTimKiem.setText("");

        if (cbTheLoai.getItemCount() > 0) {
            cbTheLoai.setSelectedIndex(0);
        }

        if (cbNXB.getItemCount() > 0) {
            cbNXB.setSelectedIndex(0);
        }

        bang.clearSelection();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThem) {
            if (!validateInput()) {
                return;
            }

            Sach s = getForm();

            if (sachService.themSach(s)) {
                JOptionPane.showMessageDialog(this, "Them thanh cong");
                loadData();
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "Them that bai");
            }
        }

        if (e.getSource() == btnSua) {
            if (txtMaSach.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Chon sach de sua");
                return;
            }

            if (!validateInput()) {
                return;
            }

            Sach s = getForm();

            if (sachService.suaSach(s)) {
                JOptionPane.showMessageDialog(this, "Sua thanh cong");
                loadData();
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "Sua that bai");
            }
        }

        if (e.getSource() == btnXoa) {
            if (txtMaSach.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Chon sach de xoa");
                return;
            }

            int ma = Integer.parseInt(txtMaSach.getText());

            if (sachService.xoaSach(ma)) {
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

        if (e.getSource() == btnDong) {
            dispose();
        }
    }

    public void mouseClicked(MouseEvent e) {
        int row = bang.getSelectedRow();

        if (row < 0) {
            return;
        }

        txtMaSach.setText(model.getValueAt(row, 0).toString());
        txtTenSach.setText(model.getValueAt(row, 1).toString());

        String tenTheLoai = model.getValueAt(row, 2).toString();
        String tenNXB = model.getValueAt(row, 3).toString();

        chonComboBoxTheoTen(tenTheLoai, tenNXB);

        txtNamXB.setText(model.getValueAt(row, 4).toString());
        txtGiaNhap.setText(model.getValueAt(row, 5).toString());
        txtGiaBan.setText(model.getValueAt(row, 6).toString());
        txtSoLuong.setText(model.getValueAt(row, 7).toString());
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