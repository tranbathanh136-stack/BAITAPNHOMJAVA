package view;

import model.*;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class NhapSachFrame extends JFrame implements ActionListener, ItemListener {

    private JComboBox<Supplier> cbNCC;
    private JComboBox<Sach> cbSach;

    private JComboBox<TheLoai> cbTheLoai;
    private JComboBox<NhaXuatBan> cbNXBMoi;

    private JTextField txtTimSach;
    private JTextField txtSoLuong;
    private JTextField txtGia;

    private JTextField txtTenSachMoi;
    private JTextField txtNamXBMoi;
    private JTextField txtGiaBanMoi;

    private JCheckBox chkSachMoi;

    private JButton btnTimSach;
    private JButton btnTatCaSach;
    private JButton btnNhap;
    private JButton btnDong;

    private NhapSachService nhapSachService;
    private SupplierService supplierService;
    private SachService sachService;
    private TheLoaiService theLoaiService;
    private NhaXuatBanService nxbService;

    private User currentUser;

    public NhapSachFrame(User user) {
        currentUser = user;

        nhapSachService = new NhapSachService();
        supplierService = new SupplierService();
        sachService = new SachService();
        theLoaiService = new TheLoaiService();
        nxbService = new NhaXuatBanService();

        GUI();

        loadData();
        loadComboBox();
        capNhatUI();
    }

    public void GUI() {
        setTitle("Nhap sach");
        setSize(650, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cbNCC = new JComboBox<Supplier>();
        cbSach = new JComboBox<Sach>();

        cbTheLoai = new JComboBox<TheLoai>();
        cbNXBMoi = new JComboBox<NhaXuatBan>();

        txtTimSach = new JTextField();

        txtSoLuong = new JTextField();
        txtGia = new JTextField();

        txtTenSachMoi = new JTextField();
        txtNamXBMoi = new JTextField();
        txtGiaBanMoi = new JTextField();

        chkSachMoi = new JCheckBox("Sach moi");

        btnTimSach = new JButton("Tim");
        btnTatCaSach = new JButton("Tat ca");
        btnNhap = new JButton("Nhap");
        btnDong = new JButton("Dong");

        btnTimSach.addActionListener(this);
        btnTatCaSach.addActionListener(this);
        btnNhap.addActionListener(this);
        btnDong.addActionListener(this);
        chkSachMoi.addActionListener(this);

        cbSach.addItemListener(this);

        JPanel pnNhap = new JPanel(new GridLayout(11, 2, 10, 10));

        pnNhap.add(new JLabel("Nha cung cap:"));
        pnNhap.add(cbNCC);

        JPanel pnTimSach = new JPanel(new BorderLayout(5, 5));
        pnTimSach.add(txtTimSach, BorderLayout.CENTER);

        JPanel pnNutTim = new JPanel(new GridLayout(1, 2, 5, 5));
        pnNutTim.add(btnTimSach);
        pnNutTim.add(btnTatCaSach);

        pnTimSach.add(pnNutTim, BorderLayout.EAST);

        pnNhap.add(new JLabel("Tim sach:"));
        pnNhap.add(pnTimSach);

        pnNhap.add(new JLabel("Sach:"));
        pnNhap.add(cbSach);

        pnNhap.add(new JLabel(""));
        pnNhap.add(chkSachMoi);

        pnNhap.add(new JLabel("Ten sach moi:"));
        pnNhap.add(txtTenSachMoi);

        pnNhap.add(new JLabel("The loai:"));
        pnNhap.add(cbTheLoai);

        pnNhap.add(new JLabel("NXB:"));
        pnNhap.add(cbNXBMoi);

        pnNhap.add(new JLabel("Nam XB:"));
        pnNhap.add(txtNamXBMoi);

        pnNhap.add(new JLabel("Gia ban:"));
        pnNhap.add(txtGiaBanMoi);

        pnNhap.add(new JLabel("So luong:"));
        pnNhap.add(txtSoLuong);

        pnNhap.add(new JLabel("Gia nhap:"));
        pnNhap.add(txtGia);

        JPanel pnNut = new JPanel(new FlowLayout());
        pnNut.add(btnNhap);
        pnNut.add(btnDong);

        setLayout(new BorderLayout(10, 10));
        add(new JLabel("NHAP SACH", JLabel.CENTER), BorderLayout.NORTH);
        add(pnNhap, BorderLayout.CENTER);
        add(pnNut, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void capNhatUI() {
        boolean sachMoi = chkSachMoi.isSelected();

        txtTimSach.setEnabled(!sachMoi);
        cbSach.setEnabled(!sachMoi);
        btnTimSach.setEnabled(!sachMoi);
        btnTatCaSach.setEnabled(!sachMoi);

        txtTenSachMoi.setEnabled(sachMoi);
        cbTheLoai.setEnabled(sachMoi);
        cbNXBMoi.setEnabled(sachMoi);
        txtNamXBMoi.setEnabled(sachMoi);
        txtGiaBanMoi.setEnabled(sachMoi);

        if (sachMoi) {
            txtTimSach.setText("");
        } else {
            txtTenSachMoi.setText("");
            txtNamXBMoi.setText("");
            txtGiaBanMoi.setText("");
        }
    }

    public void loadData() {
        loadNhaCungCap();
        loadTatCaSach();
    }

    public void loadComboBox() {
        cbTheLoai.removeAllItems();
        cbNXBMoi.removeAllItems();

        for (TheLoai tl : theLoaiService.layDanhSach()) {
            cbTheLoai.addItem(tl);
        }

        for (NhaXuatBan nxb : nxbService.layDanhSach()) {
            cbNXBMoi.addItem(nxb);
        }
    }

    public void loadNhaCungCap() {
        cbNCC.removeAllItems();

        for (Supplier s : supplierService.layDanhSach()) {
            cbNCC.addItem(s);
        }
    }

    public void loadTatCaSach() {
        cbSach.removeAllItems();

        List<Sach> ds = sachService.layDanhSach();

        for (Sach s : ds) {
            cbSach.addItem(s);
        }
    }

    public void timSach() {
        cbSach.removeAllItems();

        String tuKhoa = txtTimSach.getText();

        List<Sach> ds = sachService.timTheoTen(tuKhoa);

        for (Sach s : ds) {
            cbSach.addItem(s);
        }

        if (ds.size() == 0) {
            JOptionPane.showMessageDialog(this, "Khong tim thay sach");
        }
    }

    public boolean kiemTraDuLieu() {
        if (cbNCC.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chua co nha cung cap");
            return false;
        }

        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            double giaNhap = Double.parseDouble(txtGia.getText());

            if (soLuong <= 0 || giaNhap <= 0) {
                JOptionPane.showMessageDialog(this, "So luong va gia nhap phai lon hon 0");
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "So luong hoac gia nhap khong hop le");
            return false;
        }

        if (chkSachMoi.isSelected()) {
            if (txtTenSachMoi.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "Ten sach moi khong duoc rong");
                return false;
            }

            if (cbTheLoai.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Chua co the loai");
                return false;
            }

            if (cbNXBMoi.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Chua co nha xuat ban");
                return false;
            }

            try {
                Integer.parseInt(txtNamXBMoi.getText());
                Double.parseDouble(txtGiaBanMoi.getText());

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Nam XB hoac gia ban khong hop le");
                return false;
            }

        } else {
            if (cbSach.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Chua co sach");
                return false;
            }
        }

        return true;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chkSachMoi) {
            capNhatUI();
        }

        if (e.getSource() == btnTimSach) {
            timSach();
        }

        if (e.getSource() == btnTatCaSach) {
            txtTimSach.setText("");
            loadTatCaSach();
        }

        if (e.getSource() == btnNhap) {
            if (!kiemTraDuLieu()) {
                return;
            }

            Supplier ncc = (Supplier) cbNCC.getSelectedItem();

            int soLuong = Integer.parseInt(txtSoLuong.getText());
            double giaNhap = Double.parseDouble(txtGia.getText());

            Sach sach;

            if (chkSachMoi.isSelected()) {
                TheLoai tl = (TheLoai) cbTheLoai.getSelectedItem();
                NhaXuatBan nxb = (NhaXuatBan) cbNXBMoi.getSelectedItem();

                sach = new Sach();

                sach.setTenSach(txtTenSachMoi.getText());
                sach.setMaTheLoai(tl.getMaTheLoai());
                sach.setMaNXB(nxb.getMaNXB());
                sach.setNamXB(Integer.parseInt(txtNamXBMoi.getText()));
                sach.setGiaNhap(giaNhap);
                sach.setGiaBan(Double.parseDouble(txtGiaBanMoi.getText()));
                sach.setSoLuong(0);

                boolean themSach = sachService.themSach(sach);

                if (!themSach) {
                    JOptionPane.showMessageDialog(this, "Them sach moi that bai");
                    return;
                }

                List<Sach> ds = sachService.timTheoTen(sach.getTenSach());

                if (ds.size() == 0) {
                    JOptionPane.showMessageDialog(this, "Khong tim thay sach moi vua them");
                    return;
                }

                sach = ds.get(ds.size() - 1);

            } else {
                sach = (Sach) cbSach.getSelectedItem();
            }

            boolean ketQua = nhapSachService.nhap(
                    ncc.getSupplierId(),
                    currentUser.getUserId(),
                    sach,
                    soLuong,
                    giaNhap
            );

            if (ketQua) {
                JOptionPane.showMessageDialog(this, "Nhap sach thanh cong");

                txtSoLuong.setText("");
                txtGia.setText("");

                txtTenSachMoi.setText("");
                txtNamXBMoi.setText("");
                txtGiaBanMoi.setText("");

                chkSachMoi.setSelected(false);
                capNhatUI();

                loadTatCaSach();

            } else {
                JOptionPane.showMessageDialog(this, "Nhap sach that bai");
            }
        }

        if (e.getSource() == btnDong) {
            dispose();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cbSach && e.getStateChange() == ItemEvent.SELECTED) {
            Sach s = (Sach) cbSach.getSelectedItem();

            if (s != null && !chkSachMoi.isSelected()) {
                txtGia.setText(String.valueOf(s.getGiaNhap()));
            }
        }
    }
}