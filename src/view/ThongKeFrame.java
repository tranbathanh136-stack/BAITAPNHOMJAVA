package view;

import service.ThongKeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class ThongKeFrame extends JFrame implements ActionListener {

    private JTextField txtTuNgay;
    private JTextField txtDenNgay;

    private JLabel lblDoanhThu;
    private JLabel lblLoiNhuan;
    private JLabel lblTongHoaDon;
    private JLabel lblTongSachBan;

    private JButton btnThongKe;
    private JButton btnDong;

    private JTable bangTopSach;
    private DefaultTableModel modelTopSach;

    private ThongKeService thongKeService;

    public ThongKeFrame() {
        thongKeService = new ThongKeService();

        GUI();
        setNgayMacDinh();
        loadData();
    }

    public void GUI() {
        setTitle("Thong ke");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTieuDe = new JLabel("THONG KE DOANH THU - LOI NHUAN", JLabel.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));

        txtTuNgay = new JTextField();
        txtDenNgay = new JTextField();

        btnThongKe = new JButton("Thong ke");
        btnDong = new JButton("Dong");

        btnThongKe.addActionListener(this);
        btnDong.addActionListener(this);

        JPanel pnLoc = new JPanel(new GridLayout(2, 3, 10, 10));
        pnLoc.setBorder(BorderFactory.createTitledBorder("Loc theo ngay"));

        pnLoc.add(new JLabel("Tu ngay yyyy-MM-dd:"));
        pnLoc.add(txtTuNgay);
        pnLoc.add(btnThongKe);

        pnLoc.add(new JLabel("Den ngay yyyy-MM-dd:"));
        pnLoc.add(txtDenNgay);
        pnLoc.add(btnDong);

        lblDoanhThu = new JLabel("Doanh thu: 0");
        lblLoiNhuan = new JLabel("Loi nhuan: 0");
        lblTongHoaDon = new JLabel("Tong hoa don: 0");
        lblTongSachBan = new JLabel("Tong sach ban: 0");

        Font font = new Font("Arial", Font.BOLD, 16);
        lblDoanhThu.setFont(font);
        lblLoiNhuan.setFont(font);
        lblTongHoaDon.setFont(font);
        lblTongSachBan.setFont(font);

        JPanel pnTongQuan = new JPanel(new GridLayout(2, 2, 15, 15));
        pnTongQuan.setBorder(BorderFactory.createTitledBorder("Tong quan"));
        pnTongQuan.setPreferredSize(new Dimension(850, 130));

        pnTongQuan.add(lblDoanhThu);
        pnTongQuan.add(lblLoiNhuan);
        pnTongQuan.add(lblTongHoaDon);
        pnTongQuan.add(lblTongSachBan);

        modelTopSach = new DefaultTableModel();
        modelTopSach.addColumn("Ma sach");
        modelTopSach.addColumn("Ten sach");
        modelTopSach.addColumn("So luong ban");
        modelTopSach.addColumn("Doanh thu");

        bangTopSach = new JTable(modelTopSach);

        JPanel pnTopSach = new JPanel(new BorderLayout());
        pnTopSach.setBorder(BorderFactory.createTitledBorder("Top 5 sach ban chay"));
        pnTopSach.add(new JScrollPane(bangTopSach), BorderLayout.CENTER);

        JPanel pnTren = new JPanel(new BorderLayout(10, 10));
        pnTren.add(lblTieuDe, BorderLayout.NORTH);
        pnTren.add(pnLoc, BorderLayout.CENTER);

        JPanel pnGiua = new JPanel(new BorderLayout(10, 10));
        pnGiua.add(pnTongQuan, BorderLayout.NORTH);
        pnGiua.add(pnTopSach, BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));
        add(pnTren, BorderLayout.NORTH);
        add(pnGiua, BorderLayout.CENTER);

        setVisible(true);
    }

    public void setNgayMacDinh() {
        LocalDate homNay = LocalDate.now();
        LocalDate dauThang = homNay.withDayOfMonth(1);

        txtTuNgay.setText(dauThang.toString());
        txtDenNgay.setText(homNay.toString());
    }

    public boolean kiemTraNgay() {
        if (txtTuNgay.getText().trim().equals("") || txtDenNgay.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Nhap du ngay bat dau va ngay ket thuc");
            return false;
        }

        try {
            LocalDate.parse(txtTuNgay.getText().trim());
            LocalDate.parse(txtDenNgay.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngay phai dung dinh dang yyyy-MM-dd");
            return false;
        }

        return true;
    }

    public void loadData() {
        if (!kiemTraNgay()) {
            return;
        }

        String tuNgay = txtTuNgay.getText().trim();
        String denNgay = txtDenNgay.getText().trim();

        double doanhThu = thongKeService.tinhDoanhThu(tuNgay, denNgay);
        double loiNhuan = thongKeService.tinhLoiNhuan(tuNgay, denNgay);
        int tongHoaDon = thongKeService.tongHoaDon(tuNgay, denNgay);
        int tongSachBan = thongKeService.tongSachBan(tuNgay, denNgay);

        lblDoanhThu.setText("Doanh thu: " + doanhThu);
        lblLoiNhuan.setText("Loi nhuan: " + loiNhuan);
        lblTongHoaDon.setText("Tong hoa don: " + tongHoaDon);
        lblTongSachBan.setText("Tong sach ban: " + tongSachBan);

        modelTopSach.setRowCount(0);

        List<Object[]> dsTop = thongKeService.topSachBanChay(tuNgay, denNgay);

        for (Object[] row : dsTop) {
            modelTopSach.addRow(row);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThongKe) {
            loadData();
        }

        if (e.getSource() == btnDong) {
            dispose();
        }
    }
}