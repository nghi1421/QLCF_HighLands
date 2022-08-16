package model;

public class Ban {
	private String maBan;
	private int tang;
	private int soLuongGhe;
	public String getMaBan() {
		return maBan;
	}
	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}
	public int getTang() {
		return tang;
	}
	public void setTang(int tang) {
		this.tang = tang;
	}
	public int getSoLuongGhe() {
		return soLuongGhe;
	}
	public void setSoLuongGhe(int soLuongGhe) {
		this.soLuongGhe = soLuongGhe;
	}
	public Ban(String maBan, int tang, int soLuongGhe) {
		super();
		this.maBan = maBan;
		this.tang = tang;
		this.soLuongGhe = soLuongGhe;
	}
	
}
