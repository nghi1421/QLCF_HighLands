package views;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Connect.BoDauTiengViet;
import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import main.MyListener;
import model.Ctord;
import model.DrinkForOrdersViews;
import model.Orders;

public class TN_Order_Controller implements Initializable {

	@FXML
	private Label lbMaor;

	@FXML
	private Label lbTenNV;

	@FXML
	private Label thoiGianLap;

	@FXML
	private Label hoTenKH;

//	@FXML
//	private ImageView imgThanhToan;

	@FXML
	private Label lbTrangThai;

	@FXML
	private Label lbNhanVienTT;
	
	@FXML
	private Label thoiGianTT;
	
	@FXML
	private VBox hbChiTiet;

	/////////////////////
	@FXML
	private TableColumn<String, Orders> cMaO;

	@FXML
	private TableColumn<String, Orders> cThoiGianLap;

	@FXML
	private TableColumn<String, Orders> cTenNV;

	@FXML
	private TableColumn<String, Orders> cTenKH;

	@FXML
	private TableColumn<String, Orders> cTrangThai;

	@FXML
	private TableColumn<ImageView, Orders> cDaThanhToan;
	
	@FXML
	private TableColumn<ImageView, Orders> cThoiGianTT;

	@FXML
	private TableColumn<ImageView, Orders> cNhanVienTT;
	
	@FXML
	private TableView<Orders> tbDsOrder;

	ObservableList<Orders> danhSachO;

	private ArrayList<Orders> dso = new ArrayList<>();

	//////////////////////////

	@FXML
	private JFXButton btnClose;
	
	@FXML
	private TextField btnTimKiemOrder;
	
	@FXML
	private JFXButton btnThanhToan;
//	
	@FXML
	private JFXButton btnLuu;
	
	@FXML
	private JFXButton btnHuy;

	@FXML
	private GridPane gridView;

	@FXML
	private ScrollPane scrollDrinkView;

	private MyListener myListener;

	ObservableList<DrinkForOrdersViews> dsorders;
	private ArrayList<DrinkForOrdersViews> dsO = new ArrayList<>();

	private void setChosenDrink(DrinkForOrdersViews drink) {
		boolean coDoUong = false;
		for (Ctord c : ds_CTO) {
			if (drink.getMaDoUong().equals(c.getMadu())) {
				
				if(drink.getSlCoTheThucHien() == c.getSoLuong()) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Số lượng không thể đủ để lập thêm món!");
					alert.showAndWait();
					return ;
				}
				
				c.setSoLuong(c.getSoLuong() + 1);
				coDoUong = true;
			}
		}
		if (coDoUong == false) {
			long gia = drink.getGiaBan();
			drink.setGiaBan((long)(drink.getGiaBan()*(1-drink.getGiamGia())));
			Ctord ct = new Ctord(drink.getMaDoUong(), drink.getHinhAnh(), drink.getTenDoUong(), drink.getGiaBan(),
					drink.getSoLuong(), drink.getTru());
			
			ct.getBtnTru().setOnAction(e -> truGioHang(ct));
			
			ds_CTO.add(ct);
			drink.setGiaBan(gia);
		}

		cto = FXCollections.observableArrayList(ds_CTO);
		cImgOV.setCellValueFactory(new PropertyValueFactory<>("sourceAnh"));
		cTenDoUongOV.setCellValueFactory(new PropertyValueFactory<>("tenTu"));
		cDonGiaOV.setCellValueFactory(new PropertyValueFactory<>("donGia"));
		cTruOV.setCellValueFactory(new PropertyValueFactory<>("btnTru"));
		cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		tbCTOrders.setItems(cto);
		tbCTOrders.refresh();

	}

	//////////////////
	@FXML
	private TableColumn<ImageView, Ctord> cImgOV;

	@FXML
	private TableColumn<String, Ctord> cTenDoUongOV;

	@FXML
	private TableColumn<BigDecimal, Ctord> cDonGiaOV;

	@FXML
	private TableColumn<String, Ctord> cSoLuong;

	@FXML
	private TableColumn<JFXButton, Ctord> cTruOV;

	@FXML
	private TableView<Ctord> tbCTOrders;

	ObservableList<Ctord> cto;

	private ArrayList<Ctord> ds_CTO = new ArrayList<>();

