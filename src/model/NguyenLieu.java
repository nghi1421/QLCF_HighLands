package model;

public class NguyenLieu {
	private String maNL;
	private String tenNL;
	private String donVi;
	private float khoiLuong;
	public  NguyenLieu() {
		
		
	}
	
	public NguyenLieu(String maNL, String tenNL, String donVi, float khoiLuong) {
		super();
		this.maNL = maNL;
		this.tenNL = tenNL;
		this.donVi = donVi;
		this.khoiLuong = khoiLuong;
	}

	public String getTenNL() {
		return tenNL;
	}
	public void setTenNL(String tenNL) {
		this.tenNL = tenNL;
	}
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public float getKhoiLuong() {
		return khoiLuong;
	}
	public String getMaNL() {
		return maNL;
	}
	public void setMaNL(String maNL) {
		this.maNL = maNL;
	}
	public void setKhoiLuong(float khoiLuong) {
		this.khoiLuong = khoiLuong;
	}
}
