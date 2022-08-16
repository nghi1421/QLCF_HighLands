package model;

public class Ctpn {
	private String tenNl;
	private String soLuong;
	private String donVi;
	private String maNl;
	private String donGia;
	public String getTenNl() {
		return tenNl;
	}
	public void setTenNl(String tenNl) {
		this.tenNl = tenNl;
	}
	public String getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(String soLuong) {
		this.soLuong = soLuong;
	}
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public String getMaNl() {
		return maNl;
	}
	public void setMaNl(String maNl) {
		this.maNl = maNl;
	}
	public String getDonGia() {
		return donGia;
	}
	public void setDonGia(String donGia) {
		this.donGia = donGia;
	}
	public Ctpn(String tenNl, String soLuong, String donVi, String maNl, String donGia) {
		super();
		this.tenNl = tenNl;
		this.soLuong = soLuong;
		this.donVi = donVi;
		this.maNl = maNl;
		this.donGia = donGia;
	}
	public Ctpn() {
		
	}
}
