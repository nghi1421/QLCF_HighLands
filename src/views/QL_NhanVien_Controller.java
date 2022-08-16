package views;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import Connect.BoDauTiengViet;
import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import model.NhanVien;

public class QL_NhanVien_Controller implements Initializable {

	public static Map<String, String> dsChucVu;


	@FXML
	private TextField btnTimKiem;

	@FXML
	private Button btnTaoTaiKhoan;

	@FXML
	private TableColumn<NhanVien, Long> cLuong;

	@FXML
	private TableColumn<NhanVien, String> cSdt;

	@FXML
	private TableColumn<NhanVien, String> cGioiTinh;

	@FXML
	private TableColumn<NhanVien, String> cHoten;

	@FXML
	private TableColumn<NhanVien, String> cManv;

	@FXML
	private TableColumn<NhanVien, Date> cNgaysinh;

	@FXML
	private TableColumn<NhanVien, String> cCCCD;

	@FXML
	private TableColumn<NhanVien, String> cChucVu;

	@FXML
	private TableColumn<NhanVien, String> cDiachi = new TableColumn<>("diaChi");

	@FXML
	private TableView<NhanVien> tbNhanvien;
	ObservableList<NhanVien> dsnv;

	public static Map<String, String> chucVu;

	@FXML
	private void reload(MouseEvent e) {
		BoDauTiengViet bd = new BoDauTiengViet();
		ConnectDB cn = new ConnectDB();
		dsnv = FXCollections.observableArrayList(cn.layDSNV());

		cManv.setCellValueFactory(new PropertyValueFactory<>("maNv"));

		cCCCD.setCellValueFactory(new PropertyValueFactory<>("cccd"));

		cHoten.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
		cSdt.setCellValueFactory(new PropertyValueFactory<>("sdt"));

		cChucVu.setCellValueFactory(new PropertyValueFactory<>("chucVu"));

		cGioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
		cLuong.setCellValueFactory(new PropertyValueFactory<>("luong"));
		cNgaysinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
		cDiachi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		tbNhanvien.setItems(dsnv);

		FilteredList<NhanVien> filterNhanVien = new FilteredList<>(dsnv, b -> true);
		btnTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filterNhanVien.setPredicate(nv -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (nv.getHoTen().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(nv.getHoTen()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (nv.getMaNv().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (nv.getChucVu().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(nv.getChucVu()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (nv.getCccd().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (nv.getNgaySinh().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (nv.getGioiTinh().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(nv.getGioiTinh()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(nv.getLuong()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (nv.getDiaChi().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(nv.getDiaChi()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (nv.getSdt().indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false;
			});
		});
		SortedList<NhanVien> sortedData = new SortedList<>(filterNhanVien);
		sortedData.comparatorProperty().bind(tbNhanvien.comparatorProperty());
		tbNhanvien.setItems(sortedData);
	}

	// BUTTON ADD NHAN VIEN
	@FXML
	private void clickTHEM(MouseEvent event) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/views/CT_NhanVien.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setAlwaysOnTop(true);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Button Sửa thông tin nhân viên
	@FXML
	private void clickSuaNhanVien(MouseEvent event) {
		if (tbNhanvien.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Bạn chưa chọn nhân viên!");
			alert.showAndWait();
		} else {
			NhanVien nv = tbNhanvien.getSelectionModel().getSelectedItem();
			try {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/views/CT_NhanVien.fxml"));

				Parent parent = fxmlLoader.load();
				fxmlLoader.getController();

				Scene scene = new Scene(parent);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.UNDECORATED);

				CT_NhanVienController nvC = fxmlLoader.getController();

				nvC.setDuLieu(nv);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// Tạo tài khoản
	@FXML
	public void taoTaiKhoan(MouseEvent event) {
		ConnectDB con = new ConnectDB();
		if (tbNhanvien.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Bạn chưa chọn nhân viên!");
			alert.showAndWait();
		} else {
			NhanVien nv = tbNhanvien.getSelectionModel().getSelectedItem();

			if (!con.coSuDungPM(nv.getMaCV())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Nhân viên không thể sử dụng phần mềm!");
				alert.showAndWait();
			} else {
				if (con.daCoTaiKhoan(nv.getMaNv())) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Nhân viên đã có tài khoản!");
					alert.showAndWait();
				} else {
					try {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/views/QL_NhanVien_TaoTK.fxml"));

						Parent parent = fxmlLoader.load();
						fxmlLoader.getController();

						Scene scene = new Scene(parent);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.initStyle(StageStyle.UNDECORATED);
						QL_NhanVien_TaoTK_Controller nvC = fxmlLoader.getController();
						nvC.setDuLieu(nv.getMaNv(), nv.getHoTen(), nv.getChucVu());
//						stage.setAlwaysOnTop(true);
						stage.show();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}

	// Lay lai mat khau
	@FXML
	public void layLaiMatKhau() {
		ConnectDB con = new ConnectDB();
		if (tbNhanvien.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Bạn chưa chọn nhân viên!");
			alert.showAndWait();
		} else {
			NhanVien nv = tbNhanvien.getSelectionModel().getSelectedItem();
			if (!con.coSuDungPM(nv.getMaCV())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Nhân viên không thể sử dụng phần mềm!");
				alert.showAndWait();
			} else {
				if(Main.maNv.equals(nv.getMaNv())) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Nhân viên đang đăng nhập vào hệ thống!");
					alert.showAndWait();
					return;
				}
				if (con.daCoTaiKhoan(nv.getMaNv())) {
					/*
					 * TextInputDialog txtMK = new TextInputDialog();
					 * txtMK.setTitle("Tên đăng nhập: ");
					 * txtMK.getDialogPane().setContentText("Nhập mật khẩu mới: "); Optional<String>
					 * result = txtMK.showAndWait(); TextField input = txtMK.getEditor();
					 * input.setPromptText("Nhập mật khẩu mới");
					 * 
					 * if (input.getText().toString().length() < 6) { Alert alert = new
					 * Alert(AlertType.ERROR); alert.setTitle("Thông báo");
					 * alert.setHeaderText("Mật khẩu có ít nhất 6 kí tự!"); alert.showAndWait(); }
					 * else { Alert alert = new Alert(AlertType.INFORMATION);
					 * alert.setTitle("Thông báo");
					 * alert.setHeaderText("Thay đổi mật khẩu thành công!"); alert.showAndWait(); }
					 */
					if(con.macDinhTaiKhoan(nv.getMaNv())) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Thiết lập mật khẩu mặc định thành công!");
						alert.showAndWait();
					}
					else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Thiết lập mật khẩu mặc định thất bại!");
						alert.showAndWait();
					}
					
					
				} else {	
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Nhân viên chưa có tài khoản!");
					alert.showAndWait();
					
					
				}

			}
		}

	}

	//Xoa nhan vien
	@FXML
	public void xoaNhanVien() {
		ConnectDB con = new ConnectDB();
		if (tbNhanvien.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Bạn chưa chọn nhân viên!");
			alert.showAndWait();
		}
		else {		
			NhanVien nv =tbNhanvien.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Bạn có chắc chắn xóa nhân viên này không?");
			Optional<ButtonType> result = alert.showAndWait();
			if(!result.isPresent() || result.get() != ButtonType.OK) {
				//Thong bao loi
				return;
				
			} else {
				//Xu li xoa
				if(con.kiemTraKhoaNgoai_NhanVien(nv.getMaNv())==1) {
					Alert alert1 = new Alert(AlertType.WARNING);
					alert1.setTitle("Thông báo");
					alert1.setHeaderText("Xóa nhân viên thất bại!");
					alert1.setContentText("Nhân có thể đã lập phiếu trên phần mềm không thể xóa thông tin nhân viên!");
					alert1.showAndWait();
				}
				else {
					if(con.xoaNhanVien(nv.getMaNv())==1) {
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setTitle("Thông báo");
						alert1.setHeaderText("Xóa thông tin nhân viên thành công!");
						alert1.showAndWait();
						reload(null);
					}
					else {
						Alert alert1 = new Alert(AlertType.ERROR);
						alert1.setTitle("Thông báo");
						alert1.setHeaderText("Xóa nhân viên thất bại!");
						alert1.showAndWait();
					}
				}
			}
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ConnectDB con = new ConnectDB();
		dsChucVu = con.layChucVu();
		reload(null);

	}
}
