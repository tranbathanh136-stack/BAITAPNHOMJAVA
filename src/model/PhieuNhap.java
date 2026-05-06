package model;

public class PhieuNhap {
    private int maNCC;
    private int maNV;
    private double tongTien;

    public PhieuNhap(int maNCC, int maNV, double tongTien) {
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.tongTien = tongTien;
    }

    public int getMaNCC() { return maNCC; }
    public int getMaNV() { return maNV; }
    public double getTongTien() { return tongTien; }
}