package views;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Connect.BoDauTiengViet;
import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.KhachHang;
import model.ThucUong;

public class QL_DoUong_Controller implements Initializable{
	//KHAI BÁO CHO FORM ĐỒ UỐNG
	
	@FXML
	private TableColumn<ThucUong, String> cMaDoUong;

	@FXML
	private TableColumn<ThucUong, String> cTenDoUong;

	@FXML
	private TableColumn<ThucUong, ImageView> cHinhAnh;

	@FXML
	private TableColumn<ThucUong, Long> cGiaBan;

	@FXML
	private TableColumn<ThucUong, String> cKhuyenMai;
	
	@FXML
	private TableColumn<ThucUong, Integer> cSoLuongCT;

	@FXML
	private TableView<ThucUong> tbDoUong;
	
	@FXML
	private TextField btnTimKiem;
	
	ObservableList<ThucUong> dsDoUong;
	
	void loadDanhSachDanhUong() {
		ConnectDB con = new ConnectDB();
		dsDoUong = FXCollections.observableArrayList(con.layDanhSachDoUong());
		cMaDoUong.setCellValueFactory(new PropertyValueFactory<>("maDoUong"));
		cTenDoUong.setCellValueFactory(new PropertyValueFactory<>("tenDoUong"));
		cHinhAnh.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));
		cGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
		cKhuyenMai.setCellValueFactory(new PropertyValueFactory<>("khuyenmai"));
		cSoLuongCT.setCellValueFactory(new PropertyValueFactory<>("slCoTheThucHien"));
		tbDoUong.setItems(dsDoUong);
	}

	//load view them danh sách do uong
	@FXML
	public void loadViewThemDoUong(MouseEvent event) {
		try {
			Stage stageNow = (Stage) ((Node) event.getSource()).getScene().getWindow();

			Parent parent = FXMLLoader.load(getClass().getResource("/views/QL_DoUong_CT.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Sua thong tin do uong
	public void suaDoUong(MouseEvent event) {
		if (tbDoUong.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Bạn chưa chọn đồ uống!");
			alert.showAndWait();
		} else {
			ThucUong nv = tbDoUong.getSelectionModel().getSelectedItem();
			try {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/views/QL_DoUong_CT.fxml"));

				Parent parent = fxmlLoader.load();

				Scene scene = new Scene(parent);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.UNDECORATED);

				QL_DoUong_CT_Controller nvC = fxmlLoader.getController();
				nvC.setDuLieu(nv);
				
				stage.show();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void xoaDoUong() {
		ThucUong du  = null;
		try {
			du = tbDoUong.getSelectionModel().getSelectedItem();
		}
		catch(Exception  e){
			
		}
		if(du ==null) {
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Chưa chọn đồ uống!");
			alert1.showAndWait();
			return;
		}
		ConnectDB con = new ConnectDB();
		if(con.kiemTraKhoaNgoai_DoUong(du.getMaDoUong())==0) {
			//xoa
			if(con.xoaDoUong(du.getMaDoUong())) {
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Bạn có chắc chắn xóa đồ uống này không?");
				Optional<ButtonType> result = alert.showAndWait();
				if (!result.isPresent() || result.get() != ButtonType.OK) {
					return;
				} else {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setTitle("Thông báo");
					alert1.setHeaderText("Xóa thông tin đồ uống thành công!");
					alert1.showAndWait();
					lamMoi();
				}
			}
			else {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Thông báo");
				alert1.setHeaderText("Xóa đồ uống thất bại !");
				alert1.showAndWait();
			}
		}
		else if(con.kiemTraKhoaNgoai_DoUong(du.getMaDoUong())==1) {
			//Bao loi
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Đồ uống đã đã được lập order không thể xóa!");
			alert1.showAndWait();
			
		}
		else {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Lỗi hệ thống!");
			alert1.showAndWait();
		}
		
	}
	
	//lam moi
	public void lamMoi() {
		ConnectDB con = new ConnectDB();
		
		ObservableList<ThucUong> dsDoUong = FXCollections.observableArrayList(con.layDanhSachDoUong());
		cMaDoUong.setCellValueFactory(new PropertyValueFactory<>("maDoUong"));
		cTenDoUong.setCellValueFactory(new PropertyValueFactory<>("tenDoUong"));
		cHinhAnh.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));
		cGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
		cKhuyenMai.setCellValueFactory(new PropertyValueFactory<>("khuyenmai"));
		cSoLuongCT.setCellValueFactory(new PropertyValueFactory<>("slCoTheThucHien"));
		tbDoUong.setItems(dsDoUong);
		tbDoUong.refresh();
		BoDauTiengViet bd = new BoDauTiengViet();
		FilteredList<ThucUong> filterThucUong = new FilteredList<>(dsDoUong, b -> true);
		btnTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filterThucUong.setPredicate(kh -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (kh.getMaDoUong().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getTenDoUong().toLowerCase().indexOf(lowerCaseFilter) != -1
						||  bd.boDau(kh.getTenDoUong()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getKhuyenmai().toLowerCase().indexOf(lowerCaseFilter) != -1
						||  bd.boDau(kh.getKhuyenmai()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} 
				else if ( String.valueOf(kh.getGiaBan()).indexOf(lowerCaseFilter) !=-1) {
					return true;
				} 
				else
					return false;
			});
		});
		SortedList<ThucUong> sortedData = new SortedList<>(filterThucUong);
		sortedData.comparatorProperty().bind(tbDoUong.comparatorProperty());
		tbDoUong.setItems(sortedData);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadDanhSachDanhUong();
		BoDauTiengViet bd = new BoDauTiengViet();
		
		FilteredList<ThucUong> filterThucUong = new FilteredList<>(dsDoUong, b -> true);
		btnTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filterThucUong.setPredicate(kh -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (kh.getMaDoUong().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getTenDoUong().toLowerCase().indexOf(lowerCaseFilter) != -1
						||  bd.boDau(kh.getTenDoUong()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getKhuyenmai().toLowerCase().indexOf(lowerCaseFilter) != -1
						||  bd.boDau(kh.getKhuyenmai()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} 
				else if ( String.valueOf(kh.getGiaBan()).indexOf(lowerCaseFilter) !=-1) {
					return true;
				} 
				else
					return false;
			});
		});
		SortedList<ThucUong> sortedData = new SortedList<>(filterThucUong);
		sortedData.comparatorProperty().bind(tbDoUong.comparatorProperty());
		tbDoUong.setItems(sortedData);
	}
	
}
