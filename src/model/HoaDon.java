package model;

public class HoaDon {
    private int maHoaDon;
    private int maKhachHang;
    private int maNhanVien;
    private double tongTien;

    public HoaDon() {}

    public HoaDon(int maKhachHang, int maNhanVien, double tongTien) {
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.tongTien = tongTien;
    }

    public int getMaKhachHang() { return maKhachHang; }
    public int getMaNhanVien() { return maNhanVien; }
    public double getTongTien() { return tongTien; }
}