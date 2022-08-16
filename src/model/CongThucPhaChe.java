package model;

public class CongThucPhaChe {
	private String maNL;
	private String tenNL;
	private String dinhLuong;
	private String donVi;
	public String getMaNL() {
		return maNL;
	}
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public void setMaNL(String maNL) {
		this.maNL = maNL;
	}
	public String getTenNL() {
		return tenNL;
	}
	public void setTenNL(String tenNL) {
		this.tenNL = tenNL;
	}
	public String getDinhLuong() {
		return dinhLuong;
	}
	public void setDinhLuong(String dinhLuong) {
		this.dinhLuong = dinhLuong;
	}
	public CongThucPhaChe(String maNL, String tenNL, String dinhLuong, String donVi) {
		super();
		this.maNL = maNL;
		this.tenNL = tenNL;
		this.dinhLuong = dinhLuong;
		this.donVi = donVi;
	}
	public CongThucPhaChe() {
		
	}
}
