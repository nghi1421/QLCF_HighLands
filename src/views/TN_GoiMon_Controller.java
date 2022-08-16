package views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import main.MyListener;
import model.DrinkForOrdersViews;
import model.KhachHang;

public class TN_GoiMon_Controller implements Initializable {
	String maor;

	@FXML
	private Label lbTieuDe;

	@FXML
	private VBox vbKhachHang;

	@FXML
	private TableView<KhachHang> tbKhachHang;

	@FXML
	private TableColumn<String, KhachHang> cMaKH;

	@FXML
	private TableColumn<String, KhachHang> cHoTenKH;

	@FXML
	private TableColumn<String, KhachHang> cSDT;


	
	ObservableList<KhachHang> dskh;

	@FXML
	private Label tenKH;

	@FXML
	private Label sdtKH;

	@FXML
	private VBox chosenCard;

	@FXML
	private GridPane gridView;

	@FXML
	private ScrollPane scrollDrinkView;

	@FXML
	private TableColumn<Long, DrinkForOrdersViews> cDonGia;

	@FXML
	private TableColumn<ImageView, DrinkForOrdersViews> cImg;

	@FXML
	private TableColumn<Integer, DrinkForOrdersViews> cSoLuong;

	@FXML
	private TableColumn<String, DrinkForOrdersViews> cTenDoUong;

	@FXML
	private TableColumn<Button, DrinkForOrdersViews> cTru;

	@FXML
	private TableView<DrinkForOrdersViews> tbOrders;

	@FXML
	private VBox borderPane;

	@FXML
	private TextField txtTimKiemKH;
	

	String maKh = null;

	private MyListener myListener;

	ObservableList<DrinkForOrdersViews> dsorders;
	private ArrayList<DrinkForOrdersViews> dsO = new ArrayList<>();

	private ArrayList<DrinkForOrdersViews> daChon = new ArrayList<>();

	// Click them order vao trong CSDL

//	@FXML
//	void luuOrders() {
//		ConnectDB con = new ConnectDB();
//		maor = con.layMaOrder();
//		if(daChon.size() ==0) {
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Thông báo");
//			alert.setHeaderText("Vui lòng chọn đồ uống trước khi lập order!");
//			alert.showAndWait();
//			return;
//		}
//		if (tenKH.getText().isEmpty() && sdtKH.getText().isEmpty()) {
//			if (con.lapOrder("NV01", "0", "0")) {
//				if (con.themCTORD(maor, daChon)) {
//					Alert alert = new Alert(AlertType.INFORMATION);
//					alert.setTitle("Thông báo");
//					alert.setHeaderText("Lập orders thành công!");
//					alert.showAndWait();
//				}
//			} else {
//				Alert alert = new Alert(AlertType.ERROR);
//				alert.setTitle("Thông báo");
//				alert.setHeaderText("Lập order thất bại!");
//				alert.setContentText("Vui lòng kiểm tra nguyên liệu");
//				alert.showAndWait();
//				return;
//			}
//
//		} else {
//			// Lấy mã khách hàng
////			maor = con.lap_CT_Orders("NV01", maKh, "0", daChon);
////			con.lap_CT_Orders(null, null, null, daChon);
//			if (con.lapOrder("NV01", maKh, "0")) {
//				if (con.themCTORD(maor, daChon)) {
//					Alert alert = new Alert(AlertType.INFORMATION);
//					alert.setTitle("Thông báo");
//					alert.setHeaderText("Lập orders thành công!");
//					alert.showAndWait();
//				}
//			} else {
//				Alert alert = new Alert(AlertType.ERROR);
//				alert.setTitle("Thông báo");
//				alert.setHeaderText("Lập order thất bại!");
//				alert.setContentText("Vui lòng kiểm tra nguyên liệu");
//				alert.showAndWait();
//				return;
//			}
//		}
//
//		inOrders();
//		dsorders.clear();
//		daChon.clear();
//		tbOrders.refresh();
//		tenKH.setText("");
//		sdtKH.setText("");
//		txtTimKiemKH.setText("");
//		maKh = null;
//	}

