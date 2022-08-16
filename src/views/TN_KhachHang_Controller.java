package views;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Connect.BoDauTiengViet;
import Connect.ChuanHoaDuLieu;
import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.KhachHang;

public class TN_KhachHang_Controller implements Initializable {
	@FXML
	private TextField txtHoTenKH;

	@FXML
	private TextField txtSdtKH;

	@FXML
	private Label txtMaKH;

	@FXML
	private TextField txtTimKiempage2;

	@FXML
	private ImageView imgTBHoTenKH;

	@FXML
	private ImageView imgTBsdtKH;
	
	@FXML
	private VBox vbDSKH;
	
	@FXML
	private VBox vbChiTiet;
	
	@FXML
	private JFXButton btnThem;
	
	@FXML
	private JFXButton btnSua;
	
	@FXML
	private JFXButton btnThemKH;

	@FXML
	private TableView<KhachHang> tbKhachHang;

	@FXML
	private TableColumn<String, KhachHang> cMaKH;

	@FXML
	private TableColumn<String, KhachHang> cHoTenKH;

	@FXML
	private TableColumn<String, KhachHang> cSDT;

	ObservableList<KhachHang> dskh;

	// Tải lại danh sách khách hàng
	@FXML
	private void reloadKH() {
		ConnectDB con = new ConnectDB();
		dskh = FXCollections.observableArrayList(con.layDSKH());
		cMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
		cHoTenKH.setCellValueFactory(new PropertyValueFactory<>("hoTenKH"));
		cSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
		tbKhachHang.setItems(dskh);
	}

	// Thêm khách hàng
	@FXML
	private void clickThemKhachHang(MouseEvent e) {
		if (txtSdtKH.getStyleClass().indexOf("error") == -1 && txtSdtKH.getStyleClass().indexOf("error") == -1) {
			ConnectDB cn = new ConnectDB();
			if (cn.themKhachHang(txtHoTenKH.getText(), txtSdtKH.getText())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Thêm khách hàng thành công!");
				alert.showAndWait();
				txtHoTenKH.setText("");
				txtSdtKH.setText("");
				reloadKH();
				vbDSKH.setDisable(false);
				vbChiTiet.setDisable(true);
				txtMaKH.setText("");
				txtHoTenKH.setText("");
				txtSdtKH.setText("");
				imgTBHoTenKH.setImage(null);
				imgTBsdtKH.setImage(null);
				tbKhachHang.getSelectionModel().select(-1);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Thêm khách hàng thất bại!");
				alert.showAndWait();
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Thêm khách hàng thất bại!");
			alert.showAndWait();
		}
	}

	// Sửa thông tin khách hàng
	@FXML
	private void clickSuaKhachHang(MouseEvent e) {
		if (txtSdtKH.getStyleClass().indexOf("error") == -1 && txtSdtKH.getStyleClass().indexOf("error") == -1) {
			ConnectDB cn = new ConnectDB();
			if (cn.suaKhachHang(txtMaKH.getText(), txtHoTenKH.getText(), txtSdtKH.getText())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Sửa thông tin khách hàng thành công!");
				alert.showAndWait();
				
				txtHoTenKH.setText("");
				txtSdtKH.setText("");
				reloadKH();
				vbDSKH.setDisable(false);
				vbChiTiet.setDisable(true);
				txtMaKH.setText("");
				txtHoTenKH.setText("");
				txtSdtKH.setText("");
				imgTBHoTenKH.setImage(null);
				imgTBsdtKH.setImage(null);
				tbKhachHang.getSelectionModel().select(null);
				
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Sửa thông tin khách hàng thất bại!");
				alert.showAndWait();
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Sửa thông tin khách hàng thất bại!");
			alert.showAndWait();
		}
	}

	@FXML
	private void loadSelectedKhachHang() {
		
		if (tbKhachHang.getSelectionModel().getSelectedItem() == null) {	
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Thông báo");
//			alert.setHeaderText("Bạn chưa chọn khách hàng!");
//			alert.showAndWait();
		} else {
			KhachHang nv = tbKhachHang.getSelectionModel().getSelectedItem();
			txtMaKH.setText(nv.getMaKH());
			txtHoTenKH.setText(nv.getHoTenKH());
			txtSdtKH.setText(nv.getSdt());
			
			vbDSKH.setDisable(true);
			vbChiTiet.setDisable(false);
			btnThemKH.setDisable(true);
			btnSua.setDisable(false);
			
		}
	}

	@FXML
	private void themKhachHang() {

		vbDSKH.setDisable(true);
		vbChiTiet.setDisable(false);
		txtMaKH.setText("Mã khách hàng tự sinh");
		txtHoTenKH.setText("");
		txtSdtKH.setText("");
		imgTBHoTenKH.setImage(null);
		imgTBsdtKH.setImage(null);
		btnSua.setDisable(true);
		btnThemKH.setDisable(false);
	}
	
	@FXML
	private void boTimKiem() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có thật sự muốn thoát không?");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return;
		} else {
			vbDSKH.setDisable(false);
			vbChiTiet.setDisable(true);
			txtMaKH.setText("");
			txtHoTenKH.setText("");
			txtSdtKH.setText("");
			imgTBHoTenKH.setImage(null);
			imgTBsdtKH.setImage(null);
			tbKhachHang.getSelectionModel().select(null);
		}
	}
	
