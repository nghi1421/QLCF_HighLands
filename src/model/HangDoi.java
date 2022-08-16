package model;

import com.jfoenix.controls.JFXButton;

import javafx.scene.image.ImageView;

public class HangDoi {
	private String maor;
	private ImageView hinhanh;
	private String madu;
	private String tendu;
	private int soluong;
	private String thoiGianDat;
	private JFXButton btnPhaChe;
	private JFXButton btnTraMon;
	private boolean dangPhaChe;
	private boolean datramon;
	public boolean isDangPhaChe() {
		return dangPhaChe;
	}
	public void setDangPhaChe(boolean dangPhaChe) {
		this.dangPhaChe = dangPhaChe;
	}
	public boolean isDatramon() {
		return datramon;
	}
	public void setDatramon(boolean datramon) {
		this.datramon = datramon;
	}
	public String getMaor() {
		return maor;
	}
	public void setMaor(String maor) {
		this.maor = maor;
	}
	public ImageView getHinhanh() {
		return hinhanh;
	}
	public void setHinhanh(ImageView hinhanh) {
		this.hinhanh = hinhanh;
	}
	public String getMadu() {
		return madu;
	}
	public void setMadu(String madu) {
		this.madu = madu;
	}
	public String getTendu() {
		return tendu;
	}
	public void setTendu(String tendu) {
		this.tendu = tendu;
	}
	public int getSoluong() {
		return soluong;
	}
	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}
	public String getThoiGianDat() {
		return thoiGianDat;
	}
	public void setThoiGianDat(String thoiGianDat) {
		this.thoiGianDat = thoiGianDat;
	}
	public JFXButton getBtnPhaChe() {
		return btnPhaChe;
	}
	public void setBtnPhaChe(JFXButton btnPhaChe) {
		this.btnPhaChe = btnPhaChe;
	}
	public JFXButton getBtnTraMon() {
		return btnTraMon;
	}
	public void setBtnTraMon(JFXButton btnTraMon) {
		this.btnTraMon = btnTraMon;
	}
	public HangDoi(String maor, ImageView hinhanh, String madu, String tendu, int soluong, String thoiGianDat,
			JFXButton btnPhaChe, JFXButton btnTraMon) {
		super();
		this.maor = maor;
		this.hinhanh = hinhanh;
		this.madu = madu;
		this.tendu = tendu;
		this.soluong = soluong;
		this.thoiGianDat = thoiGianDat;
		this.btnPhaChe = btnPhaChe;
		this.btnTraMon = btnTraMon;
	}
	
}
