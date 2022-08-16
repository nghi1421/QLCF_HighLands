package model;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Ctord {
	private String madu;
	

	private ImageView sourceAnh;
	
	private String tenTu;
	
	private long donGia;
	
	private int soLuong;
	
	private JFXButton btnTru;
	
	public String getMadu() {
		return madu;
	}

	public void setMadu(String madu) {
		this.madu = madu;
	}
	


	public Ctord(String madu, ImageView sourceAnh, String tenTu, long donGia, int soLuong, JFXButton btnTru) {
		super();
		this.madu = madu;
		this.sourceAnh = sourceAnh;
		this.tenTu = tenTu;
		this.donGia = donGia;
		this.soLuong = soLuong;
		this.btnTru = btnTru;
	}

	public ImageView getSourceAnh() {
		return sourceAnh;
	}

	public void setSourceAnh(ImageView sourceAnh) {
		this.sourceAnh = sourceAnh;
	}

	public String getTenTu() {
		return tenTu;
	}

	public void setTenTu(String tenTu) {
		this.tenTu = tenTu;
	}

	public long getDonGia() {
		return donGia;
	}

	public void setDonGia(long donGia) {
		this.donGia = donGia;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public JFXButton getBtnTru() {
		return btnTru;
	}

	public void setBtnTru(JFXButton btnTru) {
		this.btnTru = btnTru;
	}

	public Ctord() {
		
	}
}
