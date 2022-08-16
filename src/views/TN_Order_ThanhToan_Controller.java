package views;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Connect.BoDauTiengViet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import model.DrinkForOrdersViews;

public class TN_Order_ThanhToan_Controller implements Initializable {
	@FXML
	private TableColumn<DrinkForOrdersViews, Long> cDonGia;

	@FXML
	private TableColumn<DrinkForOrdersViews, Integer> cSoLuong;

	@FXML
	private TableColumn<DrinkForOrdersViews, String> cTenDoUong;

	@FXML
	private Label lbtongTien;

	@FXML
	public Label lbOrder_HoaDon;

	@FXML
	private TableView<DrinkForOrdersViews> tbDoUong;

	@FXML
	private Label txtMaOrders;

	@FXML
	private Label txtThoiGian;

	@FXML
	public HBox hbSoTienKhachDua;

	@FXML
	public Label lbSoTienThua;
	
	@FXML
	public Label lbSoTienKhachDua;
	
	@FXML
	public JFXButton btnIn;

//	@FXML
//	public JFXButton btnThanhToan;
	@FXML
	public HBox hbSoTienThua;
	ObservableList<DrinkForOrdersViews>dsDaChon ;
//	String thoiGian;
//	String maor;

	public void setData(ArrayList<DrinkForOrdersViews> daChon, String thoiGian, String maor) {
//		this.thoiGian = thoiGian;
//		this.maor = maor;
		long tongTien = 0;
		BoDauTiengViet bd = new BoDauTiengViet();
		for (DrinkForOrdersViews d : daChon) {
			d.setTenDoUong(bd.boDau(d.getTenDoUong()));
			tongTien += d.getSoLuong() * d.getGiaBan();
		}

		ObservableList<DrinkForOrdersViews>dsDaChon = FXCollections.observableArrayList(daChon);
		txtMaOrders.setText(maor);
		txtThoiGian.setText(thoiGian);
		lbtongTien.setText(String.valueOf(tongTien)+"VND");
		cTenDoUong.setCellValueFactory(new PropertyValueFactory<>("tenDoUong"));
		cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		cDonGia.setCellValueFactory(new PropertyValueFactory<>("giaBan"));

		tbDoUong.setItems(dsDaChon);
	}

	// Tro ve
	@FXML
	private void inOrders(MouseEvent event) {
		if (lbOrder_HoaDon.getText().equals("Order")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("In orders thành công!");
			alert.showAndWait();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setAlwaysOnTop(false);
			stage.close();

		} else {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("In hóa đơn thành công!");
			alert.showAndWait();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setAlwaysOnTop(false);
			stage.close();

		}

	}

//	@FXML
//	public void thanhToan_XuatHoaDon(MouseEvent event) {
//		//Mo view thanh toan
////		set Du lieu
//
//		try {
//			FXMLLoader fxmlLoader = new FXMLLoader();
//			fxmlLoader.setLocation(getClass().getResource("/views/TN_ThanhToan.fxml"));
//
//			Parent parent = fxmlLoader.load();
//			TN_ThanhToan_Controller c = fxmlLoader.getController();
//			Scene scene = new Scene(parent);
//			Stage stage = new Stage();
//			stage.setScene(scene);
//			stage.initStyle(StageStyle.UNDECORATED);
//
//			c.setDuLieu(txtMaOrders.getText(), Main.hoTenNv, txtThoiGian.getText(),
//					Long.parseLong(lbtongTien.getText().replace("VND", "")));
//			stage.show();
//		
//		}
//		catch(Exception e ) {
//			e.printStackTrace();
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Thông báo");
//			alert.setHeaderText("Lỗi hệ thống!");
//			alert.showAndWait();
//		}
//		
//		Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		stage1.close();
//		
////		ConnectDB con = new ConnectDB();
////		if(con.thanhToan(txtMaOrders.getText())) {
////			
////			
////		}
////		else {
////			Alert alert = new Alert(AlertType.INFORMATION);
////			alert.setTitle("Thông báo");
////			alert.setHeaderText("Tiến thành thanh toán thất bại!");
////			alert.showAndWait();
////			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
////			stage.close();
////		}
//	}

	@FXML
	public void troVe(MouseEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có thật sự muốn thoát không?");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return;
		} else {
			Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage1.close();	
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
}