	// Order va thanh toan cho khach hang
	
	@FXML
	public void lapVaThanhToan() {
		ConnectDB con = new ConnectDB();
		maor = con.layMaOrder();
		
		if (daChon.size() == 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Vui lòng chọn đồ uống trước khi lập order!");
			alert.showAndWait();
			return;
		}
		
		System.out.println(maor);
		
		if(maor == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Lỗi hệ thống!");
			alert.showAndWait();
			return;
		}
		
		if (tenKH.getText().equals("") && sdtKH.getText().equals("")) {
			if (con.lapOrder(Main.maNv, "0", "0")) {
				if (con.themCTORD(maor, daChon)) {
					// load view thanh toán
					try {
						
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Lập order thành công!");
						alert.showAndWait();
						
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/views/TN_ThanhToan.fxml"));

						Parent parent = fxmlLoader.load();
						TN_ThanhToan_Controller c = fxmlLoader.getController();
						Scene scene = new Scene(parent);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.initStyle(StageStyle.UNDECORATED);
						c.setDuLieu(maor, Main.hoTenNv, con.layThoiGianLapDon(maor), con.layTongTienOrder(maor));
						stage.show();

					} catch (Exception e) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Lỗi hệ thống!");
						alert.showAndWait();
					}
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Lập orders không thành công!");
				alert.showAndWait();
			}

		} else {
			// Lấy mã khách hàng

			if (con.lapOrder(Main.maNv, maKh, "0")) {
				if (con.themCTORD(maor, daChon)) {
					try {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/views/TN_ThanhToan.fxml"));

						Parent parent = fxmlLoader.load();
						TN_ThanhToan_Controller c = fxmlLoader.getController();
						Scene scene = new Scene(parent);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.initStyle(StageStyle.UNDECORATED);
						c.setDuLieu(maor, Main.hoTenNv, con.layThoiGianLapDon(maor), con.layTongTienOrder(maor));
						stage.show();
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Lỗi hệ thống!");
						alert.showAndWait();
					}
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Ghi order không thành công!");
				alert.showAndWait();
			}
		}
		dsorders.clear();
		daChon.clear();
		tbOrders.refresh();
		tenKH.setText("");
		sdtKH.setText("");
		txtTimKiemKH.setText("");
		maKh = null;
	}

	// Click in order cho khach hang

	void inOrders() {
		try {
//Load view			
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/views/TN_Order_ThanhToan.fxml"));
			BorderPane anchorpane = fxmlLoader.load();
			fxmlLoader.getController();
			TN_Order_ThanhToan_Controller ohc = fxmlLoader.getController();
			ohc.hbSoTienKhachDua.setVisible(false);
			ohc.hbSoTienThua.setVisible(false);
			ohc.btnIn.setText("In order");
			Scene scene = new Scene(anchorpane);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);

			ConnectDB con = new ConnectDB();

			ohc.setData(daChon, con.layThoiGianLapDon(maor), maor);

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Thông báo");
			alert.setHeaderText("In phiếu orders thất bại!");
			alert.showAndWait();
			Stage stage = (Stage) borderPane.getScene().getWindow();
			stage.close();
		}
	}

	// Lấy danh sách đồ uống từ DB

	private List<DrinkForOrdersViews> getData() {
		ArrayList<DrinkForOrdersViews> drinks = new ArrayList<>();
		ConnectDB con = new ConnectDB();
		drinks = con.layDSDU();
		return drinks;
	}

	// Load table order của khách hàng

	private void loadBanOrder() {

		cImg.setCellValueFactory(new PropertyValueFactory<>("hinhAnh"));
		cTenDoUong.setCellValueFactory(new PropertyValueFactory<>("tenDoUong"));
		cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
		cDonGia.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
		cTru.setCellValueFactory(new PropertyValueFactory<>("tru"));
		tbOrders.setItems(dsorders);
		tbOrders.refresh();
	}

	// Bỏ đồ uống từ giỏ hàng ra ngoài

