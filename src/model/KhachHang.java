package model;

public class KhachHang {
	private String maKH;
	private String hoTenKH;
	private String sdt;
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getHoTenKH() {
		return hoTenKH;
	}
	public void setHoTenKH(String hoTenKH) {
		this.hoTenKH = hoTenKH;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public KhachHang(String maKH, String hoTenKH, String sdt) {
		super();
		this.maKH = maKH;
		this.hoTenKH = hoTenKH;
		this.sdt = sdt;
	}
	public KhachHang() {
		
	}
	
}
