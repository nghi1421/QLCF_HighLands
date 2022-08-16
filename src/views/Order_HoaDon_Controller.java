package views;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Connect.BoDauTiengViet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DrinkForOrdersViews;

public class Order_HoaDon_Controller implements Initializable {
	@FXML
	private JFXButton btnIn;

	@FXML
	private TableColumn<DrinkForOrdersViews, Long> cDonGia;

	@FXML
	private TableColumn<DrinkForOrdersViews, Integer> cSoLuong;

	@FXML
	private TableColumn<DrinkForOrdersViews, String> cTenDoUong;

	@FXML
	private Label lbtongTien;

	@FXML
	private TableView<DrinkForOrdersViews> tbDoUong;

	@FXML
	private Label txtMaOrders;

	@FXML
	private Label txtThoiGian;

//	ArrayList<DrinkForOrdersViews> daChon = new ArrayList<>();
//	String thoiGian;
//	String maor;

	public void setData(ArrayList<DrinkForOrdersViews> daChon, String thoiGian, String maor) {
//		this.thoiGian = thoiGian;
//		this.maor = maor;
//		this.daChon = daChon;
		long tongTien=0;
		BoDauTiengViet bd = new BoDauTiengViet();
		for(DrinkForOrdersViews d:daChon) {
			d.setTenDoUong(bd.boDau(d.getTenDoUong()));
			tongTien+=d.getSoLuong()*d.getGiaBan();
		}
		
		txtMaOrders.setText(maor);
		txtThoiGian.setText(thoiGian);
		lbtongTien.setText(String.valueOf(tongTien));
		cTenDoUong.setCellValueFactory(new PropertyValueFactory<>("tenDoUong"));
		cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		cDonGia.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
		
		ObservableList<DrinkForOrdersViews> dsDaChon = null;
		dsDaChon = FXCollections.observableArrayList(daChon);
		tbDoUong.setItems(dsDaChon);
	}
	
	//Tro ve
	@FXML
	private void inOrders(MouseEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("In orders thành công!");
		alert.showAndWait();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
