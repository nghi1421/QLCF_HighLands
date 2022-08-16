package Connect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jfoenix.controls.JFXButton;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import model.CongThucPhaChe;
import model.Ctord;
import model.Ctpn;
import model.DrinkForOrdersViews;
import model.HangDoi;
import model.KhachHang;
import model.NguyenLieu;
import model.NhanVien;
import model.Orders;
import model.PhieuNhap;
import model.ThongKe;
import model.ThucUong;

public class ConnectDB {
	public Connection getConnectionToSQL() {
		Connection con = null;
		try {
			String url = "jdbc:sqlserver://DESKTOP-5OHR22L:1433;databaseName=QLCAFE";
			String user = "sa";
			String pass = "123123";
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return con;
	}

	public Connection roleConnection(String loginName) {
		Connection con = null;
		try {
			String url = "jdbc:sqlserver://DESKTOP-5OHR22L:1433;databaseName=QLCAFE";
			String pass = "123123";
			con = DriverManager.getConnection(url, loginName, pass);
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return con;
	}

	// Tào tài khoản cho nhân viên
	public boolean coSuDungPM(String macv) {
		String query = "SELECT MAQUYEN FROM QUYEN";
		int mq = Integer.parseInt(macv.substring(2, macv.length()));
		try {
			Connection cn = roleConnection("quanli");
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				if (rs.getInt(1) == mq) {
					return true;
				}
			}
		} catch (SQLException e) {
			return false;
		}

		return false;
	}

	// Kiểm tra nhân viên có tài khoản hay chưa
	public boolean daCoTaiKhoan(String manv) {
		String query = "SELECT TENDANGNHAP FROM TAIKHOAN WHERE MANV = ?";
		int test = 0;
		try {
			Connection cn = roleConnection("quanli");
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, manv);
			ResultSet rs = prs.executeQuery();

			// nut tru thong tin
			while (rs.next()) {
				test = 1;
			}
			rs.close();
			cn.close();
		} catch (SQLException e) {
			return false;
		}
		if (test == 0)
			return false;
		return true;
	}

	public boolean taoTaiKhoan(String username, String password, String manv, String macv) {
		int mq = Integer.parseInt(macv.substring(2, macv.length()));
		try {
			Connection cn = roleConnection("quanli");
			CallableStatement cs = cn.prepareCall("{call dbo.SP_THEM_TAI_KHOAN(?,?,?,?)}");
			cs.setString(1, username);
			cs.setString(2, password);
			cs.setString(3, manv);
			cs.setInt(4, mq);
			cs.execute();
			cs.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// Kiem tra ten dang nhap trung
	public boolean kiemTraTenDangNhap(String tenDangNhap) {
		String query = "SELECT TENDANGNHAP FROM TAIKHOAN WHERE TENDANGNHAP = ? ";
		int test = 0;
		try {
			Connection cn = roleConnection("quanli");
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, tenDangNhap);
			ResultSet rs = prs.executeQuery();

			// nut tru thong tin
			while (rs.next()) {
				test = 1;
			}
			rs.close();
			cn.close();
		} catch (SQLException e) {
			return false;
		}
		if (test == 0)
			return false;
		return true;
	}

	// Lấy lại mật khẩu
	public boolean macDinhTaiKhoan(String maNv) {
		String query = "UPDATE TAIKHOAN\r\n" + "	SET PASSWORD = '123456'\r\n" + "	WHERE MANV = ?";
		int thanhCong = 0;
		try {
			Connection cn = roleConnection("quanli");
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, maNv);
			thanhCong = prs.executeUpdate();

			cn.close();
		} catch (SQLException e) {
			return false;
		}
		if (thanhCong == 1)
			return true;
		return false;
	}

