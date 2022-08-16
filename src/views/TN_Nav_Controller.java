package views;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

public class TN_Nav_Controller implements Initializable {

	@FXML
	private Label lbHoTenNV;

	@FXML
	private JFXButton btnGoiMon;

	@FXML
	private JFXButton btnKhachHang;

	@FXML
	private JFXButton btnOrder;

	@FXML
	private JFXButton btnDoiMatKhau;
	
	@FXML
	private BorderPane borderPane;

	@FXML
	void loadViewGoiMon(MouseEvent event) {
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Gọi món");
		loadUI("TN_GoiMon");
		btnGoiMon.getStyleClass().remove("chon");
		btnKhachHang.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnGoiMon.getStyleClass().add("chon");
	}

	@FXML
	void loadViewKhachHang(MouseEvent event) {
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Khách hàng");
		loadUI("TN_KhachHang");
		btnGoiMon.getStyleClass().remove("chon");
		btnKhachHang.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnKhachHang.getStyleClass().add("chon");
	}

	@FXML
	void dangXuat(MouseEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn thật sự muốn đăng xuất không?");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return;
		} else {
			Stage stage1 = (Stage) borderPane.getScene().getWindow();
			stage1.close();
			Stage stage = new Stage();
			BorderPane root;
			try {
				root = (BorderPane) FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setMaximized(false);
				Scene scene = new Scene(root);
				scene.setFill(Color.TRANSPARENT);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {

			}

		}
	}

	@FXML
	void thoat(MouseEvent event) {
		Stage stage = (Stage) borderPane.getScene().getWindow();
		stage.close();
	}

	@FXML
	void loadViewOrder(MouseEvent event) {
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Order");
		loadUI("TN_Order");

		btnGoiMon.getStyleClass().remove("chon");
		btnKhachHang.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnOrder.getStyleClass().add("chon");
	}

	@FXML
	public void doiMatKhau() {
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Đổi mật khẩu");
		loadUI("DoiMatKhau");
		btnGoiMon.getStyleClass().remove("chon");
		btnKhachHang.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbHoTenNV.setText(Main.hoTenNv);
	}

	private void loadUI(String UI) {
//		System.out.print(Main.hoTenNv);

		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(UI + ".fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		borderPane.setCenter(root);
	}
}
