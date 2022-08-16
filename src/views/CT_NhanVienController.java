package views;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;

import Connect.ChuanHoaDuLieu;
import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.NhanVien;

public class CT_NhanVienController implements Initializable {

	boolean hoTen = false;
	boolean cccd = false;
	boolean sdt = false;
	boolean cv = false;
	boolean ngaySinh = false;
	boolean luong = false;
	boolean ngayVaoLam = false;

	boolean loiNgaySinh = false;
	boolean loiNgayVaoLam = false;

	@FXML
	private JFXButton btnLayMK;

	@FXML
	private JFXButton btnKhoiPhucMK;

	@FXML
	private JFXButton btnLuu;

	@FXML
	private JFXButton btnTrove;

	@FXML
	private JFXCheckBox cbNghi;

	@FXML
	private ComboBox<String> cbbChucvu;

	@FXML
	private ToggleGroup gioiTinhNV;

	@FXML
	private JFXRadioButton rdNam;

	@FXML
	private JFXRadioButton rdNu;

	@FXML
	private TextField txtCCCD;

	@FXML
	private TextField txtDiachi;

	@FXML
	private TextField txtHoten;

	@FXML
	private TextField txtLuong;

	@FXML
	private TextField txtNgaysinh;

	@FXML
	private TextField txtNgayvaolam;

	@FXML
	private TextField txtSDT;

	@FXML
	private Label txtManv;

	@FXML
	private ImageView imgTBCCCD;

	@FXML
	private ImageView imgTBChucVu;

	@FXML
	private HBox tbChucVu;

	@FXML
	private ImageView imgTBDiaChi;

	@FXML
	private ImageView imgTBHoTen;

	@FXML
	private ImageView imgTBLuong;

	@FXML
	private ImageView imgTBNgaySinh;

	@FXML
	private ImageView imgTBNgayVaoLam;

	@FXML
	private ImageView imgTBSDT;
	///

	public Map<String, String> chucVu;

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