	// Kiem tra khoa ngoai nhan vien
	public int kiemTraKhoaNgoai_NhanVien(String maNv) {
		int thanhCong = -1;
		try {
			Connection cn = roleConnection("quanli");
			CallableStatement cstmt = cn.prepareCall("{? = call dbo.UDF_CHECK_KHOANGOAI_NHANVIEN(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, maNv);
			cstmt.execute();
			thanhCong = cstmt.getInt(1);

			cn.close();
		} catch (SQLException e) {
			return -1;
		}
		return thanhCong;
	}

	public int kiemTraKhoaNgoai_DoUong(String madu) {
		int thanhCong = -1;
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cstmt = cn.prepareCall("{? = call dbo.UDF_CHECK_KHOANGOAI_DOUONG(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, madu);
			cstmt.execute();
			thanhCong = cstmt.getInt(1);
			cn.close();
		} catch (SQLException e) {
			return -1;
		}
		return thanhCong;
	}

	public int kiemTraKhoaNgoai_NguyenLieu(String manl) {
		int thanhCong = -1;
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cstmt = cn.prepareCall("{? = call dbo.UDF_CHECK_KHOANGOAI_NGUYENLEU(?)}");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, manl);
			cstmt.execute();
			thanhCong = cstmt.getInt(1);
			cn.close();
		} catch (SQLException e) {
			return -1;
		}
		return thanhCong;
	}

	public boolean xoaNguyenLieu(String manl) {
		String query1 = "DELETE NGUYENLIEU WHERE MANL = ?";
		try {
			Connection cn = getConnectionToSQL();
			PreparedStatement prs = cn.prepareStatement(query1);
			prs.setString(1, manl);
			prs.execute();
			prs.close();
			cn.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public boolean xoaDoUong(String matu) {
		String query1 = "DELETE CTPC WHERE MATU = ?";
		String query2 = "DELETE THUCUONG WHERE MATU = ?";
		try {
			Connection cn = roleConnection("quanli");
			PreparedStatement prs = cn.prepareStatement(query1);
			prs.setString(1, matu);
			prs.execute();
			prs = cn.prepareStatement(query2);
			prs.setString(1, matu);
			prs.execute();
			prs.close();
			cn.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	// Xoa thông tin nhân vien
	public int xoaNhanVien(String manv) {
		String query = "DELETE NHANVIEN WHERE MANV = ?";
		int thanhCong = 0;
		try {
			Connection cn = roleConnection("quanli");
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, manv);
			thanhCong = prs.executeUpdate();
			cn.close();
		} catch (SQLException e) {
			return 0;
		}
		return thanhCong;
	}

	public ArrayList<NhanVien> layDSNV() {
		ArrayList<NhanVien> dsnv = new ArrayList();
		Map<String, String> chucVu = layChucVu();
		String query = "select * from NHANVIEN";
		try {
			Connection cn = roleConnection("quanli");
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				String maNv = rs.getString("MANV");
				String Hoten = rs.getString("HO") + " " + rs.getString("TEN");
				// String cCCD= rs.getString("CCCD");
				String cCCD = rs.getString("CCCD");
				String sDT = rs.getString("SDT");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String ngaySinh = sdf.format(rs.getDate("NGAYSINH"));
//                Date ngaySinh = rs.getDate("NGAYSINH");
				String gioiTinh = rs.getString("GIOITINH");
				String diaChi = rs.getString("DIACHI");
				String ngayVaoLam = sdf.format(rs.getDate("NGAYVAOLAM"));
				long luong = rs.getLong("LUONG");
				Boolean trangThaiNghi = rs.getBoolean("TRANGTHAINGHI");
				String maCv = rs.getString("MACV");
				dsnv.add(new NhanVien(maNv, Hoten, cCCD, sDT, ngaySinh, gioiTinh, diaChi, ngayVaoLam, luong,
						trangThaiNghi, maCv, chucVu.get(maCv)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsnv;
	}

	public boolean themNhanVien(NhanVien nv) {
		try {
			Connection con = getConnectionToSQL();
			CallableStatement cs = con.prepareCall("{call dbo.SP_THEM_TTNV(?,?,?,?,?,?,?,?,?,?,?)}");
			String ho = null;
			String ten = null;
			for (int i = nv.getHoTen().length() - 1; i >= 0; i--) {
				if (nv.getHoTen().charAt(i) == ' ') {
					ten = nv.getHoTen().substring(i + 1);
					ho = nv.getHoTen().substring(0, i);
					break;
				}
			}
//			System.out.println(ho);
//			System.out.println(ten);
			
			cs.setString(1, ho);
			cs.setString(2, ten);
			cs.setString(3, nv.getCccd());
			cs.setString(4, nv.getSdt());
			cs.setString(5, nv.getNgaySinh());
			cs.setString(6, nv.getGioiTinh());
			cs.setString(7, nv.getDiaChi());
			cs.setString(8, nv.getNgayVaoLam());
			cs.setLong(9, nv.getLuong());
			cs.setBoolean(10, nv.getTrangThaiNghi());
			cs.setString(11, nv.getMaCV());

			cs.execute();
			cs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// Sua thong tin nhan vien
	public boolean suaNhanVien(NhanVien nv) {
		try {
			Connection con = getConnectionToSQL();
			CallableStatement cs = con.prepareCall("{call dbo.SP_SUA_NHANVIEN(?,?,?,?,?,?,?,?,?,?,?,?)}");
			String ho = null;
			String ten = null;
			for (int i = nv.getHoTen().length() - 1; i >= 0; i--) {
				if (nv.getHoTen().charAt(i) == ' ') {
					ten = nv.getHoTen().substring(i + 1);
					ho = nv.getHoTen().substring(0, i);
					break;
				}
			}
			cs.setString(1, nv.getMaNv());
			cs.setString(2, ho);
			cs.setString(3, ten);
			cs.setString(4, nv.getCccd());
			cs.setString(5, nv.getSdt());
			cs.setString(6, nv.getNgaySinh());
			cs.setString(7, nv.getGioiTinh());
			cs.setString(8, nv.getDiaChi());
			cs.setString(9, nv.getNgayVaoLam());
			cs.setLong(10, nv.getLuong());
			cs.setBoolean(11, nv.getTrangThaiNghi());
			cs.setString(12, nv.getMaCV());
			cs.execute();
			cs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// Lay thong tin chuc vu
	public Map<String, String> layChucVu() {
		Map<String, String> mCV = new HashMap<String, String>();
		String query = "SELECT * FROM CHUCVU";
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				mCV.put(rs.getString("MACV"), rs.getString("TENCV"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mCV;
	}

	// Lay danh sach nguyen lieu tu database
	public ArrayList<NguyenLieu> layDsnl() {
		ArrayList<NguyenLieu> dsnl = new ArrayList();
		String query = "select * from NGUYENLIEU";
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				String maNL = rs.getString("MANL");
				String tenNL = rs.getString("TENNL");
				String donVi = rs.getString("DONVI");
				float soLuong = rs.getFloat("SOLUONG");
				dsnl.add(new NguyenLieu(maNL, tenNL, donVi, soLuong));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsnl;
	}
	
	

	// Them nguyen lieu moi
	public boolean themNguyenLieu(NguyenLieu nl) {
		try {
			Connection con = getConnectionToSQL();

			CallableStatement cs = con.prepareCall("{call dbo.SP_THEM_NGUYENLIEU(?,?,?)}");
			cs.setString(1, nl.getTenNL());
			cs.setString(2, nl.getDonVi());
			cs.setFloat(3, nl.getKhoiLuong());
			cs.execute();

			cs.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	// Kiem tra ten DU
	public boolean kiemTraTenDoUong(String tenDu) {
		String query = "SELECT * FROM THUCUONG WHERE TEN = N'" + tenDu + "'";
		String test = "";
		try {
			Connection cn = roleConnection("quanli");
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				test = rs.getString(1);
			}
			rs.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		if (!test.isEmpty())
			return false;
		return true;
	}

	// Lay danh sách giảm giá
	public ArrayList<String> layDanhSachGiamGia() {
		ArrayList<String> mCV = new ArrayList<>();
		String query = "SELECT PHANTRAM FROM GIAMGIA";
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				mCV.add(String.valueOf(rs.getFloat("PHANTRAM") * 100) + "%");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mCV;
	}

	// Them thuc uong
	public boolean themThucUong(String ten, String anh, long gia, String magg) {
		int thanhCong = 0;
		try {
			Connection con = getConnectionToSQL();
			CallableStatement cs = con.prepareCall("{call dbo.SP_THEM_THUC_UONG(?,?,?,?)}");
			cs.setString(1, ten);
			cs.setString(2, anh);
			cs.setLong(3, gia);
			cs.setString(4, magg);
			thanhCong = cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (thanhCong == 0)
			return false;
		return true;
	}

	// Them công thức pha chế
	public boolean themCongThucPhaChe(ArrayList<CongThucPhaChe> ctpc, String matu) {
		try {
			Connection cn = roleConnection("quanli");
			Statement st = cn.createStatement();
			for (CongThucPhaChe ct : ctpc) {
				st.executeUpdate("INSERT INTO CTPC VALUES('" + ct.getMaNL() + "'," + "'" + matu + "',"
						+ ct.getDinhLuong() + ")");
			}
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	// Lay ma thuc uong
	public String layMaTU() {
		String matu;
		try {
			Connection con = getConnectionToSQL();
			CallableStatement cs1 = con.prepareCall("{call dbo.SP_TUSINH_THUCUONG(?)}");
			cs1.setString(1, "");
			cs1.registerOutParameter(1, Types.NVARCHAR);
			cs1.execute();
			matu = cs1.getString(1);
			con.close();
			cs1.close();
		} catch (SQLException e) {
			return null;
		}
		return matu;
	}

	// Lay danh sach do uong tu Database
	public ArrayList<DrinkForOrdersViews> layDSDU() {
		ArrayList<DrinkForOrdersViews> dsdu = new ArrayList();
		String query = "select * from VIEW_SL_THUCUONG_ORDERS_VIEW";
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				String maDU = rs.getString("MATU");
				String tenDU = rs.getString("TEN");
				String soureAnh = rs.getString("ANH");
				long giaBan = rs.getLong("GIA");
				int slCon = rs.getInt("SOLUONG");
				float giamGia = rs.getFloat("KHUYENMAI");
				DrinkForOrdersViews a = new DrinkForOrdersViews(maDU, tenDU, soureAnh, giaBan, 1);

				JFXButton bTru = new JFXButton();
				bTru.setStyle("-fx-border-radius: 20.0px;\r\n" + "    -fx-background-radius: 20.0;\r\n"
						+ "    -fx-border-color: #879095; ");
				a.setGiamGia(giamGia);
				Image img = new Image("/image/TN/icons8_minus_64px.png");
				ImageView view = new ImageView(img);
				view.setFitHeight(25);
				view.setFitWidth(25);
				bTru.setGraphic(view);
				bTru.setPrefSize(20, 40);

				a.setTru(bTru);

				ImageView haTu = new ImageView(new Image(a.getSourceAnh()));
				haTu.setFitHeight(95);
				haTu.setFitWidth(85);
				a.setHinhAnh(haTu);

				a.setSlCoTheThucHien(slCon);
				dsdu.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsdu;

	}

	// THEM GIAM GIA
	public boolean themGiamGia(double phanTram) {
		try {
			Connection con = getConnectionToSQL();
			CallableStatement cs1 = con.prepareCall("{call dbo.SP_THEM_GIAMGIA(?,?)}");
			cs1.setString(1, "");
			cs1.setDouble(2, phanTram);
			cs1.execute();

			con.close();
			cs1.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	// Lay CTPC
	public ArrayList<CongThucPhaChe> layDanhSachCTPC(String maTu) {
		String query = "SELECT NL.MANL,NL.TENNL,DINHLUONG,DONVI FROM CTPC CT, (SELECT TENNL,MANL,DONVI FROM NGUYENLIEU) NL WHERE MATU = ? AND NL.MANL = CT.MANL";
		ArrayList<CongThucPhaChe> dsct = new ArrayList<>();
		try {
			Connection cn = roleConnection("quanli");
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, maTu);
			ResultSet rs = prs.executeQuery();
			while (rs.next()) {
				CongThucPhaChe ct = new CongThucPhaChe(rs.getString(1), rs.getString(2), String.valueOf(rs.getFloat(3)),
						rs.getString(4));
				dsct.add(ct);
			}
			rs.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
//			return null;
		}
		return dsct;
	}

	// Sua thong tin do uong
	public boolean suaDoUong(String madu, String ten, String anh, Long gia, String magg) {
		try {
			Connection con = getConnectionToSQL();
			CallableStatement cs1 = con.prepareCall("{call dbo.SP_SUA_THUCUONG(?,?,?,?,?)}");
			cs1.setString(1, madu);
			cs1.setString(2, ten);
			cs1.setString(3, anh);
			cs1.setLong(4, gia);
			cs1.setString(5, magg);
			cs1.execute();
			con.close();
			cs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}

	// Sua nguyen lieu
	public boolean suaNguyenLieu(String manl, String tennl, String donvi, float soluong) {
		String query = "UPDATE NGUYENLIEU SET TENNL = ?, DONVI = ?, SOLUONG = ? WHERE MANL = ?";
		try {
			Connection con = roleConnection("quanli");
			PreparedStatement prs = con.prepareStatement(query);
			prs.setString(1, tennl);
			prs.setString(2, donvi);
			prs.setFloat(3, soluong);
			prs.setString(4, manl);
			prs.execute();
			con.close();
			prs.close();
		} catch (SQLException e) {
//			e.printStackTrace();
			return false;

		}
		return true;
	}

	// Lay danh sach do uong
	public ArrayList<ThucUong> layDanhSachDoUong() {
		String query = "SELECT * FROM VIEW_SL_THUCUONG_ORDERS_VIEW";
		ArrayList<ThucUong> dstu = new ArrayList();
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				String maDU = rs.getString("MATU");
				String tenDU = rs.getString("TEN");
				String soureAnh = rs.getString("ANH");
				long giaBan = rs.getLong("GIA");
				int slCon = rs.getInt("SOLUONG");
				float khuyenMai = rs.getFloat("KHUYENMAI");

				ImageView haTu = new ImageView(new Image(soureAnh));
				haTu.setFitHeight(120);
				haTu.setFitWidth(100);
				ThucUong tu = new ThucUong(maDU, tenDU, soureAnh, haTu, giaBan, slCon,
						String.valueOf(khuyenMai * 100) + "%");
				dstu.add(tu);
			}
		} catch (SQLException e) {
		}
		return dstu;
	}

	// Lay danh sach khach hang tu csdl
	public ArrayList<KhachHang> layDSKH() {
		String query = "select * from KHACHHANG";
		ArrayList<KhachHang> dskh = new ArrayList();
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				String maKH = rs.getString("MAKH");
				String hoTen = rs.getString("HO") + " " + rs.getString("TEN");
				String sdt = rs.getString("SDT");
				dskh.add(new KhachHang(maKH, hoTen, sdt));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dskh;
	}

	public KhachHang timKiemKhachHang(String a) {
		ArrayList<KhachHang> dskh = layDSKH();
		for (KhachHang kh : dskh) {
			if (kh.getMaKH().equalsIgnoreCase(a)) {
				return kh;
			}
		}
		return null;
	}

	// Lap phieu order
	public boolean lapOrder(String manv, String makh, String thoigian) {
		try {
			Connection con = getConnectionToSQL();
			CallableStatement cs1 = con.prepareCall("{call dbo.SP_LAP_ORDERS(?,?,?)}");
			cs1.setString(1, thoigian);
			cs1.setString(2, manv);
			cs1.setString(3, makh);
			cs1.execute();
			cs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// Lay maor
	public String layMaOrder() {
		String maor;
		try {
			Connection con = getConnectionToSQL();
			CallableStatement cs1 = con.prepareCall("{call dbo.SP_TUSINH_ORDERS(?)}");
			cs1.setString(1, "");
			cs1.registerOutParameter(1, Types.NVARCHAR);
			cs1.execute();
			maor = cs1.getString(1);
			cs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return maor;
	}

	// Ghi thoong tin chi tiet order
	public boolean themCTORD(String maor, ArrayList<DrinkForOrdersViews> orlist) {

		try {
			Connection con = getConnectionToSQL();
			for (DrinkForOrdersViews d : orlist) {
				CallableStatement cs1 = con.prepareCall("{call dbo.SP_GHI_CTORD(?,?,?,?)}");
				cs1.setString(1, maor);
				cs1.setString(2, d.getMaDoUong());
				cs1.setLong(3,d.getGiaBan());
				cs1.setInt(4, d.getSoLuong());
				cs1.execute();
				cs1.close();

			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	// Thanh toan
	public boolean thanhToan(String maor) {
		String query = "UPDATE ORDERS SET  THOIGIANTT = GETDATE() WHERE MAOR = ?";
		try {
			Connection con = roleConnection("thungan");
			PreparedStatement prs = con.prepareStatement(query);
			prs.setString(1, maor);
			prs.execute();
			con.close();
			prs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}

	// Lay thoi gian thanh toán
	public String layThoiGianThanhToan(String maor) {
		String thoiGian;
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_LAY_DATETIME_THANHTOAN_ORDER(?,?)}");
			cs1.setString(1, maor);
			cs1.setString(2, "");
			cs1.registerOutParameter(2, Types.NVARCHAR);
			cs1.execute();
			thoiGian = cs1.getString(2);
			cs1.close();
		} catch (SQLException e) {
			return null;
		}
		return thoiGian;
	}

	// Lay danh sach order
	public ArrayList<DrinkForOrdersViews> layDanhOrder(String maor) {
		ArrayList<DrinkForOrdersViews> dsdu = new ArrayList();
		String query = "SELECT TEN,SOLUONG,DONGIA FROM CTORD CT, THUCUONG TU WHERE TU.MATU = CT.MATU AND CT.MAOR = ? ";
		try {
			Connection cn = roleConnection("thungan");
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, maor);
			ResultSet rs = prs.executeQuery();

			while (rs.next()) {
				String tenDU = rs.getString("TEN");
				int soLuong = rs.getInt("SOLUONG");
				long donGia = rs.getLong("DONGIA");
				DrinkForOrdersViews a = new DrinkForOrdersViews();
				a.setGiaBan(donGia);
				a.setTenDoUong(tenDU);
				a.setSoLuong(soLuong);
				dsdu.add(a);
			}
			cn.close();
			prs.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return dsdu;
	}

	public String layThoiGianLapDon(String maor) {
		String thoiGian;
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_LAY_DATE_TIME_ORDER(?,?)}");
			cs1.setString(1, maor);
			cs1.setString(2, "");
			cs1.registerOutParameter(2, Types.NVARCHAR);
			cs1.execute();
			thoiGian = cs1.getString(2);
			cs1.close();
		} catch (SQLException e) {
			return null;
		}
		return thoiGian;
	}

	public long layTongTienOrder(String maor) {
		long tongTien = 0;
		String query = "SELECT SUM(SOLUONG*DONGIA) FROM CTORD WHERE MAOR = ?";
		try {
			Connection cn = getConnectionToSQL();
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, maor);
			ResultSet rs = prs.executeQuery();
			while (rs.next()) {
				tongTien = rs.getLong(1);
			}
			cn.close();
			rs.close();
			prs.close();
		} catch (SQLException e) {
			return 0;
		}
		return tongTien;
	}

//	public String timKiemKhachHang(String hoTen,String sdt) {
//		String query = "SELECT MAKH FROM KHACHHANG WHERE HO +' ' +TEN = N'"+hoTen+"' AND SDT = '"+sdt+"'";
//		String maKh = null;
//		try {
//			Connection cn = getConnectionToSQL();
//			java.sql.Statement s = cn.createStatement();
//			ResultSet rs = s.executeQuery(query);
//			while (rs.next()) {
//				maKh =rs.getString(1);
//			}
//		} catch (SQLException e) {
//			
//		}
//		return maKh;
//	}

	// Lay ma thuc uong order dua vao maOrder va maTu
	public int lay_MATUO(String maOrder, String maTu) {
		String query = "select MATUO from CTORD where MAOR = ? AND MATU = ?";
		int maTUO = 0;

		try {
			Connection cn = getConnectionToSQL();
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, maOrder);
			prs.setString(2, maTu);
			ResultSet rs = prs.executeQuery();
			while (rs.next()) {
				maTUO = rs.getInt("MATUO");
			}
			rs.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return maTUO;
	}

	// Them thong tin khach hang
	public boolean themKhachHang(String tenKH, String sdt) {
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_THEM_KHACHHANG(?,?,?)}");
			String ho = null;
			String ten = null;
			for (int i = tenKH.length() - 1; i >= 0; i--) {
				if (tenKH.charAt(i) == ' ') {
					ten = tenKH.substring(i + 1);
					ho = tenKH.substring(0, i);
					break;
				}
			}
			cs1.setString(1, ho);
			cs1.setString(2, ten);
			cs1.setString(3, sdt);
			cs1.execute();
			cs1.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	// Sua thong tin khahc hang
	public boolean suaKhachHang(String maKh, String tenKH, String sdt) {
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_SUA_KHACHHANG(?,?,?,?)}");
			String ho = null;
			String ten = null;
			for (int i = tenKH.length() - 1; i >= 0; i--) {
				if (tenKH.charAt(i) == ' ') {
					ten = tenKH.substring(i + 1);
					ho = tenKH.substring(0, i);
					break;
				}
			}
			cs1.setString(1, maKh);
			cs1.setString(2, ho);
			cs1.setString(3, ten);
			cs1.setString(4, sdt);
			cs1.execute();
			cs1.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	// Lay thong tin order cua khach hang tu database
	public ArrayList<Orders> layDsOrders() {
		String query = "select * from VIEW_DS_ORDERS";
		ArrayList<Orders> dso = new ArrayList();
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				String maO = rs.getString(1);
				String thoiGian = rs.getString(2);
				String hoTenNV = rs.getString(3);
				String hotenKH = rs.getString(4);
				String trangThai = rs.getString(5);
				String thoiGianTT = rs.getString(6);
				ImageView img = new ImageView(
						new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png")));

				if (thoiGianTT == null)
					img = new ImageView(new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png")));

				img.setFitHeight(30);
				img.setFitWidth(30);
				if (trangThai.equals("X")) {
					dso.add(new Orders(maO, thoiGian, hoTenNV, hotenKH, "Đã xác nhận", img, thoiGianTT));
				} else if (trangThai.equals("P")) {
					dso.add(new Orders(maO, thoiGian, hoTenNV, hotenKH, "Đang pha chế", img, thoiGianTT));
				} else if (trangThai.equals("T")) {
					dso.add(new Orders(maO, thoiGian, hoTenNV, hotenKH, "Đã trả món", img, thoiGianTT));
				}
				else if (trangThai.equals("H")) {
					dso.add(new Orders(maO, thoiGian, hoTenNV, hotenKH, "Hủy", img, thoiGianTT));
				}
			}
			cn.close();
			rs.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return dso;
	}

	// Lay thong tin chi tiet order cua khach hang
	public ArrayList<Ctord> layDsCT_Orders(String maor) {
		String query = "SELECT TU.MATU,ANH,TEN,DONGIA,SOLUONG\r\n" + "	FROM THUCUONG TU, CTORD CT \r\n"
				+ "	WHERE CT.MAOR = ? AND CT.MATU = TU.MATU";
		ArrayList<Ctord> dso = new ArrayList();
		try {
			Connection cn = roleConnection("thungan");
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, maor);
			ResultSet rs = prs.executeQuery();
			while (rs.next()) {
				String maTu = rs.getString("MATU");
				String anh = rs.getString("ANH");
				String ten = rs.getString("TEN");
				long donGia = rs.getLong("DONGIA");
				int soLuong = rs.getInt("SOLUONG");

				JFXButton b = new JFXButton();

				b.setBorder(null);
				b.setStyle("-fx-border-radius: 20.0px;\r\n" + "    -fx-background-radius: 20.0;\r\n"
						+ "    -fx-border-color: #879095; ");

//				Image img = new Image("/image/QL/subtract_52px.png");
				Image img = new Image("/image/TN/icons8_minus_64px.png");
				ImageView view = new ImageView(img);
				view.setFitHeight(25);
				view.setFitWidth(25);
				b.setGraphic(view);
				b.setPrefSize(20, 40);

				ImageView iv = new ImageView(new Image(getClass().getResourceAsStream(anh)));
				iv.setFitHeight(95);
				iv.setFitWidth(85);
				dso.add(new Ctord(maTu, iv, ten, donGia, soLuong, b));
			}
			cn.close();
			prs.close();
			rs.close();
		} catch (SQLException e) {
			return null;
		}
		return dso;
	}

	// Lay danh sách phiêu nhập
	public ArrayList<PhieuNhap> layDanhSachPhieuNhap() {
		String query = "SELECT * FROM VIEW_PHIEUNHAP";
		ArrayList<PhieuNhap> dspn = new ArrayList();
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				String maPn = rs.getString("MAPN");
				String thoiGian = rs.getString("THOIGIAN");
				String hotenNv = rs.getString("HOTENNV");
				String tenNcc = rs.getString("TENNCC");
				String trangThai = rs.getString("TRANGTHAI");
				if(trangThai.equals("D")) {
					dspn.add(new PhieuNhap(maPn, hotenNv, thoiGian, tenNcc,"Đợi kiểm tra"));
				}
				else {
					dspn.add(new PhieuNhap(maPn, hotenNv, thoiGian, tenNcc,"Đã xác nhận"));
				}
			}
			cn.close();
			s.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return dspn;
	}

	public ArrayList<Ctpn> layDanhSach_CTPN(String mapn) {
		ArrayList<Ctpn> dsctpn = new ArrayList<>();
		String query = "SELECT TENNL,SOLUONG,DONVI,CT.MANL,DONGIA\r\n"
				+ "	FROM (SELECT MANL,TENNL,DONVI FROM NGUYENLIEU) NL,(SELECT MANL,DONGIA,MAPN,SOLUONG FROM CTPN WHERE MAPN = ?)CT\r\n"
				+ "	WHERE NL.MANL = CT.MANL";
		try {
			Connection cn = getConnectionToSQL();
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, mapn);
			ResultSet rs = prs.executeQuery();
			while (rs.next()) {
				String tenNl = rs.getString(1);
				float soLuong = rs.getFloat(2);
				String donVi = rs.getString(3);
				String maNl = rs.getString(4);
				long donGia = rs.getLong(5);
				dsctpn.add(new Ctpn(tenNl, String.valueOf(soLuong), donVi, maNl, String.valueOf(donGia)));
			}
			cn.close();
			rs.close();
			prs.close();
		} catch (SQLException e) {
			return null;
		}
		return dsctpn;
	}

	public ArrayList<String> layDanhSachNCC() {
		ArrayList<String> dsncc = new ArrayList<>();
		String query = "SELECT TENNCC FROM NHACUNGCAP";
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				dsncc.add(rs.getString(1));
			}
			cn.close();
			rs.close();
			s.close();
		} catch (SQLException e) {
			return null;
		}
		return dsncc;
	}

	public boolean themNhaCungCap(String nhaCC) {
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_THEM_NHACUNGCAP(?,?)}");
			cs1.setString(1, "");
			cs1.setString(2, nhaCC);
//			cs1.registerOutParameter(2, Types.NVARCHAR);
			cs1.execute();
			// mancc = cs1.getString(2);
			cs1.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public String layMaNCC(String tenncc) {
		String mancc = null;
		String query = "SELECT MANCC FROM NHACUNGCAP WHERE TENNCC = ?";
		try {
			Connection cn = getConnectionToSQL();
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, tenncc);
			ResultSet rs = prs.executeQuery();
			while (rs.next()) {
				mancc = rs.getString(1);
			}
			cn.close();
			rs.close();
			prs.close();
		} catch (SQLException e) {
			return null;
		}
		return mancc;
	}

	public String layMaGiamGia(double d) {
		String query = "SELECT MAGG FROM GIAMGIA WHERE PHANTRAM = ?";
		try {
			Connection cn = getConnectionToSQL();
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setDouble(1, d);
			ResultSet rs = prs.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}
			cn.close();
			rs.close();
			prs.close();
		} catch (SQLException e) {
		}
		return null;
	}

	public boolean kiemTraTenAnh(String sourceAnh) {
		String magg = null;
		String query = "SELECT MATU FROM THUCUONG WHERE ANH LIKE '%" + sourceAnh + "'";
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				magg = rs.getString(1);
			}
			s.close();
			cn.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Sai");
		}
		if (magg != null)
			return false;
		return true;
	}

	public String themPhieuNhap(String manv, String mancc) {
		String mapn;
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_THEM_PHIEUNHAP(?,?,?,?)}");
			cs1.setString(1, "");
			cs1.setString(2, manv);
			cs1.setString(3, mancc);
			cs1.setString(4,"D");
			cs1.registerOutParameter(1, Types.NVARCHAR);
			cs1.execute();
			mapn = cs1.getString(1);
			cs1.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return mapn;
	}
	
	public boolean suaPhieuNhap_NCC(String mapn,String mancc) {
		String query = "UPDATE PHIEUNHAP SET MANCC = ? WHERE MAPN = ?";
		try {
			Connection con = roleConnection("thungan");
			PreparedStatement prs = con.prepareStatement(query);
			prs.setString(1, mapn);
			prs.setString(2, mancc);
			prs.execute();
			prs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean suaPhieuNhap_TrangThai(String mapn, String trangThai) {
		String query = "UPDATE PHIEUNHAP SET TRANGTHAI = ? WHERE MAPN = ?";
		try {
			Connection con = roleConnection("thungan");
			PreparedStatement prs = con.prepareStatement(query);
			prs.setString(1, trangThai);
			prs.setString(2, mapn);
			prs.execute();
			prs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean xoaPhieuNhap(String mapn) {
		String query1 = "DELETE CTPN WHERE MAPN = ?";
		String query2 = "DELETE PHIEUNHAP WHERE MAPN = ?";
		try {
			Connection con = roleConnection("thungan");
			PreparedStatement prs = con.prepareStatement(query1);
			prs.setString(1, mapn);
			prs.execute();
			prs.close();
			prs = con.prepareStatement(query2);
			prs.setString(1, mapn);
			prs.execute();
			prs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean suaPhieuNhap(String manl, String mapn, float soluon, long dongia) {
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_SUA_CT_PHIEUNHAP(?,?,?,?)}");
			cs1.setString(1, manl);
			cs1.setString(2, mapn);
			cs1.setFloat(3, soluon);
			cs1.setLong(4, dongia);
			cs1.execute();
			cs1.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean themCTPN(String manl, String mapn, float soluon, long dongia) {
		String query = "INSERT INTO CTPN VALUES(?,?,?,?)";
		try {
			Connection cn = getConnectionToSQL();
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, mapn);
			prs.setString(2, manl);
			prs.setFloat(3, soluon);
			prs.setLong(4, dongia);

			prs.executeUpdate();
			prs.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<HangDoi> layDanhSachMon_HangDoi() {
		String query = "SELECT * FROM VIEW_HANGDOI\r\n" + "ORDER BY THOIGIANLAP";
		ArrayList<HangDoi> dso = new ArrayList();
		try {
			Connection cn = getConnectionToSQL();
			PreparedStatement prs = cn.prepareStatement(query);
			ResultSet rs = prs.executeQuery();
			while (rs.next()) {
				String maor = rs.getString("MAOR");
				String anh = rs.getString("ANH");
				String ten = rs.getString("TEN");
				String thoiGian = rs.getString("THOIGIAN");
				int soLuong = rs.getInt("SOLUONG");
				String madu = rs.getString("MATU");
				JFXButton btnPhaChe = new JFXButton();

				btnPhaChe.setText("Pha chế");
				btnPhaChe.setBorder(null);
				btnPhaChe.setStyle("-fx-border-color: #879095; -fx-background-color: #fff;");

//				Image img = new Image("/image/QL/subtract_52px.png");
				Image img = new Image("/image/B/icons8_chef_hat_48px_1.png");
				ImageView view = new ImageView(img);
				view.setFitHeight(25);
				view.setFitWidth(25);
				btnPhaChe.setGraphic(view);
				btnPhaChe.setPrefSize(100, 40);

				JFXButton btnTraMon = new JFXButton();
				btnTraMon.setDisable(true);
				btnTraMon.setText("Trả món");
				btnTraMon.setBorder(null);
				btnTraMon.setStyle("-fx-border-color: #879095; -fx-background-color: #fff; ");
//				Image img = new Image("/image/QL/subtract_52px.png");
				Image img1 = new Image("/image/B/icons8_Done_48px.png");
				ImageView view1 = new ImageView(img1);
				view1.setFitHeight(25);
				view1.setFitWidth(25);
				btnTraMon.setGraphic(view1);
				btnTraMon.setPrefSize(100, 40);

				ImageView iv = new ImageView(new Image(getClass().getResourceAsStream(anh)));
				iv.setFitHeight(95);
				iv.setFitWidth(85);
				HangDoi hd = new HangDoi(maor, iv, madu, ten, soLuong, thoiGian, btnPhaChe, btnTraMon);
				hd.setDangPhaChe(false);
				hd.setDatramon(false);

				dso.add(hd);
			}
			cn.close();
			prs.close();
			rs.close();
		} catch (SQLException e) {
			return null;
		}
		return dso;
	}

	public String kiemTraTrangThai(String maor) {
		String query = "SELECT TRANGTHAI FROM ORDERS WHERE MAOR ='" + maor + "'";
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				return rs.getString(1);
			}
			s.close();
			cn.close();
			rs.close();
		} catch (SQLException e) {
			return null;
		}
		return null;
	}
	
	public boolean kiemTraThanhToan(String maor) {
		String query = "SELECT MANVTT FROM ORDERS WHERE MAOR ='" + maor + "'";
		try {
			Connection cn = getConnectionToSQL();
			java.sql.Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				if(rs.getString(1)!=null)
					return true;
			}
			s.close();
			cn.close();
			rs.close();
		} catch (SQLException e) {
			return false;
		}
		return false;
	}
	
	public boolean thayDoiTrangThai(String maor, String trangThai) {
		String query = "UPDATE ORDERS SET TRANGTHAI = ? WHERE MAOR = ?";
		try {
			Connection cn = getConnectionToSQL();
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, trangThai);
			prs.setString(2, maor);
			prs.executeUpdate();
			prs.close();
			cn.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public ArrayList<ThongKe> thongKeSLMuaTheoNgay(String ngay) {
		ArrayList<ThongKe> dstk = new ArrayList<>();
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_THONGKE_SLMUA_THUCUONG_NGAY(?)}");
			cs1.setString(1, ngay);
			ResultSet rs = cs1.executeQuery();
			while (rs.next()) {
				dstk.add(new ThongKe(rs.getString(1), rs.getInt(2)));
			}
			rs.close();
			cs1.close();
			cn.close();
		} catch (SQLException e) {
			return null;
		}
		return dstk;
	}

	public ArrayList<ThongKe> thongKeSLMuaTheoThang(String ngay) {
		int thang = 0, nam = 0;
		String[] a = ngay.split("/");
		try {
			thang = Integer.parseInt(a[0]);
			nam = Integer.parseInt(a[1]);
		} catch (Exception e) {

		}

		ArrayList<ThongKe> dstk = new ArrayList<>();
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_THONGKE_SLMUA_THUCUONG_THANG(?,?)}");
			cs1.setInt(1, thang);
			cs1.setInt(2, nam);
			ResultSet rs = cs1.executeQuery();
			while (rs.next()) {
				dstk.add(new ThongKe(rs.getString(1), rs.getInt(2)));
			}
			rs.close();
			cs1.close();
			cn.close();
		} catch (SQLException e) {
			return null;
		}
		return dstk;
	}

	public ArrayList<ThongKe> thongKeSLMuaTheoNam(String ngay) {
		ArrayList<ThongKe> dstk = new ArrayList<>();
		int nam = 0;
		try {
			nam = Integer.parseInt(ngay);
		} catch (Exception e) {

		}
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_THONGKE_SLMUA_THUCUONG_NAM(?)}");
			cs1.setInt(1, nam);
			ResultSet rs = cs1.executeQuery();
			while (rs.next()) {
				dstk.add(new ThongKe(rs.getString(1), rs.getInt(2)));
			}
			rs.close();
			cs1.close();
			cn.close();
		} catch (SQLException e) {
			return null;
		}
		return dstk;
	}

	public boolean suaChiTietOrder(String maor, ArrayList<Ctord> orlist) {

		String query = "DELETE FROM CTORD WHERE MAOR = ?";

		try {
			Connection con = getConnectionToSQL();
			PreparedStatement prs = con.prepareStatement(query);
			prs.setString(1, maor);
			prs.executeUpdate();
			prs.close();

			for (Ctord d : orlist) {
				CallableStatement cs1 = con.prepareCall("{call dbo.SP_GHI_CTORD(?,?,?,?)}");
				cs1.setString(1, maor);
				cs1.setString(2, d.getMadu());
				cs1.setLong(3, d.getDonGia());
				cs1.setInt(4, d.getSoLuong());
				cs1.execute();
				cs1.close();

			}
			con.close();

		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public ArrayList<Long> thongKeDoanhThuNam(int nam) {
		ArrayList<Long> dstk = new ArrayList<>();
		try {
			Connection cn = getConnectionToSQL();
			CallableStatement cs1 = cn.prepareCall("{call dbo.SP_THONGKETUNGTHANGTRONGNAM(?)}");
			cs1.setInt(1, nam);
			ResultSet rs = cs1.executeQuery();
			while (rs.next()) {
				dstk.add(rs.getLong(2));
			}
			rs.close();
			cs1.close();
			cn.close();
		} catch (SQLException e) {
			return null;
		}
		return dstk;
	}
	
	public boolean doiMatKhau(String tenDangNhap, String matKhauMoi) {
		String query = "UPDATE TAIKHOAN SET PASSWORD = ? WHERE TENDANGNHAP = ?";
		try {
			Connection cn = getConnectionToSQL();
			PreparedStatement prs = cn.prepareStatement(query);
			prs.setString(1, matKhauMoi);
			prs.setString(2, tenDangNhap);
			prs.executeUpdate();
			prs.close();
			cn.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}
