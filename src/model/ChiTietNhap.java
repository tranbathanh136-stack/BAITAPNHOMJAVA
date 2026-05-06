package model;

public class ChiTietNhap {
    private int maPN, maSach, soLuong;
    private double giaNhap;

    public ChiTietNhap(int maPN, int maSach, int soLuong, double giaNhap) {
        this.maPN = maPN;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
    }

    public int getMaPN() { return maPN; }
    public int getMaSach() { return maSach; }
    public int getSoLuong() { return soLuong; }
    public double getGiaNhap() { return giaNhap; }
}