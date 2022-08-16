package model;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class DrinkForOrdersViews {
	private String maDoUong;
	private String tenDoUong;
	private String sourceAnh;
	private long giaBan;
	private int soLuong;
	private JFXButton tru;
	private ImageView hinhAnh;
	private int slCoTheThucHien;
	private float giamGia;

	public float getGiamGia() {
		return giamGia;
	}

	
	
	public void setGiamGia(float giamGia) {
		this.giamGia = giamGia;
	}

	public int getSlCoTheThucHien() {
		return slCoTheThucHien;
	}

	public void setSlCoTheThucHien(int slCoTheThucHien) {
		this.slCoTheThucHien = slCoTheThucHien;
	}

	public ImageView getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(ImageView hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public DrinkForOrdersViews(String maDoUong, String tenDoUong, String sourceAnh, long giaBan, int soLuong) {
		super();
		this.maDoUong = maDoUong;
		this.tenDoUong = tenDoUong;
		this.sourceAnh = sourceAnh;
		this.giaBan = giaBan;
		this.soLuong = soLuong;
	}

	public String getMaDoUong() {
		return maDoUong;
	}

	public JFXButton getTru() {
		return tru;
	}

	public void setTru(JFXButton tru) {
		this.tru = tru;
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

	public long getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(long giaBan) {
		this.giaBan = giaBan;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public DrinkForOrdersViews() {
		
	}
}
