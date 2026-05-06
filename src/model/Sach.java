package model;

public class Sach {
    private int maSach;
    private String tenSach;
    private String isbn;
    private int maTheLoai;
    private int maNXB;
    private int namXB;
    private double giaNhap;
    private double giaBan;
    private int soLuong;
    private String moTa;
    private String duongDanAnh;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, String isbn, int maTheLoai, int maNXB,
                int namXB, double giaNhap, double giaBan,
                int soLuong, String moTa, String duongDanAnh) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.isbn = isbn;
        this.maTheLoai = maTheLoai;
        this.maNXB = maNXB;
        this.namXB = namXB;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.duongDanAnh = duongDanAnh;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public int getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(int maNXB) {
        this.maNXB = maNXB;
    }

    public int getNamXB() {
        return namXB;
    }

    public void setNamXB(int namXB) {
        this.namXB = namXB;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getDuongDanAnh() {
        return duongDanAnh;
    }

    public void setDuongDanAnh(String duongDanAnh) {
        this.duongDanAnh = duongDanAnh;
    }
    @Override
public String toString() {
    return tenSach;
}
}