	// Kiểm tra họ và tên
	@FXML
	private void kiemTraHoTen(KeyEvent event) {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		if (ch.chuanHoaChuoi(txtHoten.getText()).isEmpty()) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBHoTen.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Họ và tên nhân viên không được để trống!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtHoten.setTooltip(tt);
			txtHoten.getStyleClass().remove("error");
			txtHoten.getStyleClass().add("error");
			hoTen = false;
		} else if (!ch.kiemTraHoTenNV(txtHoten.getText())) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBHoTen.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Vui lòng nhập đầy đủ họ và tên!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtHoten.setTooltip(tt);
			txtHoten.getStyleClass().remove("error");
			txtHoten.getStyleClass().add("error");
			hoTen = false;
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtHoten.getStyleClass().remove("error");
			imgTBHoTen.setImage(image);
			txtHoten.setTooltip(null);
			hoTen = true;
		}
	}

	// kiểm tra căn cước công dân
	@FXML
	private void kiemTraCCCD(KeyEvent e) {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		if (ch.chuanHoaChuoi(txtCCCD.getText()).isEmpty()) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBCCCD.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Căn cước công dân không được để trống!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtCCCD.setTooltip(tt);
			txtCCCD.getStyleClass().remove("error");
			txtCCCD.getStyleClass().add("error");

			cccd = false;
		} else if (txtCCCD.getText().matches("\\\\d{0,12}")) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBCCCD.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Căn cước công dân không hợp lệ!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtCCCD.setTooltip(tt);
			txtCCCD.getStyleClass().remove("error");
			txtCCCD.getStyleClass().add("error");

			cccd = false;
		} else if (txtCCCD.getText().length() != 12) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBCCCD.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Độ dài căng cức công dân không phù hợp!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtCCCD.setTooltip(tt);
			txtCCCD.getStyleClass().remove("error");
			txtCCCD.getStyleClass().add("error");

			cccd = false;
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtCCCD.getStyleClass().remove("error");
			imgTBCCCD.setImage(image);
			txtCCCD.setTooltip(null);
			cccd = true;
		}
	}

	// Kiểm tra thông tin nhâp vào số điện thoại
	@FXML
	private void kiemTraSDT(KeyEvent e) {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		if (ch.chuanHoaChuoi(txtSDT.getText()).isEmpty()) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBSDT.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Số điện thoại không được để trống!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtSDT.setTooltip(tt);
			txtSDT.getStyleClass().remove("error");
			txtSDT.getStyleClass().add("error");
			sdt = false;

		} else if (!ch.kiemTraSoNguyen(txtSDT.getText())) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBSDT.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Số điện thoại không hợp lệ!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtSDT.setTooltip(tt);
			txtSDT.getStyleClass().remove("error");
			txtSDT.getStyleClass().add("error");
			sdt = false;
		} else if (ch.chuanHoaChuoi(txtSDT.getText()).length() != 10) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBSDT.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Số điện thoại không hợp lệ!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtSDT.setTooltip(tt);
			txtSDT.getStyleClass().remove("error");
			txtSDT.getStyleClass().add("error");
			sdt = false;
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtSDT.getStyleClass().remove("error");
			imgTBSDT.setImage(image);
			txtSDT.setTooltip(null);
			sdt = true;
		}
	}

	// Kiểm tra thông tin ngày sinh
	@FXML
	private void kiemTraNgaySinh() {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
//	
		if (txtNgaysinh.getText().trim().length() == 0) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBNgaySinh.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Ngày sinh không được để trống!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtNgaysinh.setTooltip(tt);
			txtNgaysinh.getStyleClass().remove("error");
			txtNgaysinh.getStyleClass().add("error");
			ngaySinh = false;
		} else if (!ch.kiemTraDate(ch.chuanHoaChuoi(txtNgaysinh.getText().toString()))) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBNgaySinh.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Ngày sinh không đúng định dạng dd/MM/yyyy!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtNgaysinh.setTooltip(tt);
			txtNgaysinh.getStyleClass().remove("error");
			txtNgaysinh.getStyleClass().add("error");
			ngaySinh = false;
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtNgaysinh.getStyleClass().remove("error");
			imgTBNgaySinh.setImage(image);
			txtNgaysinh.setTooltip(null);
			ngaySinh = true;
		}
	}

	// Kiểm tra thông tin giới tính
	@FXML
	private void tatCSSNam(MouseEvent e) {
		tbChucVu.getStyleClass().remove("error");
		tbChucVu.setStyle("-fx-border-color: green;");
	}

	@FXML
	private void tatCSSNu(MouseEvent e) {
		tbChucVu.getStyleClass().remove("error");
	}

	// Kiem tra ngay vao lam

	@FXML
	private void kiemTraNgayVaoLam() {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		if (txtNgayvaolam.getText().trim().length() == 0) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBNgayVaoLam.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Ngày vào làm không được để trống!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtNgayvaolam.setTooltip(tt);
			txtNgayvaolam.getStyleClass().remove("error");
			txtNgayvaolam.getStyleClass().add("error");

			ngayVaoLam = false;
		} else if (!ch.kiemTraDate(ch.chuanHoaChuoi(txtNgayvaolam.getText().toString()))) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBNgayVaoLam.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Ngày vào làm không đúng định dạng dd/MM/yyyy!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtNgayvaolam.setTooltip(tt);
			txtNgayvaolam.getStyleClass().remove("error");
			txtNgayvaolam.getStyleClass().add("error");
			ngayVaoLam = false;
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtNgayvaolam.getStyleClass().remove("error");
			imgTBNgayVaoLam.setImage(image);
			txtNgayvaolam.setTooltip(null);
			ngayVaoLam = true;
		}
	}

	// Thong bao nhap sai luong
	@FXML
	private void formatTien(KeyEvent event) {
		if (txtLuong.getText().trim().length() == 0) {
			imgTBLuong.setImage(sai());
			txtLuong.setTooltip(toolTipValidate("Vui lòng nhập số tiền!"));
			txtLuong.getStyleClass().remove("error");
			txtLuong.getStyleClass().add("error");
			luong = false;
		} else if (!txtLuong.getText().trim().matches("\\d{0,15}")) {
			imgTBLuong.setImage(sai());
			txtLuong.setTooltip(toolTipValidate("Lương không hợp lệ!"));
			txtLuong.getStyleClass().remove("error");
			txtLuong.getStyleClass().add("error");
			luong = false;
		} else {
			imgTBLuong.setImage(dung());
			txtLuong.getStyleClass().remove("error");
			txtLuong.setTooltip(null);
			luong = true;
		}
	}

	// Kiem tra ngay sinh

	// TRO VE CT_NhanVien
	@FXML
	private void clickTroVe_CT_NhanVien(MouseEvent event) {
		Stage stage = (Stage) btnTrove.getScene().getWindow();
		stage.setAlwaysOnTop(false);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn thật sự thoát không?");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return;
		} else {
			stage.setAlwaysOnTop(true);
			stage.close();

		}

	}

	// Load dữ liệu vào trong Stage
	public void setDuLieu(NhanVien nv) {

		hoTen = true;
		cccd = true;
		sdt = true;
		cv = true;
		ngaySinh = true;
		luong = true;
		ngayVaoLam = true;

		txtManv.setText(nv.getMaNv());
		txtHoten.setText(nv.getHoTen());
		txtCCCD.setText(nv.getCccd());
		txtSDT.setText(nv.getSdt());

		if (nv.getGioiTinh().equals("Nam")) {
			rdNam.setSelected(true);
		} else {
			rdNu.setSelected(true);
		}
		txtDiachi.setText(nv.getDiaChi());
		txtLuong.setText(String.valueOf(nv.getLuong()));
		txtNgaysinh.setText(nv.getNgaySinh());
		txtNgayvaolam.setText(nv.getNgayVaoLam());
		if (nv.getTrangThaiNghi() == true) {
			cbNghi.setSelected(true);
		} else {
			cbNghi.setSelected(false);
		}
		cbbChucvu.setValue(chucVu.get(nv.getMaCV()));
//		System.out.println(nv.getNgaySinh());
	}

	public String layMaChucVu() {
		for (Map.Entry<String, String> entry : chucVu.entrySet()) {
			if (cbbChucvu.getSelectionModel().getSelectedItem().equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	//
	@FXML
	private void clickLuuNV_CT_NhanVien(MouseEvent event) {
		Stage stage = (Stage) btnLuu.getScene().getWindow();

		if (hoTen && luong && cv && sdt && cccd && ngaySinh && ngayVaoLam) {
			String gioiTinh;
			if (rdNam.isSelected()) {
				gioiTinh = "Nam";

			} else if (rdNu.isSelected()) {
				gioiTinh = "Nữ";

			} else {
				// Thông báo chưa điền giới tính
				stage.setAlwaysOnTop(false);
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Thêm nhân viên thất bại!");
				alert.showAndWait();
				stage.setAlwaysOnTop(true);
				tbChucVu.getStyleClass().remove("error");
				tbChucVu.getStyleClass().add("error");
				return;
			}

			////// Nhớ sửa truyền dữ liệu vào
			NhanVien nv = new NhanVien(txtManv.getText(), txtHoten.getText(), txtCCCD.getText(), txtSDT.getText(),
					txtNgaysinh.getText(), gioiTinh, txtDiachi.getText(), txtNgayvaolam.getText(),
					Long.parseLong(txtLuong.getText()), false, layMaChucVu(),
					cbbChucvu.getSelectionModel().getSelectedItem());

			if (txtManv.getText().equals("Mã nhân viên tự sinh")) {
				ConnectDB cn = new ConnectDB();
				if (cn.themNhanVien(nv)) {
					stage.setAlwaysOnTop(false);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Thêm nhân viên thành công!");
					alert.showAndWait();
					stage.close();
				} else {
					stage.setAlwaysOnTop(false);
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Cảnh báo");
					alert.setHeaderText("Thêm thông tin nhân viên thất bại!");
					alert.showAndWait();
					stage.setAlwaysOnTop(true);
				}
			} else {
				ConnectDB cn = new ConnectDB();
				if (cn.suaNhanVien(nv)) {
					stage.setAlwaysOnTop(false);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Sửa nhân viên thành công!");
					alert.showAndWait();
					stage.close();
				} else {
					stage.setAlwaysOnTop(false);
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Cảnh báo");
					alert.setHeaderText("Sửa thông tin nhân viên thất bại!");
					alert.showAndWait();
					stage.setAlwaysOnTop(true);
				}
			}
		} else {
			stage.setAlwaysOnTop(false);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Cảnh báo");
			alert.setHeaderText("Vui lòng kiểm tra lại dữ liệu!");
			alert.showAndWait();
			stage.setAlwaysOnTop(true);
		}

	}

	// CHon cbb chuc vu
	@FXML
	public void chonChucVu(ActionEvent event) {
		if (cbbChucvu.getSelectionModel().isSelected(-1)) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBChucVu.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Vui lòng chọn chức vụ");
			tt.setStyle("-fx-border-color:#F02849;" + "-fx-base: #000; " + "-fx-text-fill: #F02849;"
					+ "-fx-show-delay: 250ms;" + "-fx-background-color: white;" + "-fx-background-radius: 20;"
					+ "-fx-border-radius: 20");
			cbbChucvu.setTooltip(tt);
			cbbChucvu.getStyleClass().remove("error");
			cbbChucvu.getStyleClass().add("error");
		} else {
			cv = true;
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
//			cbbChucvu.getStyleClass().remove("error");
			imgTBChucVu.setImage(image);
//			cbbChucvu.setTooltip(null);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<String> DSCV = new ArrayList();
		ConnectDB cn = new ConnectDB();
		chucVu = cn.layChucVu();
		for (String i : chucVu.keySet()) {
			DSCV.add(chucVu.get(i));
		}
		ObservableList<String> dscv = FXCollections.observableArrayList(DSCV);
		cbbChucvu.setItems(dscv);
	}

	// Tạo tài khoản cho nhân viên
	@FXML
	public void taoTaiKhoan() {

	}

}
