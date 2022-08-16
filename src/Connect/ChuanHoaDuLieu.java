package Connect;

import java.util.ArrayList;

import model.NguyenLieu;

public class ChuanHoaDuLieu {
	public String chuanHoaChuoi(String a) {
		String s = a;
		s = s.trim();
		s = s.replaceAll(" ", " ");
		s = s.replaceAll("//s+", "");
		return s;
	}

	public String chuanHoaHoTen(String a) {
		a = chuanHoaChuoi(a);
//		System.out.println(a);
		String temp[] = a.split(" ");
		a = "";
//        for(int i=0;i<temp.length;i++) {
//        	System.out.println(temp[i]);
//        }
		for (int i = 0; i < temp.length; i++) {
			a += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
			if (i < temp.length - 1)
				a += " ";
		}
		return a;
	}

	public boolean kiemTraKhoangTrang(String a) {
		String b = a.trim();
		for (char x : b.toCharArray()) {
			if (x == ' ')
				return false;
		}
		return true;
	}

	public boolean kiemTraTenNL(String a) {
		ConnectDB cn = new ConnectDB();
		ArrayList<NguyenLieu> dsnl = cn.layDsnl();
		for (NguyenLieu nl : dsnl)
			if (nl.getTenNL().equalsIgnoreCase(a))
				return false;
		return true;
	}

	public boolean kiemTraSoThuc(String a) {
		String bieuThucSoThuc = "^(\\d+\\.)?\\d+$";
		if (a.matches(bieuThucSoThuc)) {
			return true;
		}
		return false;
	}

	public boolean kiemTraHoTenNV(String a) {
		a = chuanHoaChuoi(a);
		if (a.contains(" ")) {
			String ho = null;
			String ten = null;
			for (int i = a.length() - 1; i >= 0; i--) {
				if (a.charAt(i) == ' ') {
					ten = a.substring(i + 1);
					ho = a.substring(0, i);
					if (ho.length() <= 50 && ten.length() <= 50)
						return true;
				}
			}

		}
		return false;
	}

	// Kiểm tra kí tự đặc biệt
	public boolean kiemTraKiTuDacBiet(String a) {
		String mauKi = ("[a-zA-Z0-9]");
		if (mauKi.matches(a))
			return true;
		return false;
	}

	// Kiểm tra tên đăng nhập
	public boolean kiemTraTenDangNhap(String a) {
		boolean coKiTu = false;
		boolean coKiSo = false;
		for (char c : a.toCharArray()) {
			if (c > '0' && c < '9') {
				coKiSo = true;
				if (coKiTu)
					return true;
			}

			if ((c > 'a' && c < 'z') || (c > 'A' && c < 'Z')) {
				coKiTu = true;
				if (coKiSo)
					return true;
			}

		}
		return false;
	}

	public boolean kiemTraSoNguyen(String a) {
		try {
			Integer.parseInt(a.replaceAll(" ", ""));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean kiemTraDate(String a) {
		String mauNgay = "^(0?[1-9]|[12][0-9]|3[0,1])/(0?[1-9]|1[012])/([0-9]{4})$";
		a = chuanHoaChuoi(a);
		if (a.matches(mauNgay))
			return true;
		return false;
	}
//	public String formatTien(String a) {
//		
//	}
}
