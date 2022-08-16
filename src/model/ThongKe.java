package model;

public class ThongKe {
	private String tendu;
	private int soluong;
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
	public ThongKe(String tendu, int soluong) {
		super();
		this.tendu = tendu;
		this.soluong = soluong;
	}
}
