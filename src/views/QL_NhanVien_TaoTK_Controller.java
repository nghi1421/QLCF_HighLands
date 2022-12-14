package views;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import Connect.ChuanHoaDuLieu;
import Connect.ConnectDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class QL_NhanVien_TaoTK_Controller implements Initializable {
	@FXML
	private Button btnTaoTk;
	
	@FXML
	private Button btnTroVe;
	
	boolean tenDangNhap = false;
	boolean matKhau = false;

	@FXML
	private ImageView imgTBMatKhau;

	@FXML
	private ImageView imgTBTenDangNhap;

	@FXML
	private Label lbChucVu;

	@FXML
	private Label lbHoTen;

	@FXML
	private Label lbmaNv;

	@FXML
	private PasswordField txtMatKhau;

	@FXML
	private TextField txtTenDangNhap;

	public void setDuLieu(String manv, String hoten, String chucvu) {
		lbmaNv.setText(manv);
		lbHoTen.setText(hoten);
		lbChucVu.setText(chucvu);
	}

	public String layMaCV() {
		for (Map.Entry<String, String> entry : QL_NhanVien_Controller.dsChucVu.entrySet()) {
			if (lbChucVu.getText().equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

//  public ValidationSupport validation = new ValidationSupport();
	public Tooltip toolTipValidate(String message) {
		Tooltip tt = new Tooltip();
		tt.setText(message);
		tt.getStyleClass().remove("tooltipError");
		tt.getStyleClass().add("tooltipError");
		return tt;
	}
	
	public Image sai() {
		Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
		return image;
	}
	
	public Image dung() {
		Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
		return image;
	}

	//Tro ve
	@FXML
	public void troVe() {
		Stage stage = (Stage)btnTroVe.getScene().getWindow();
		stage.setAlwaysOnTop(false);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Th??ng b??o");
		alert.setHeaderText("B???n c?? ch???c ch???n tr??? v??? kh??ng?");
		Optional<ButtonType> result = alert.showAndWait();
		if(!result.isPresent() || result.get() != ButtonType.OK) {
			stage.setAlwaysOnTop(true);
		} else {
			stage.close();
		}
	}
	
	//Kiem tra ten dang nhap bi trung
	@FXML
	public void kiemTraTenDangNhap(KeyEvent e) {

		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		ConnectDB con = new ConnectDB();
		if (ch.chuanHoaChuoi(txtTenDangNhap.getText()).isEmpty()) {
			imgTBTenDangNhap.setImage(sai());
			txtTenDangNhap.setTooltip(toolTipValidate("T??n ????ng nh???p kh??ng ???????c ????? tr???ng!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
			tenDangNhap = false;
		}
		else if (!ch.kiemTraKhoangTrang(txtTenDangNhap.getText())) {
			imgTBTenDangNhap.setImage(sai());
			txtTenDangNhap.setTooltip(toolTipValidate("T??n ????ng nh???p kh??ng ch???a kho???ng tr???ng!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
			tenDangNhap = false;
		}
		else if (txtTenDangNhap.getText().length() < 6) {
			imgTBTenDangNhap.setImage(sai());
			txtTenDangNhap.setTooltip(toolTipValidate("T??n ????ng nh???p ph???i c?? ??t nh???t 6 k?? t???!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
			tenDangNhap = false;
		} else if (ch.chuanHoaChuoi(txtTenDangNhap.getText()).length() > 40) {
			imgTBTenDangNhap.setImage(sai());
			txtTenDangNhap.setTooltip(toolTipValidate("T??n ????ng nh???p v?????t qu?? 40 k?? t???!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
			tenDangNhap = false;
		} else if (ch.kiemTraKiTuDacBiet(txtTenDangNhap.getText().trim())) {
			imgTBTenDangNhap.setImage(sai());
			txtTenDangNhap.setTooltip(toolTipValidate("T??n ????ng nh???p kh??ng ch??a k?? t??? ?????c bi???t!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
			tenDangNhap = false;
		} else if (!ch.kiemTraTenDangNhap(txtTenDangNhap.getText().trim())) {
			imgTBTenDangNhap.setImage(sai());
			txtTenDangNhap.setTooltip(toolTipValidate("T??n ????ng nh???p ph???i bao g???m ch??? v?? s???!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
			tenDangNhap = false;
		}
		else if(con.kiemTraTenDangNhap(txtTenDangNhap.getText().trim())) {
			imgTBTenDangNhap.setImage(sai());
			txtTenDangNhap.setTooltip(toolTipValidate("T??n ????ng nh???p ???? b??? tr??ng!"));
			txtTenDangNhap.getStyleClass().remove("error");
			txtTenDangNhap.getStyleClass().add("error");
			tenDangNhap = false;
		}
		else {
			imgTBTenDangNhap.setImage(dung());
			txtTenDangNhap.setTooltip(null);
			txtTenDangNhap.getStyleClass().remove("error");
			tenDangNhap = true;
		}
	}

	@FXML
	public void kiemTraMatKhau(KeyEvent e) {
		if (txtMatKhau.getText().isEmpty()) {
			imgTBMatKhau.setImage(sai());
			txtMatKhau.setTooltip(toolTipValidate("M???t kh???u kh??ng ???????c ????? tr???ng!"));
			txtMatKhau.getStyleClass().remove("error");
			txtMatKhau.getStyleClass().add("error");
			matKhau = false;
		}
		else if(txtMatKhau.getText().length()<6) {
			imgTBMatKhau.setImage(sai());
			txtMatKhau.setTooltip(toolTipValidate("M???t kh???u t???i thi???u ph???i c?? 6 k?? t???!"));
			txtMatKhau.getStyleClass().remove("error");
			txtMatKhau.getStyleClass().add("error");
			matKhau = false;
		}
		else if(txtMatKhau.getText().length()>50) {
			imgTBMatKhau.setImage(sai());
			txtMatKhau.setTooltip(toolTipValidate("M???t kh???u t???i ??a 50 k?? t???!"));
			txtMatKhau.getStyleClass().remove("error");
			txtMatKhau.getStyleClass().add("error");
			matKhau = false;
		}
		else {
			imgTBMatKhau.setImage(dung());
			txtMatKhau.setTooltip(null);
			txtMatKhau.getStyleClass().remove("error");
			matKhau = true;
		}
	}
	
	@FXML
	public void themTaiKhoan() {

		ConnectDB cn = new ConnectDB();
		if(!tenDangNhap || !matKhau) {
			Stage stage = (Stage)btnTaoTk.getScene().getWindow();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Th??ng b??o");
			alert.setHeaderText("Th??m t??i kho???n th???t b???i!");
			alert.showAndWait();
			return;
		}
		if (cn.taoTaiKhoan(txtTenDangNhap.getText().trim(), txtMatKhau.getText(), lbmaNv.getText(), layMaCV())) {
			Stage stage = (Stage)btnTaoTk.getScene().getWindow();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Th??ng b??o");
			alert.setHeaderText("Th??m t??i kho???n th??nh c??ng!");
			alert.showAndWait();
			stage.close();
		
		} else {
			Stage stage = (Stage)btnTaoTk.getScene().getWindow();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Th??ng b??o");
			alert.setHeaderText("Th??m t??i kho???n t???t b???i!");
			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
