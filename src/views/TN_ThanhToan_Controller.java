package views;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Connect.ConnectDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

public class TN_ThanhToan_Controller implements Initializable {
	@FXML
	private JFXButton btnThanhToan;

	@FXML
	private JFXButton btnTroVe;

	@FXML
	private ImageView imgTBSoTienKhachDua;

	@FXML
	private Label lbHoTenNV;

	@FXML
	private Label lbMaO;

	@FXML
	private Label lbTgLap;

	@FXML
	private Label lbTongTien;

	@FXML
	private Label lbTienThua;

	boolean tienThua = false;
	boolean tienDua = false;

	long tongTien = 0;
	String maor;
	@FXML
	private TextField txtSoTienKhachDua;

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

	@FXML
	void kiemTraSoTienNhapVao(KeyEvent event) {
		if (txtSoTienKhachDua.getText().trim().length() == 0) {
			imgTBSoTienKhachDua.setImage(sai());
			txtSoTienKhachDua.setTooltip(toolTipValidate("Vui lòng nhập số tiền!"));
			txtSoTienKhachDua.getStyleClass().remove("error");
			txtSoTienKhachDua.getStyleClass().add("error");
			lbTienThua.setText("0VND");
			tienThua = false;
			tienDua = false;
		} else if (!txtSoTienKhachDua.getText().trim().matches("\\d{0,15}")) {
			imgTBSoTienKhachDua.setImage(sai());
			txtSoTienKhachDua.setTooltip(toolTipValidate("Số tiền không hợp lệ!"));
			txtSoTienKhachDua.getStyleClass().remove("error");
			txtSoTienKhachDua.getStyleClass().add("error");
			lbTienThua.setText("0VND");
			tienThua = false;
			tienDua = false;
		} else {
			imgTBSoTienKhachDua.setImage(dung());
			txtSoTienKhachDua.getStyleClass().remove("error");
			txtSoTienKhachDua.setTooltip(null);
			long t = Long.parseLong(txtSoTienKhachDua.getText()) - tongTien;

			if (t < 0)
				tienThua = false;
			else
				tienThua = true;

			lbTienThua.setText(String.valueOf(t) + "VND");
			tienDua = true;
		}
	}

	@FXML
	void thanhToan_XuatHoaDon(MouseEvent event) {
		if (lbTienThua.getText().charAt(0) == '-') {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Số tiền không đủ để thanh toán!");
			alert.setContentText("Vui lòng kiểm tra số tiền");
			alert.showAndWait();
		} else {

			if (tienThua && tienDua) {
				ConnectDB con = new ConnectDB();
				if (con.thanhToan(lbMaO.getText())) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Thanh toán thành công!");
					alert.showAndWait();
					Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
					stage1.close();

					try {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/views/TN_Order_ThanhToan.fxml"));

						Parent parent = fxmlLoader.load();
						TN_Order_ThanhToan_Controller c = fxmlLoader.getController();
						Scene scene = new Scene(parent);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.initStyle(StageStyle.UNDECORATED);
						c.setData(con.layDanhOrder(maor), con.layThoiGianThanhToan(maor), maor);
//						c.btnThanhToan.setVisible(false);
						c.lbSoTienKhachDua.setText(txtSoTienKhachDua.getText());
						c.lbSoTienThua.setText(lbTienThua.getText());
						c.lbOrder_HoaDon.setText("Hoa don");
						c.btnIn.setText("In hóa đơn");
						stage.show();
					} catch (Exception e) {
						e.printStackTrace();
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setTitle("Thông báo");
						alert1.setHeaderText("Lỗi hệ thống!");
						alert1.showAndWait();
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Lỗi hệ thống!");
					alert.showAndWait();
				}
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Số tiền khách đưa không hợp lệ!");
				alert.showAndWait();
			}

		}
	}

	@FXML
	void troVe(MouseEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có thật sự muốn thoát không?");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return;
		} else {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		}
	}

	public void setDuLieu(String maor, String hoTenNv, String thoiGianLap, long tongTien) {
		lbMaO.setText(maor);
		lbHoTenNV.setText(hoTenNv);
		lbTgLap.setText(thoiGianLap);
		this.tongTien = tongTien;
		this.maor = maor;
		lbTongTien.setText(String.valueOf(tongTien) + "VND");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
