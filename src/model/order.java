package model;

public class order {
	private String tenDoUong;
	private String imgSource;
	private String ghiChu;
	
	private int soLuong;
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getTenDoUong() {
		return tenDoUong;
	}
	public void setTenDoUong(String tenDoUong) {
		this.tenDoUong = tenDoUong;
	}
	public String getImgSource() {
		return imgSource;
	}
	public void setImgSource(String imgSource) {
		this.imgSource = imgSource;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public order(int soLuong,String tenDoUong, String imgSource, String ghiChu) {
		super();
		this.soLuong =  soLuong;
		this.tenDoUong = tenDoUong;
		this.imgSource = imgSource;
		this.ghiChu = ghiChu;
	}
	public order() {
		
	}
	
}