//	private ArrayList<CT_ThucUong_Orders> dsThemOrders = new ArrayList<>();

	@FXML
	private StackPane stackPane;

	@FXML
	private VBox vbDSDU;

	@FXML
	private VBox vbDSO;

	String maor;
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Page chi tiết Orders

	// Load Orderss
	public void loadDanhSachOrder() {
		ConnectDB cn = new ConnectDB();
		dso = cn.layDsOrders();
		danhSachO = FXCollections.observableArrayList(dso);

		cMaO.setCellValueFactory(new PropertyValueFactory<>("maO"));
		cThoiGianLap.setCellValueFactory(new PropertyValueFactory<>("thoigianLap"));
		cTenNV.setCellValueFactory(new PropertyValueFactory<>("hoTenNV"));
		cTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
		cTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
		cDaThanhToan.setCellValueFactory(new PropertyValueFactory<>("thanhToan"));
		cThoiGianTT.setCellValueFactory(new PropertyValueFactory<>("thoiGianTT"));
		
		tbDsOrder.setItems(danhSachO);
		tbDsOrder.refresh();
		
		BoDauTiengViet bd = new BoDauTiengViet();
		
		FilteredList<Orders> filterOrder = new FilteredList<>(danhSachO, b -> true);
		btnTimKiemOrder.textProperty().addListener((observable, oldValue, newValue) -> {
			filterOrder.setPredicate(kh -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (kh.getMaO().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getHoTenNV().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(kh.getHoTenNV()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getThoigianLap().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				else if (kh.getTrangThai().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(kh.getTrangThai()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				else
					return false;
			});
		});
		SortedList<Orders> sortedData = new SortedList<>(filterOrder);
		sortedData.comparatorProperty().bind(tbDsOrder.comparatorProperty());
		tbDsOrder.setItems(sortedData);
	}

	
	@FXML
	public void huyDon() {
		//
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có chắc chắn hủy order này không?");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return;
		} else {
			ConnectDB con = new ConnectDB();
			if(!con.kiemTraTrangThai(lbMaor.getText()).equals("X")){
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Thông báo");
				if(con.kiemTraTrangThai(lbMaor.getText()).equals("P") || con.kiemTraTrangThai(lbMaor.getText()).equals("T")) {
					alert1.setHeaderText("Đơn hàng đang được pha chế không thể hủy!");
				}
				else {
					alert1.setHeaderText("Đơn hàng đã được hủy!");
					lamMoi();
					
					maor = "";
					vbDSDU.setVisible(false);
					vbDSO.setVisible(true);
					ds_CTO.clear();
					
					cto = FXCollections.observableArrayList(ds_CTO);

					cImgOV.setCellValueFactory(new PropertyValueFactory<>("sourceAnh"));
					cTenDoUongOV.setCellValueFactory(new PropertyValueFactory<>("tenTu"));
					cDonGiaOV.setCellValueFactory(new PropertyValueFactory<>("donGia"));
					cTruOV.setCellValueFactory(new PropertyValueFactory<>("btnTru"));
					cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
					
					tbCTOrders.setItems(cto);
					hbChiTiet.setDisable(true);
				
					lbMaor.setText(null);
					lbTenNV.setText(null);
					thoiGianLap.setText(null);
					hoTenKH.setText(null);
					lbTrangThai.setText(null);
					tbDsOrder.getSelectionModel().select(null);
					tbDsOrder.refresh();
				}
				alert1.showAndWait();
				
				return;
			}
			if(!con.thayDoiTrangThai(lbMaor.getText(),"H")) {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Thông báo");
				alert1.setHeaderText("Lỗi hệ thống!");
				alert1.setContentText("Hủy order thất bại!");
				alert1.showAndWait();
				return;
			}
			
			
			
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Hủy order thành công!");
			alert1.showAndWait();
			vbDSDU.setVisible(false);
//			btnThanhToan.setDisable(false);
//			btnLuu.setDisable(false);
//			btnHuy.setDisable(false);
			cTruOV.setVisible(false);	
			btnHuy.setDisable(true);
			btnThanhToan.setDisable(true);
			btnLuu.setDisable(true);
			vbDSO.setVisible(true);
			
			cto = FXCollections.observableArrayList(ds_CTO);
			cImgOV.setCellValueFactory(new PropertyValueFactory<>("sourceAnh"));
			cTenDoUongOV.setCellValueFactory(new PropertyValueFactory<>("tenTu"));
			cDonGiaOV.setCellValueFactory(new PropertyValueFactory<>("donGia"));
			cTruOV.setCellValueFactory(new PropertyValueFactory<>("btnTru"));
			cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
			tbCTOrders.setItems(cto);
			
			lbTrangThai.setText("Hủy");
			lamMoi();

			int index = -1;
			for(int i =0 ;i<dso.size();i++) {
				if(dso.get(i).getMaO().equals(lbMaor.getText())) {
					index = i;
					break;
				}
			}
			
			tbDsOrder.getSelectionModel().select(index);
			
			btnHuy.setDisable(true);
			btnThanhToan.setDisable(true);
			btnLuu.setDisable(true);
		}
		
	}
	// load CT Orderss
	@FXML
	public void loadCT_Orders() {
		ds_CTO.clear();
		
		Orders ordChon = null;
		try {
			ordChon = tbDsOrder.getSelectionModel().getSelectedItem();
		}
		catch(Exception e) {
			
		}
		
		if(!Main.quyen.equals("QUANLI")) {
//			vbDSDU.setDisable(false);;
			hbChiTiet.setDisable(false);
			if(ordChon.getThoiGianTT() != null) {
				vbDSDU.setVisible(false);
				vbDSO.setVisible(true);
				btnLuu.setDisable(true);
				btnThanhToan.setDisable(true);
				cTruOV.setVisible(false);
				btnHuy.setDisable(true);
				
			}
//			else if(ordChon.getTrangThai().equals("Đã xác nhận")) {
//				vbDSDU.setVisible(true);
//				vbDSO.setVisible(false);
//				btnLuu.setDisable(false);
//				btnThanhToan.setDisable(false);
//				cTruOV.setVisible(true);
//				btnHuy.setDisable(false);
//			}
			else if(ordChon.getTrangThai().equals("Đang pha chế") || ordChon.getTrangThai().equals("Đã trả món")) {
				vbDSDU.setVisible(false);
				vbDSO.setVisible(true);
				btnLuu.setDisable(true);
				btnThanhToan.setDisable(false);
				cTruOV.setVisible(false);
				btnHuy.setDisable(true);
			}
//			else if(ordChon.getTrangThai().equals("Hủy")) {
//				vbDSDU.setVisible(false);
//				vbDSO.setVisible(true);
//				btnLuu.setDisable(true);
//				btnThanhToan.setDisable(true);
//				cTruOV.setVisible(false);
//				btnHuy.setDisable(true);
//			}
			else {
				vbDSDU.setVisible(false);
				vbDSO.setVisible(true);
				
				btnHuy.setDisable(false);
				btnThanhToan.setDisable(false);
				btnLuu.setDisable(false);
				
//				btnThanhToan.setDisable(false);
				cTruOV.setVisible(false);
	//			btnHuy.setDisable(true);
			}
		}
		else {
			btnHuy.setVisible(false);
			btnLuu.setVisible(false);
			btnThanhToan.setVisible(false);
			cTruOV.setVisible(false);
			vbDSDU.setVisible(false);
		}

		if ( ordChon == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Bạn chưa chọn Order!");
			alert.showAndWait();
		} else {
			
//			if(!Main.quyen.equals("QUANLI")) {
//				hbChiTiet.setDisable(false);
//			}
			
			Orders o = tbDsOrder.getSelectionModel().getSelectedItem();
			lbMaor.setText(o.getMaO());
			lbTenNV.setText(o.getHoTenNV());
			thoiGianLap.setText(o.getThoigianLap());
			hoTenKH.setText(o.getTenKH());
			lbTrangThai.setText(o.getTrangThai());
			maor = o.getMaO();
			if(o.getThoiGianTT() == null) {
//				lbNhanVienTT.setText("");
				thoiGianTT.setText("");
			}
			else {
				thoiGianTT.setText(o.getThoiGianTT());
			}			
			
			ConnectDB con = new ConnectDB();
			ds_CTO = con.layDsCT_Orders(maor);
			if (ds_CTO == null) {
				// Báo lỗi
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Load danh chi tiết order thất bại!");
				alert.showAndWait();
				return;
			}

			for (Ctord c : ds_CTO) {
				c.getBtnTru().setOnAction(e -> truGioHang(c));
			}
			cto = FXCollections.observableArrayList(ds_CTO);

			cImgOV.setCellValueFactory(new PropertyValueFactory<>("sourceAnh"));
			cTenDoUongOV.setCellValueFactory(new PropertyValueFactory<>("tenTu"));
			cDonGiaOV.setCellValueFactory(new PropertyValueFactory<>("donGia"));
			cTruOV.setCellValueFactory(new PropertyValueFactory<>("btnTru"));
			cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
			tbCTOrders.setItems(cto);

		}
	}

	// Tru trong ghi chi tiet hoa don
	private void truGioHang(Ctord drink) {
		if (drink.getSoLuong() - 1 == 0) {
			tbCTOrders.getItems().remove(drink);
			ds_CTO.remove(drink);
		} else {
			for (Ctord d : ds_CTO) {
				if (d.equals(drink)) {
					d.setSoLuong(drink.getSoLuong() - 1);
				}
			}
			tbCTOrders.refresh();
		}

	}
//
//	
	@FXML
	public void lamMoi() {
		loadDanhSachOrder();
	}
	
	//
	@FXML
	private void closeDSDU() {
		Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.setAlwaysOnTop(false);

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có chắc chắn trở về không?");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			stage.setAlwaysOnTop(true);
		} else {
			maor = "";
			vbDSDU.setVisible(false);
			vbDSO.setVisible(true);
			ds_CTO.clear();
			
			cto = FXCollections.observableArrayList(ds_CTO);

			cImgOV.setCellValueFactory(new PropertyValueFactory<>("sourceAnh"));
			cTenDoUongOV.setCellValueFactory(new PropertyValueFactory<>("tenTu"));
			cDonGiaOV.setCellValueFactory(new PropertyValueFactory<>("donGia"));
			cTruOV.setCellValueFactory(new PropertyValueFactory<>("btnTru"));
			cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
			
			tbCTOrders.setItems(cto);
			hbChiTiet.setDisable(true);
		
			lbMaor.setText(null);
			lbTenNV.setText(null);
			thoiGianLap.setText(null);
			hoTenKH.setText(null);
			lbTrangThai.setText(null);
			
//			imgThanhToan.setImage(null);
			
			lbNhanVienTT.setText("");
			thoiGianTT.setText("");
			
			tbDsOrder.getSelectionModel().select(null);
			tbDsOrder.refresh();
		}
	}

	// Load view Thêm Orders
	@FXML
	private void loadThemOrders(MouseEvent event) {
//		if (tbDsOrderss.getSelectionModel().getSelectedItem() == null) {
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Thông báo");
//			alert.setHeaderText("Bạn chưa chọn Orders!");
//			alert.showAndWait();
//		} else {
//			
//			Stage stageNow = (Stage) ((Node) event.getSource()).getScene().getWindow();
//			
//			Parent parent = null;
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader();
//				fxmlLoader.setLocation(getClass().getResource("/views/ThemOrders.fxml"));
//
//				parent = fxmlLoader.load();
//				ThemOrders_Controller toc = fxmlLoader.getController();
//				toc.layThongTin_CT_Orders(tbDsOrderss.getSelectionModel().getSelectedItem().getMaO(),ds_CTO);
////				parent = FXMLLoader.load(getClass().getResource("/views/ThemOrders.fxml"));
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Scene scene = new Scene(parent);
//			Stage stage = new Stage();
//			stage.setScene(scene);
//			stage.initStyle(StageStyle.UTILITY);
//			stage.show();
//		}
	}

	private List<DrinkForOrdersViews> getData() {
		ArrayList<DrinkForOrdersViews> drinks = new ArrayList<>();
		ConnectDB con = new ConnectDB();
		drinks = con.layDSDU();
		return drinks;
	}

	@FXML
	private void LuuOrder() {
		ConnectDB con =new ConnectDB();
		if(!con.kiemTraTrangThai(lbMaor.getText()).equals("X")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Lưu order thất bại!");
			alert.setContentText("Order có thể đang được pha chế không thể sửa order");
			alert.showAndWait();
			return;
		}
		
		if(con.kiemTraThanhToan(lbMaor.getText())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Lưu order thất bại!");
			alert.setContentText("Order đã được thanh toán không thể sửa order");
			alert.showAndWait();
			return;
		}
		
		if(ds_CTO.size()==0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Không thể lưu order trống!");
			alert.showAndWait();
			
			lamMoi();

			int index = -1;
			for(int i =0 ;i<dso.size();i++) {
				if(dso.get(i).getMaO().equals(lbMaor.getText())) {
					index = i;
					break;
				}
			}
			
			tbDsOrder.getSelectionModel().select(index);
			loadCT_Orders();
			
			return;
		}

		if(con.suaChiTietOrder(maor, ds_CTO)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Lưu thông tin order thành công!");
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Lưu thông tin order thất bại!");
			alert.setContentText("Vui lòng kiểm tra nguyên liệu");
			alert.showAndWait();
		}
		lamMoi();
		int index = -1;
		for(int i =0 ;i<dso.size();i++) {
			if(dso.get(i).getMaO().equals(lbMaor.getText())) {
				index = i;
				break;
			}
		}
	
		tbDsOrder.getSelectionModel().select(index);
	}
	
	@FXML
	private void thanhToan(MouseEvent event) {
		ConnectDB con = new ConnectDB();
		if(con.kiemTraThanhToan(lbMaor.getText())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Order đã được thanh toán!");
			alert.showAndWait();

			vbDSDU.setVisible(false);
			btnThanhToan.setDisable(false);
//			cTruOV.setVisible(false);
			
			btnThanhToan.setDisable(true);
//			btnLuu.setDisable(true);
			vbDSO.setVisible(true);
			
			cto = FXCollections.observableArrayList(ds_CTO);
			cImgOV.setCellValueFactory(new PropertyValueFactory<>("sourceAnh"));
			cTenDoUongOV.setCellValueFactory(new PropertyValueFactory<>("tenTu"));
			cDonGiaOV.setCellValueFactory(new PropertyValueFactory<>("donGia"));
			cTruOV.setCellValueFactory(new PropertyValueFactory<>("btnTru"));
			cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
			tbCTOrders.setItems(cto);
			
//			lbTrangThai.setText("Hủy");
			lamMoi();

			int index = -1;
			for(int i =0 ;i<dso.size();i++) {
				if(dso.get(i).getMaO().equals(lbMaor.getText())) {
					index = i;
					break;
				}
			}
			
			tbDsOrder.getSelectionModel().select(index);
			loadCT_Orders();
			return;
		}
		long tongTien = con.layTongTienOrder(lbMaor.getText());
		if(tongTien == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Đơn trống vui lòng kiểm tra!");
			alert.showAndWait();
			return;
		}
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/views/TN_ThanhToan.fxml"));
			Parent parent = fxmlLoader.load();
			TN_ThanhToan_Controller c = fxmlLoader.getController();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);

			c.setDuLieu(lbMaor.getText(), Main.hoTenNv, thoiGianLap.getText(),
					tongTien);
			stage.show();
			
			cTruOV.setVisible(false);	
			btnHuy.setDisable(true);
			btnThanhToan.setDisable(true);
			btnLuu.setDisable(true);
			vbDSO.setVisible(true);
			vbDSDU.setVisible(false);
			cto = FXCollections.observableArrayList(ds_CTO);
			cImgOV.setCellValueFactory(new PropertyValueFactory<>("sourceAnh"));
			cTenDoUongOV.setCellValueFactory(new PropertyValueFactory<>("tenTu"));
			cDonGiaOV.setCellValueFactory(new PropertyValueFactory<>("donGia"));
			cTruOV.setCellValueFactory(new PropertyValueFactory<>("btnTru"));
			cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
			tbCTOrders.setItems(cto);
		
			
			lamMoi();
			
			maor = "";
			vbDSDU.setVisible(false);
			vbDSO.setVisible(true);
			ds_CTO.clear();
			
			cto = FXCollections.observableArrayList(ds_CTO);

			cImgOV.setCellValueFactory(new PropertyValueFactory<>("sourceAnh"));
			cTenDoUongOV.setCellValueFactory(new PropertyValueFactory<>("tenTu"));
			cDonGiaOV.setCellValueFactory(new PropertyValueFactory<>("donGia"));
//			cTruOV.setCellValueFactory(new PropertyValueFactory<>("btnTru"));
			cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
			
			tbCTOrders.setItems(cto);
			hbChiTiet.setDisable(true);
		
			lbMaor.setText(null);
			lbTenNV.setText(null);
			thoiGianLap.setText(null);
			hoTenKH.setText(null);
			lbTrangThai.setText(null);
			
//			imgThanhToan.setImage(null);
			
			lbNhanVienTT.setText("");
			thoiGianTT.setText("");
			
			tbDsOrder.getSelectionModel().select(null);
			tbDsOrder.refresh();
		}
		catch(Exception e ) {
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Thông báo");
//			alert.setHeaderText("Lỗi hệ thống!");
//			alert.showAndWait();
		}
		
