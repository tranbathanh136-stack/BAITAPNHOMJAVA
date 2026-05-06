package view;

import model.Sach;
import model.User;
import service.SachService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SachFrame extends JFrame implements ActionListener, MouseListener {
    private User nguoiDung;
    private SachService sachService;

    private JTextField txtMaSach, txtTenSach, txtISBN, txtGiaBan, txtSoLuong;

    private JButton btnThem, btnSua, btnXoa, btnLamMoi;

    private JTable bang;
    private DefaultTableModel model;

    public SachFrame(User user) {
        nguoiDung = user;
        sachService = new SachService();

        GUI();
        phanQuyen();
        loadData();
    }

    public void GUI() {
        setTitle("Quan ly sach");
        setSize(700, 500);
        setLocationRelativeTo(null);

        txtMaSach = new JTextField();
        txtMaSach.setEditable(false);

        txtTenSach = new JTextField();
        txtISBN = new JTextField();
        txtGiaBan = new JTextField();
        txtSoLuong = new JTextField();

        JPanel pnInput = new JPanel(new GridLayout(5, 2, 10, 10));

        pnInput.add(new JLabel("Ma sach"));
        pnInput.add(txtMaSach);

        pnInput.add(new JLabel("Ten sach"));
        pnInput.add(txtTenSach);

        pnInput.add(new JLabel("ISBN"));
        pnInput.add(txtISBN);

        pnInput.add(new JLabel("Gia ban"));
        pnInput.add(txtGiaBan);

        pnInput.add(new JLabel("So luong"));
        pnInput.add(txtSoLuong);

        btnThem = new JButton("Them");
        btnSua = new JButton("Sua");
        btnXoa = new JButton("Xoa");
        btnLamMoi = new JButton("Lam moi");

        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);

        JPanel pnBtn = new JPanel();
        pnBtn.add(btnThem);
        pnBtn.add(btnSua);
        pnBtn.add(btnXoa);
        pnBtn.add(btnLamMoi);

        model = new DefaultTableModel();
        model.addColumn("Ma");
        model.addColumn("Ten");
        model.addColumn("ISBN");
        model.addColumn("Gia");
        model.addColumn("So luong");

        bang = new JTable(model);
        bang.addMouseListener(this);

        add(pnInput, BorderLayout.NORTH);
        add(new JScrollPane(bang), BorderLayout.CENTER);
        add(pnBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void phanQuyen() {
        if ("STAFF".equalsIgnoreCase(nguoiDung.getRoleName())) {
            btnXoa.setEnabled(false);
        }
    }

    public void loadData() {
        model.setRowCount(0);

        List<Sach> ds = sachService.layDanhSach();

        for (Sach s : ds) {
            model.addRow(new Object[]{
                    s.getMaSach(),
                    s.getTenSach(),
                    s.getIsbn(),
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

        try {
            Double.parseDouble(txtGiaBan.getText());
            Integer.parseInt(txtSoLuong.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gia hoac so luong sai");
            return false;
        }

        return true;
    }

    public Sach getForm() {
        Sach s = new Sach();

        if (!txtMaSach.getText().equals("")) {
            s.setMaSach(Integer.parseInt(txtMaSach.getText()));
        }

        s.setTenSach(txtTenSach.getText());
        s.setIsbn(txtISBN.getText());
        s.setGiaBan(Double.parseDouble(txtGiaBan.getText()));
        s.setSoLuong(Integer.parseInt(txtSoLuong.getText()));

        return s;
    }

    public void reset() {
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtISBN.setText("");
        txtGiaBan.setText("");
        txtSoLuong.setText("");
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnThem) {
            if (!validateInput()) return;

            Sach s = getForm();

            if (sachService.themSach(s)) {
                JOptionPane.showMessageDialog(this, "Them thanh cong");
                loadData();
                reset();
            }
        }

        if (e.getSource() == btnSua) {
            if (txtMaSach.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Chon sach de sua");
                return;
            }

            if (!validateInput()) return;

            Sach s = getForm();

            if (sachService.suaSach(s)) {
                JOptionPane.showMessageDialog(this, "Sua thanh cong");
                loadData();
                reset();
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
            }
        }

        if (e.getSource() == btnLamMoi) {
            reset();
        }
    }

    public void mouseClicked(MouseEvent e) {
        int row = bang.getSelectedRow();

        txtMaSach.setText(model.getValueAt(row, 0).toString());
        txtTenSach.setText(model.getValueAt(row, 1).toString());
        txtISBN.setText(model.getValueAt(row, 2).toString());
        txtGiaBan.setText(model.getValueAt(row, 3).toString());
        txtSoLuong.setText(model.getValueAt(row, 4).toString());
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}