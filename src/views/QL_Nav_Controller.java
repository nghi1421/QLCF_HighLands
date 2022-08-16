package views;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.Main;

public class QL_Nav_Controller implements Initializable {

	@FXML
	private JFXButton btnDoiMatKhau;
	
	@FXML
	private JFXButton btnDoUong;

	@FXML
	private JFXButton btnNhanVien;

	@FXML
	private JFXButton btnOrder;

	
	@FXML
	private JFXButton btnNguyenLieu;
	
	@FXML
	private JFXButton btnPhieuNhap;

	@FXML
	private JFXButton btnThongKe;

	@FXML
	private Label lbHoTenNV;

	@FXML
	private StackPane stackPane;
	
	@FXML
	private BorderPane borderPane;

	@FXML
	void loadViewThongKe(MouseEvent event) {
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Thống kê");
		loadUI("QL_ThongKe");
		btnDoUong.getStyleClass().remove("chon");
		btnNhanVien.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnPhieuNhap.getStyleClass().remove("chon");
		btnNguyenLieu.getStyleClass().remove("chon");
		btnThongKe.getStyleClass().remove("chon");
		btnThongKe.getStyleClass().add("chon");
		btnDoiMatKhau.getStyleClass().remove("chon");
	}

	@FXML
	void loadViewNhanVien(MouseEvent event) {
		
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Nhân viên");
		loadUI("QL_NhanVien");
		
		btnDoUong.getStyleClass().remove("chon");
		btnNhanVien.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnPhieuNhap.getStyleClass().remove("chon");
		btnNguyenLieu.getStyleClass().remove("chon");
		btnThongKe.getStyleClass().remove("chon");
		btnNhanVien.getStyleClass().add("chon");
		btnDoiMatKhau.getStyleClass().remove("chon");
	}

	@FXML
	void thoat(MouseEvent event) {
		Stage stage = (Stage) borderPane.getScene().getWindow();
		stage.close();
	}

	@FXML
	void loadViewDoUong(MouseEvent event) {
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Đồ uống");
		loadUI("QL_DoUong");
		btnDoUong.getStyleClass().remove("chon");
		btnNhanVien.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnPhieuNhap.getStyleClass().remove("chon");
		btnNguyenLieu.getStyleClass().remove("chon");
		btnThongKe.getStyleClass().remove("chon");
		btnDoUong.getStyleClass().add("chon");
		btnDoiMatKhau.getStyleClass().remove("chon");
	}

	@FXML
	void loadViewNguyenLieu(MouseEvent event) {
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Nguyên liệu");
		loadUI("QL_NguyenLieu");
		btnDoUong.getStyleClass().remove("chon");
		btnNhanVien.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnPhieuNhap.getStyleClass().remove("chon");
		btnNguyenLieu.getStyleClass().remove("chon");
		btnThongKe.getStyleClass().remove("chon");
		btnNguyenLieu.getStyleClass().add("chon");
		btnDoiMatKhau.getStyleClass().remove("chon");
	}

	@FXML
	void loadViewPhieuNhap(MouseEvent event) {
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Phiếu nhập");
		loadUI("B_NhapHang");
		btnDoUong.getStyleClass().remove("chon");
		btnNhanVien.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnPhieuNhap.getStyleClass().remove("chon");
		btnNguyenLieu.getStyleClass().remove("chon");
		btnThongKe.getStyleClass().remove("chon");
		btnPhieuNhap.getStyleClass().add("chon");
		btnDoiMatKhau.getStyleClass().remove("chon");
	}
	
	@FXML
	void loadViewOrder(MouseEvent event) {
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Order");
		loadUI("TN_Order");
		btnDoUong.getStyleClass().remove("chon");
		btnNhanVien.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnPhieuNhap.getStyleClass().remove("chon");
		btnNguyenLieu.getStyleClass().remove("chon");
		btnThongKe.getStyleClass().remove("chon");
		btnOrder.getStyleClass().add("chon");
		btnDoiMatKhau.getStyleClass().remove("chon");
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
	public void doiMatKhau() {
		Stage stage = (Stage)(borderPane.getScene()).getWindow();
		stage.setTitle("QUẢN LÍ QUÁN CÀ PHÊ HIGHLANDS - Đổi mật khẩu");
		loadUI("DoiMatKhau");
		btnDoUong.getStyleClass().remove("chon");
		btnNhanVien.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnPhieuNhap.getStyleClass().remove("chon");
		btnNguyenLieu.getStyleClass().remove("chon");
		btnThongKe.getStyleClass().remove("chon");
		btnOrder.getStyleClass().remove("chon");
		btnDoiMatKhau.getStyleClass().add("chon");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbHoTenNV.setText(Main.hoTenNv);
	}

	private void loadUI(String UI) {

		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(UI +".fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(stackPane.getChildren().size()==0) {
			stackPane.getChildren().add(root);
			borderPane.setCenter(stackPane);
//			System.out.println("n1231323");
			return;
		}
//		System.out.println("n");
//        System.out.println(stackPane.getChildren().get(0));
        
//        root.translateXProperty().set(-20);

		/*
		 * for(int i =0 ;i<stackPane.getChildren().size();i++) {
		 * System.out.println(stackPane.getChildren().get(i).toString()); }
		 */
        stackPane.getChildren().add(root);
        
//        Timeline timeline = new Timeline();
//        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.LINEAR);
//        KeyFrame kf = new KeyFrame(Duration.seconds(0.4), kv);
//        timeline.getKeyFrames().add(kf);
//        timeline.setOnFinished(t -> {
        	stackPane.getChildren().remove(0);
//        });
//              
//        timeline.play();
        borderPane.setCenter(stackPane);
        
//		Parent root = null;
//		
//		try {
//			root = FXMLLoader.load(getClass().getResource(UI + ".fxml"));
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		borderPane.setCenter(root);
//		
		
	}
}
