package model;

public class PhieuNhap {
	private String maPn;
	private String tenNv;
	private String thoiGianLap;
	private String tenNCC;
	private String trangThai;
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public String getMaPn() {
		return maPn;
	}
	public void setMaPn(String maPn) {
		this.maPn = maPn;
	}
	public String getTenNv() {
		return tenNv;
	}
	public void setTenNv(String tenNv) {
		this.tenNv = tenNv;
	}
	public String getThoiGianLap() {
		return thoiGianLap;
	}
	public void setThoiGianLap(String thoiGianLap) {
		this.thoiGianLap = thoiGianLap;
	}
	public String getTenNCC() {
		return tenNCC;
	}
	public void setTenNCC(String tenNCC) {
		this.tenNCC = tenNCC;
	}
	public PhieuNhap() {
		
	}
	public PhieuNhap(String maPn, String tenNv, String thoiGianLap, String tenNCC,String trangThai) {
		super();
		this.maPn = maPn;
		this.tenNv = tenNv;
		this.thoiGianLap = thoiGianLap;
		this.tenNCC = tenNCC;
		this.trangThai = trangThai;
	}
}