	//

	@FXML
	private void kiemTraTenKhachHang() {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		if (ch.chuanHoaChuoi(txtHoTenKH.getText()).isEmpty()) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBHoTenKH.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Họ và tên khách hàng không được để trống!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtHoTenKH.setTooltip(tt);
			txtHoTenKH.getStyleClass().remove("error");
			txtHoTenKH.getStyleClass().add("error");
		} else if (!ch.kiemTraHoTenNV(txtHoTenKH.getText())) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBHoTenKH.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Vui lòng nhập đầy đủ họ và tên!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtHoTenKH.setTooltip(tt);
			txtHoTenKH.getStyleClass().remove("error");
			txtHoTenKH.getStyleClass().add("error");
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtHoTenKH.getStyleClass().remove("error");
			imgTBHoTenKH.setImage(image);
			txtHoTenKH.setTooltip(null);
		}
	}

	// Kiểm tra số điện thoại nhập vào

	@FXML
	private void kiemTraSoDienThoai() {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		if (!ch.kiemTraSoNguyen(txtSdtKH.getText())) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBsdtKH.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Số điện thoại không hợp lệ!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtSdtKH.setTooltip(tt);
			txtSdtKH.getStyleClass().remove("error");
			txtSdtKH.getStyleClass().add("error");
		} else if (ch.chuanHoaChuoi(txtSdtKH.getText()).length() > 12) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBsdtKH.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Số điện thoại vượt quá độ dài quy định!");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtSdtKH.setTooltip(tt);
			txtSdtKH.getStyleClass().remove("error");
			txtSdtKH.getStyleClass().add("error");
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtSdtKH.getStyleClass().remove("error");
			imgTBsdtKH.setImage(image);
			txtSdtKH.setTooltip(null);
		}
	}

	// Tìm thông tin khách hàng page khách hàng



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		vbChiTiet.setDisable(true);
		reloadKH();
		
		BoDauTiengViet bd = new BoDauTiengViet();
		FilteredList<KhachHang> filterKhachHang = new FilteredList<>(dskh, b -> true);
		txtTimKiempage2.textProperty().addListener((observable, oldValue, newValue) -> {
			filterKhachHang.setPredicate(kh -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (kh.getHoTenKH().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
						bd.boDau(kh.getHoTenKH()).toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true;
				} else if (kh.getMaKH().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true;
				} else if (kh.getSdt().indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false;
			});
		});
		SortedList<KhachHang> sortedData = new SortedList<>(filterKhachHang);
		sortedData.comparatorProperty().bind(tbKhachHang.comparatorProperty());
		tbKhachHang.setItems(sortedData);
	}

}
