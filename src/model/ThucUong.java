package model;

import javafx.scene.image.ImageView;

public class ThucUong {
	private String maDoUong;
	private String tenDoUong;
	private String sourceAnh;
	private ImageView hinhAnh;
	private long giaBan;
	public long getGiaBan() {
		return giaBan;
	}
	public void setGiaBan(long giaBan) {
		this.giaBan = giaBan;
	}
	private int slCoTheThucHien;
	private String khuyenmai;
	public String getMaDoUong() {
		return maDoUong;
	}
	public void setMaDoUong(String maDoUong) {
		this.maDoUong = maDoUong;
	}
	public String getTenDoUong() {
		return tenDoUong;
	}
	public void setTenDoUong(String tenDoUong) {
		this.tenDoUong = tenDoUong;
	}
	public String getSourceAnh() {
		return sourceAnh;
	}
	public void setSourceAnh(String sourceAnh) {
		this.sourceAnh = sourceAnh;
	}
	public ImageView getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(ImageView hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	public int getSlCoTheThucHien() {
		return slCoTheThucHien;
	}
	public void setSlCoTheThucHien(int slCoTheThucHien) {
		this.slCoTheThucHien = slCoTheThucHien;
	}
	public String getKhuyenmai() {
		return khuyenmai;
	}
	public void setKhuyenmai(String khuyenmai) {
		this.khuyenmai = khuyenmai;
	}
	public ThucUong(String maDoUong, String tenDoUong, String sourceAnh, ImageView hinhAnh,long giaBan, int slCoTheThucHien,
			String khuyenmai) {
		super();
		this.maDoUong = maDoUong;
		this.tenDoUong = tenDoUong;
		this.sourceAnh = sourceAnh;
		this.giaBan =giaBan;
		this.hinhAnh = hinhAnh;
		this.slCoTheThucHien = slCoTheThucHien;
		this.khuyenmai = khuyenmai;
	}
	public ThucUong() {
		
	}
}
