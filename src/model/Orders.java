package model;

import javafx.scene.image.ImageView;

public class Orders {
	private String maO;
	private String thoigianLap;
	private String hoTenNV;
	private String tenKH;
	private String trangThai;
	private ImageView thanhToan;
	private String thoiGianTT;
	public String getThoiGianTT() {
		return thoiGianTT;
	}
	public void setThoiGianTT(String thoiGianTT) {
		this.thoiGianTT = thoiGianTT;
	}
	public Orders(String maO, String thoigianLap, String hoTenNV, String tenKH, String trangThai, ImageView thanhToan, String thoiGianTT) {
		super();
		this.maO = maO;
		this.thoigianLap = thoigianLap;
		this.hoTenNV = hoTenNV;
		this.tenKH = tenKH;
		this.trangThai = trangThai;
		this.thanhToan = thanhToan;
		this.thoiGianTT = thoiGianTT;
	}
	public ImageView getThanhToan() {
		return thanhToan;
	}
	public void setThanhToan(ImageView thanhToan) {
		this.thanhToan = thanhToan;
	}
	public Orders() {
		
	}
	public String getMaO() {
		return maO;
	}
	public void setMaO(String maO) {
		this.maO = maO;
	}
	public String getThoigianLap() {
		return thoigianLap;
	}
	public void setThoigianLap(String thoigianLap) {
		this.thoigianLap = thoigianLap;
	}
	public String getHoTenNV() {
		return hoTenNV;
	}
	public void setHoTenNV(String hoTenNV) {
		this.hoTenNV = hoTenNV;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}



}