//		Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		stage1.close();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if(!Main.quyen.equals("QUANLI")) {
			hbChiTiet.setDisable(true);
			vbDSDU.setVisible(false);
			

			dsO.addAll(getData());
			if (dsO.size() > 0) {
				myListener = new MyListener() {
					@Override
					public void onClickListener(DrinkForOrdersViews drink) {
						setChosenDrink(drink);
					}
				};

			}
			int column = 0;
			int row = 1;
			try {
				for (int i = 0; i < dsO.size(); i++) {

					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("item.fxml"));

					AnchorPane anchorpane = fxmlLoader.load();
					fxmlLoader.getController();
					itemController itemC = fxmlLoader.getController();

					// itemC.s

					itemC.setData(dsO.get(i), myListener);

					if (dsO.get(i).getSlCoTheThucHien() == 0) {
						anchorpane.setDisable(true);
					}
					
					if (column == 3) {
						column = 0;
						row++;
					}
					gridView.add(anchorpane, column++, row); // (child,column,row)

					GridPane.setMargin(anchorpane, new Insets(6));
				}
			} catch (IOException e) {
				
			}
			btnClose.setVisible(false);
		}else {
			hbChiTiet.setDisable(false);
			vbDSDU.setVisible(false);
			btnThanhToan.setVisible(false);
			btnLuu.setVisible(false);
			btnClose.setVisible(false);
			cTruOV.setVisible(false);
			btnHuy.setVisible(false);
			tbCTOrders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		}
		loadDanhSachOrder();
		
		BoDauTiengViet bd = new BoDauTiengViet();
		
		FilteredList<Orders> filterOrder = new FilteredList<>(danhSachO, b -> true);
		btnTimKiemOrder.textProperty().addListener((observable, oldValue, newValue) -> {
			filterOrder.setPredicate(kh -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (kh.getMaO().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getHoTenNV().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(kh.getHoTenNV()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getThoigianLap().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				else if (kh.getTrangThai().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(kh.getTrangThai()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				else
					return false;
			});
		});
		SortedList<Orders> sortedData = new SortedList<>(filterOrder);
		sortedData.comparatorProperty().bind(tbDsOrder.comparatorProperty());
		tbDsOrder.setItems(sortedData);

	}
	
}
