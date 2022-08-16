package model;

import java.math.BigDecimal;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CT_Orders {
	private String ct_tuo;
	private String mao;
	private ImageView anh;
	private String ten;
	private BigDecimal donGia;
	private String trangThai;
	private Button tru;
	public CT_Orders() {
		
	}
	public ImageView getAnh() {
		return anh;
	}
	public void setAnh(ImageView anh) {
		this.anh = anh;
	}
	public String getTen() {
		return ten;
	}
	public CT_Orders(String ct_tuo,String mao, String ten, BigDecimal donGia, String trangThai) {
		super();
		this.ct_tuo = ct_tuo;
		this.mao = mao;
		this.ten = ten;
		this.donGia = donGia;
		this.trangThai = trangThai;
	}
	
	public String getCt_tuo() {
		return ct_tuo;
	}
	public void setCt_tuo(String ct_tuo) {
		this.ct_tuo = ct_tuo;
	}
	public String getMao() {
		return mao;
	}
	public void setMao(String mao) {
		this.mao = mao;
	}
	public BigDecimal getDonGia() {
		return donGia;
	}
	public void setDonGia(BigDecimal donGia) {
		this.donGia = donGia;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public Button getTru() {
		return tru;
	}
	public void setTru(Button tru) {
		this.tru = tru;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	
}
