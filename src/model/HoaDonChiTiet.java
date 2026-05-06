package model;

public class HoaDonChiTiet {
    private int maHoaDon;
    private int maSach;
    private int soLuong;
    private double donGia;

    public HoaDonChiTiet(int maHoaDon, int maSach, int soLuong, double donGia) {
        this.maHoaDon = maHoaDon;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public int getMaHoaDon() { return maHoaDon; }
    public int getMaSach() { return maSach; }
    public int getSoLuong() { return soLuong; }
    public double getDonGia() { return donGia; }
}