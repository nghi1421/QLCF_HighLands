package Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.Main;

public class EntryConnection {
	
	public Connection entryConnectSQL() {
		Connection con = null;
		try {
			String url = "jdbc:sqlserver://DESKTOP-5OHR22L:1433;databaseName=QLCAFE";
			String user = "entry";
			String pass = "123123";
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return con;
	}
	
	public boolean dangNhap(String tenDangNhap, String matKhau) {
		
		String query = "SELECT TENDANGNHAP, PASSWORD,MANV,TENQUYEN FROM TAIKHOAN TK, QUYEN Q WHERE TK.MAQUYEN = Q.MAQUYEN";
		try{
			Connection con = entryConnectSQL();
			java.sql.Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				if(tenDangNhap.equals(rs.getString(1)) && matKhau.equals(rs.getString(2))) {
					Main.maNv = rs.getString(3);
					Main.quyen =rs.getString(4);
					layThongTinDangNhap();
					return true;
				}
			}
			rs.close();
			con.close();
		}
		catch(SQLException e) {
			
		}
		return false;
		
	}
	
	public void layThongTinDangNhap(){
		
		String query = "SELECT HOTEN = HO + ' ' +TEN FROM NHANVIEN WHERE MANV = '"+Main.maNv+"'";
		try{
			Connection con = entryConnectSQL();
			java.sql.Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				Main.hoTenNv = rs.getString(1);
			}
			rs.close();
			con.close();
		}
		catch(SQLException e) {
			
		}
		
	}
}