	private void truGioHang(DrinkForOrdersViews drink) {
		if (drink.getSoLuong() - 1 == 0) {
			tbOrders.getItems().remove(drink);
			daChon.remove(drink);
		} else {
			for (DrinkForOrdersViews d : daChon) {
				if (d.getMaDoUong().equals(drink.getMaDoUong())) {
					drink.setSoLuong(drink.getSoLuong() - 1);
				}
			}
			tbOrders.refresh();
		}
	}

	// Cap nhat bang orders
	private void setChosenDrink(DrinkForOrdersViews drink) {

		boolean coDoUong = false;
		for (DrinkForOrdersViews c : daChon) {
			if (drink.getMaDoUong().equals(c.getMaDoUong())) {
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
			float giamGia = drink.getGiamGia();

			DrinkForOrdersViews ct = new DrinkForOrdersViews(drink.getMaDoUong(), drink.getTenDoUong(), "", 0, 1);
			ct.setGiaBan((long) (gia * (1 - giamGia)));
			ct.setHinhAnh(drink.getHinhAnh());
			ct.setTru(drink.getTru());
			ct.getTru().setOnAction(e -> truGioHang(ct));
			daChon.add(ct);
		}

		dsorders = FXCollections.observableArrayList(daChon);
		loadBanOrder();
	}

	// tìm kiếm khách hàng cho views ORDERS

	@FXML
	private void timKiemKhachHang() {
		ConnectDB cn = new ConnectDB();
		KhachHang kh = cn.timKiemKhachHang(txtTimKiemKH.getText());
		if (kh != null) {
			tenKH.setText(kh.getHoTenKH());
			sdtKH.setText(kh.getSdt());
			maKh = txtTimKiemKH.getText();
		} else {
			maKh = null;
			tenKH.setText(null);
			sdtKH.setText(null);
		}

	}

	@FXML
	public void boTimKiem() {
		maKh = null;
		txtTimKiemKH.setText("");
		tenKH.setText(null);
		sdtKH.setText(null);

	}

	@FXML
	private void reloadKH() {
		ConnectDB con = new ConnectDB();
		dskh = FXCollections.observableArrayList(con.layDSKH());
		cMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
		cHoTenKH.setCellValueFactory(new PropertyValueFactory<>("hoTenKH"));
		cSDT.setCellValueFactory(new PropertyValueFactory<>("sdt"));
		tbKhachHang.setItems(dskh);
	}

	@FXML
	private void hienDSKH() {
		lbTieuDe.setText("Danh sách khách hàng");
		scrollDrinkView.setVisible(false);
		vbKhachHang.setVisible(true);

	}

	@FXML
	private void anDSKH() {
		lbTieuDe.setText("Danh sách đồ uống");
		scrollDrinkView.setVisible(true);
		vbKhachHang.setVisible(false);
	}

	@FXML
	private void loadSelectedKhachHang() {
		KhachHang kh = null;
		try {
			kh = tbKhachHang.getSelectionModel().getSelectedItem();
		} catch (Exception e) {

		}

		if (kh != null) {
			tenKH.setText(kh.getHoTenKH());
			sdtKH.setText(kh.getSdt());
			maKh = kh.getMaKH();
			anDSKH();
			txtTimKiemKH.setText(null);
		}

	}

	@FXML
	public void lamTrong() {
		boTimKiem();
		daChon.clear();;
		dsorders = FXCollections.observableArrayList(daChon);
		loadBanOrder();
	}
	
	@FXML
	public void lamMoi() {
		gridView.getChildren().clear();
		dsO.clear();
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
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		reloadKH();

		tbOrders.setSelectionModel(null);
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
			e.printStackTrace();
		}
		
		BoDauTiengViet bd = new BoDauTiengViet();

		FilteredList<KhachHang> filterKhachHang = new FilteredList<>(dskh, b -> true);
		txtTimKiemKH.textProperty().addListener((observable, oldValue, newValue) -> {
			filterKhachHang.setPredicate(kh -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (kh.getHoTenKH().toLowerCase().indexOf(lowerCaseFilter) != -1 || bd.boDau(kh.getHoTenKH()).toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true;
				} else if (kh.getMaKH().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
