package model;

import java.math.BigDecimal;
import java.util.Date;

import views.QL_NhanVien_Controller;

public class NhanVien {
	private String maNv;
	private String hoTen;
	
	private String cccd;
	private String sdt;
	private String ngaySinh;
	private String gioiTinh;
	private String diaChi;
	private String ngayVaoLam;
	private long luong;
	private boolean trangThaiNghi;
	private String maCV;
	private String chucVu;
	public String getMaNv() {
		return maNv;
	}
	public void setMaNv(String maNv) {
		this.maNv = maNv;
	}
	public NhanVien(String maNv, String hoTen, String cccd, String sdt, String ngaySinh, String gioiTinh, String diaChi,
			String ngayVaoLam, long luong, Boolean trangThaiNghi, String maCV, String chucVu) {
		super();
		this.maNv = maNv;
		this.hoTen = hoTen;
		this.cccd = cccd;
		this.sdt = sdt;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.ngayVaoLam = ngayVaoLam;
		this.luong = luong;
		this.trangThaiNghi = trangThaiNghi;
		this.maCV = maCV;
		this.chucVu = chucVu;
	}
	
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public void setTrangThaiNghi(boolean trangThaiNghi) {
		this.trangThaiNghi = trangThaiNghi;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getCccd() {
		return cccd;
	}
	public void setCccd(String cccd) {
		this.cccd = cccd;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getNgayVaoLam() {
		return ngayVaoLam;
	}
	public void setNgayVaoLam(String ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}
	public long getLuong() {
		return luong;
	}
	public void setLuong(long luong) {
		this.luong = luong;
	}
	public Boolean getTrangThaiNghi() {
		return trangThaiNghi;
	}
	public void setTrangThaiNghi(Boolean trangThaiNghi) {
		this.trangThaiNghi = trangThaiNghi;
	}
	public String getMaCV() {
		return maCV;
	}
	public void setMaCV(String maCV) {
		this.maCV = maCV;
	}
	public NhanVien() {
		
	}
	
}
