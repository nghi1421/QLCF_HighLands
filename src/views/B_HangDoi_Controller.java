package views;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.HangDoi;
import model.NguyenLieu;

public class B_HangDoi_Controller implements Initializable {
	@FXML
	private JFXButton btnLamMoi;

	@FXML
	private JFXButton btnTraMon;

	@FXML
	private TableColumn<HangDoi, JFXButton> cBtnPhaChe;

	@FXML
	private TableColumn<HangDoi, JFXButton> cBtnTraMon;

	@FXML
	private TableColumn<HangDoi, ImageView> cHinhAnh;

	@FXML
	private TableColumn<HangDoi, Integer> cSoLuong;

	@FXML
	private TableColumn<HangDoi, String> cTen;

	@FXML
	private TableColumn<HangDoi, String> cMaDu;

	@FXML
	private TableColumn<HangDoi, String> cThoiGian;

	@FXML
	private TableColumn<HangDoi, String> cMaOr;

	@FXML
	private TableView<HangDoi> tbHangDoi;

	ArrayList<HangDoi> dsachhd;
	ObservableList<HangDoi> dshd;

	boolean kiemTraDaTraMon(String maor) {
		for(HangDoi e:dsachhd) {
			if (e.getMaor() == maor && !e.isDatramon())
				return false;
		}
		return true;
	}

	boolean kiemTraDangPhacChe(String maor) {
		int soLan= 0;
		for(HangDoi e:dsachhd) {
			if (e.getMaor() == maor && e.isDangPhaChe())
				soLan++;
			if(soLan>1)
				return true;
		}
		return false;
	}
	
	boolean kiemTraDaHoanToanTraMon() {
		for(HangDoi h: dsachhd) {
			if(h.isDangPhaChe())
				return false;
			else {
				if(h.isDatramon()) {	
					if(!kiemTraDaTraMon(h.getMaor()))
						return false;
				}
			}
		}
		return true;
	}
	
	public void loadHangDoi() {
		ConnectDB con = new ConnectDB();
		dsachhd = con.layDanhSachMon_HangDoi();

		for (HangDoi hd : dsachhd) {
			hd.getBtnPhaChe().setOnAction(e -> {
				hd.getBtnPhaChe().setDisable(true);
				hd.getBtnTraMon().setDisable(false);
				hd.setDangPhaChe(true);
				if(!kiemTraDangPhacChe(hd.getMaor())) {
					//Thay doi trang thai tren database
					if(con.kiemTraTrangThai(hd.getMaor()).equals("X")) {
						if(!con.thayDoiTrangThai(hd.getMaor(), "P")) {

							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Thông báo");
							alert.setHeaderText("Đổi trạng thái thất bại!");
							alert.showAndWait();
							
						}
					}
					else if(con.kiemTraTrangThai(hd.getMaor()).equals("H")){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Đơn hàng đã bị hủy!");
						alert.setContentText("Vui lòng chọn làm mới để cập nhật hàng đợi");
						alert.showAndWait();
					}
					
					
				}
		
			});
			hd.getBtnTraMon().setOnAction(e -> {
				hd.getBtnTraMon().setDisable(true);
				hd.setDatramon(true);
				hd.setDangPhaChe(false);
				if(kiemTraDaTraMon(hd.getMaor())) {
					//Thay doi tren database trang thai
					if(!con.thayDoiTrangThai(hd.getMaor(), "T")) {

						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Đổi trạng thái thất bại!");
						alert.showAndWait();
						
					}
				}
			
			});
		}
		

		dshd = FXCollections.observableArrayList(dsachhd);
		cMaOr.setCellValueFactory(new PropertyValueFactory<>("maor"));
		cHinhAnh.setCellValueFactory(new PropertyValueFactory<>("hinhanh"));
		cTen.setCellValueFactory(new PropertyValueFactory<>("tendu"));
		cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soluong"));
		cThoiGian.setCellValueFactory(new PropertyValueFactory<>("thoiGianDat"));
		cBtnPhaChe.setCellValueFactory(new PropertyValueFactory<>("btnPhaChe"));
		cBtnTraMon.setCellValueFactory(new PropertyValueFactory<>("btnTraMon"));
		cMaDu.setCellValueFactory(new PropertyValueFactory<>("madu"));
		tbHangDoi.setItems(dshd);
	}

	@FXML
	void lamMoi(MouseEvent event) {
		if(kiemTraDaHoanToanTraMon()) {
			loadHangDoi();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Làm mới thất bại");
			alert.setContentText("Vui lòng hoàn thành đơn gọi trước khi !");
			alert.showAndWait();
			
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadHangDoi();
	}
}
