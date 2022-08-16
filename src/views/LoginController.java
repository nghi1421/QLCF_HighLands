package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Connect.ChuanHoaDuLieu;
import Connect.EntryConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.Main;

public class LoginController implements Initializable {

	@FXML
	private Button btnThoat;

	@FXML
	private ImageView btnDangNhap;

	@FXML
	private PasswordField txtMatKhau;

	@FXML
	private TextField txtTenDangNhap;

//   public ValidationSupport validation = new ValidationSupport();
	public Tooltip toolTipValidate(String message) {
		Tooltip tt = new Tooltip();
		tt.setText(message);
		tt.getStyleClass().remove("tooltipError");
		tt.getStyleClass().add("tooltipError");
		return tt;
	}

	@FXML
	public void kiemTraTenDangNhap(KeyEvent e) {

		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		if (ch.chuanHoaChuoi(txtTenDangNhap.getText()).isEmpty()) {
			txtTenDangNhap.setTooltip(toolTipValidate("Tên đăng nhập không được để trống!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
		}
		else if (!ch.kiemTraKhoangTrang(txtTenDangNhap.getText())) {
			txtTenDangNhap.setTooltip(toolTipValidate("Tên đăng nhập không chứa khoảng trắng!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
		}
		else if (txtTenDangNhap.getText().length() < 6) {
			txtTenDangNhap.setTooltip(toolTipValidate("Tên đăng nhập phải có ít nhất 6 kí tự!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
		} else if (ch.chuanHoaChuoi(txtTenDangNhap.getText()).length() > 40) {
			txtTenDangNhap.setTooltip(toolTipValidate("Tên đăng nhập vượt quá 40 kí tự!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
		} else if (ch.kiemTraKiTuDacBiet(txtTenDangNhap.getText().trim())) {
			txtTenDangNhap.setTooltip(toolTipValidate("Tên đăng nhập không chưa kí tự đặc biệt!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
		} else if (!ch.kiemTraTenDangNhap(txtTenDangNhap.getText().trim())) {
			txtTenDangNhap.setTooltip(toolTipValidate("Tên đăng nhập phải bao gồm chữ và số!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
		}else {
			txtTenDangNhap.setTooltip(null);
			txtTenDangNhap.getStyleClass().remove("error");
		}
	}

	@FXML
	public void kiemTraMatKhau(KeyEvent e) {
		if (txtMatKhau.getText().isEmpty()) {
			txtMatKhau.setTooltip(toolTipValidate("Mật khẩu không được để trống!"));
			txtMatKhau.getStyleClass().remove("error");
			txtMatKhau.getStyleClass().add("error");
		} else {
			txtMatKhau.setTooltip(null);
			txtMatKhau.getStyleClass().remove("error");
		}
	}

	@FXML
	public void loadForm(ActionEvent event) {

		// Kiểm tra thông tin đăng nhập
		EntryConnection ec = new EntryConnection();
		if (ec.dangNhap(txtTenDangNhap.getText(), txtMatKhau.getText())) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Đăng nhập thành công");
			alert.showAndWait();
			
			if (Main.quyen.equals("QUANLI")) {
				// đúng go site and role
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.close();

				try {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/views/QL_Nav.fxml"));

					Parent parent = fxmlLoader.load();

					Scene scene = new Scene(parent);
					Stage stage1 = new Stage();
					stage1.setScene(scene);
					stage1.setMaximized(true);
					QL_Nav_Controller nvC = fxmlLoader.getController();
					nvC.loadViewNhanVien(null);
					stage1.getIcons().add(new Image("/image/download.png"));
					Main.tenDangNhap = txtTenDangNhap.getText();
					Main.matKhau = txtMatKhau.getText();
					stage1.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					
				}
			} else if (Main.quyen.equals("BEP")) {
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.close();

				try {
//					Parent parent = FXMLLoader.load(getClass().getResource("/views/B_Nav.fxml"));
//					Scene scene = new Scene(parent);
//					Stage stage1 = new Stage();
//					stage1.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Nhân viên bếp");
//					stage1.setScene(scene);
//					stage1.setMaximized(true);
//					stage1.show();
					
					
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/views/B_Nav.fxml"));

					Parent parent = fxmlLoader.load();

					Scene scene = new Scene(parent);
					Stage stage1 = new Stage();
					stage1.setScene(scene);
					stage1.setMaximized(true);
					stage1.getIcons().add(new Image("/image/download.png"));
					B_Nav_Controller nvC = fxmlLoader.getController();
					nvC.loadViewDanhSachHangDoi(null);
					Main.tenDangNhap = txtTenDangNhap.getText();
					Main.matKhau = txtMatKhau.getText();
					stage1.show();
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (Main.quyen.equals("THUNGAN")) {
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.close();

				try {
//					Parent parent = FXMLLoader.load(getClass().getResource("/views/TN_Nav.fxml"));
//					Scene scene = new Scene(parent);
//					Stage stage1 = new Stage();
//					stage1.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Nhân viên thu ngân");
//					stage1.setScene(scene);
//					stage1.setMaximized(true);
//					
//					stage1.show();
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/views/TN_Nav.fxml"));

					Parent parent = fxmlLoader.load();

					Scene scene = new Scene(parent);
					Stage stage1 = new Stage();
					stage1.setScene(scene);
					stage1.setMaximized(true);
					TN_Nav_Controller nvC = fxmlLoader.getController();
					nvC.loadViewGoiMon(null);
					stage1.getIcons().add(new Image("/image/download.png"));
					Main.tenDangNhap = txtTenDangNhap.getText();
					Main.matKhau = txtMatKhau.getText();
					stage1.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Đăng nhập thất bại! \nVui lòng kiểm tra tên đăng nhập và mật khẩu");
			alert.showAndWait();
		}

	}

	@FXML
	void close() {
		Stage stage = (Stage) btnThoat.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
