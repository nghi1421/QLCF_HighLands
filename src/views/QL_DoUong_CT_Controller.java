package views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;

import Connect.BoDauTiengViet;
import Connect.ChuanHoaDuLieu;
import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CongThucPhaChe;
import model.NguyenLieu;
import model.ThucUong;

public class QL_DoUong_CT_Controller implements Initializable {

	boolean giaBan = false;
	boolean giamGia = false;
	boolean tenDoUong = false;
	boolean anh = false;
	boolean ctpc = false;

	@FXML
	private JFXToggleButton tbThem;

	@FXML
	private TextField txtNhapSearch;

	@FXML
	private AnchorPane apGiamGia;

	@FXML
	private ListView<String> lvGiamGia;

	ArrayList<String> dsachGiamGia = new ArrayList<>();

	@FXML
	private ImageView imgTBKM;

	private String sourceAnh = "";

	@FXML
	private VBox anchorPane;

	@FXML
	private TableColumn<CongThucPhaChe, String> cTenNL;

	@FXML
	private TableColumn<CongThucPhaChe, String> cMaNL2;

	@FXML
	private TableColumn<NguyenLieu, String> cMaNL;

	@FXML
	private JFXButton btnLuuThongTin;

	@FXML
	private JFXButton btnTroVe;

	@FXML
	private TableColumn<CongThucPhaChe, String> cDinhLuong;

	@FXML
	private TableColumn<CongThucPhaChe, String> cDonVi1;

	@FXML
	private TableColumn<NguyenLieu, String> cDonVi;

	@FXML
	private TableColumn<NguyenLieu, String> cTenNguyenLieu;

	@FXML
	private ImageView imgAnh;

	@FXML
	private ImageView imgTBCTPC;

	@FXML
	private ImageView imgTBChonAnh;

	@FXML
	private ImageView imgTBGiaBan;

	@FXML
	private ImageView imgTBTenDU;

	@FXML
	private TableView<CongThucPhaChe> tbCongThuc;

	@FXML
	private TableView<NguyenLieu> tbNguyenLieu;

	@FXML
	private TextField txtGiaBan;

	@FXML
	private Label txtMaDU;

	@FXML
	private TextField txtTenDU;

	@FXML
	private JFXButton btnChonAnh;
	private String maTu;

	ObservableList<NguyenLieu> dsnl;
	ArrayList<NguyenLieu> dsachNl = new ArrayList<>();

	ArrayList<CongThucPhaChe> dsachCt = new ArrayList<>();
	ObservableList<CongThucPhaChe> dsct;

//  public ValidationSupport validation = new ValidationSupport();
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

	// Load danh sach nguyen lieu
	public void loadDanhSachNguyenLieu() {

		dsnl = FXCollections.observableArrayList(dsachNl);

		cTenNguyenLieu.setCellValueFactory(new PropertyValueFactory<NguyenLieu, String>("tenNL"));
		cDonVi.setCellValueFactory(new PropertyValueFactory<NguyenLieu, String>("donVi"));
		cMaNL.setCellValueFactory(new PropertyValueFactory<NguyenLieu, String>("maNL"));
		tbNguyenLieu.setItems(dsnl);

	}

	public void loadDanhSachCTPC() {
		dsct = FXCollections.observableArrayList(dsachCt);
		cTenNL.setCellValueFactory(new PropertyValueFactory<CongThucPhaChe, String>("tenNL"));
		cMaNL2.setCellValueFactory(new PropertyValueFactory<CongThucPhaChe, String>("maNL"));
		cDinhLuong.setCellValueFactory(new PropertyValueFactory<CongThucPhaChe, String>("dinhLuong"));
		cDonVi1.setCellValueFactory(new PropertyValueFactory<CongThucPhaChe, String>("donVi"));
		tbCongThuc.setItems(dsct);

	}

	public void kiemTraDinhLuong(CellEditEvent event) {

		String giaTri = event.getNewValue().toString();

		String bieuThucSoThuc = "^(\\d+\\.)?\\d+$";
		if (!giaTri.matches(bieuThucSoThuc)) {
			Stage stage = (Stage) anchorPane.getScene().getWindow();

			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Định lượng không hợp lệ!");
			alert1.showAndWait();

			return;
		}

		CongThucPhaChe ct = tbCongThuc.getSelectionModel().getSelectedItem();
		ct.setDinhLuong("");
		int viTri = dsachCt.indexOf(ct);
		dsachCt.remove(ct);
		ct.setDinhLuong(giaTri);
		dsachCt.add(viTri, ct);
	}

	@FXML
	void luuDoUong(MouseEvent event) {
		if (!anh || !giaBan || !tenDoUong || !giamGia) {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Thêm đồ uống thất bại!");
			alert1.setContentText("Vui lòng kiểm tra thông tin");
			alert1.showAndWait();
//			System.out.println(anh);
//			System.out.println(giaBan);
//			System.out.println(tenDoUong);
//			System.out.println(giamGia);
			return;
		}
		if (dsachCt.size() == 0) {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Công thức pha chế không hợp lệ!");
			alert1.setContentText("Vui lòng kiểm tra công thức pha chế trước khi thực hiện thao tác thêm");
			alert1.showAndWait();
			return;
		}
		for (CongThucPhaChe ct : dsachCt) {
			try {
				if(Float.parseFloat(ct.getDinhLuong())==0) {
					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("Thông báo");
					alert1.setHeaderText("Công thức pha chế không hợp lệ!");
					alert1.setContentText("Vui lòng kiểm tra công thức pha chế trước khi thực hiện thao tác thêm");
					alert1.showAndWait();
					return;
				}
				
			} catch (Exception e) {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Thông báo");
				alert1.setHeaderText("Công thức pha chế không hợp lệ!");
				alert1.setContentText("Vui lòng kiểm tra công thức pha chế trước khi thực hiện thao tác thêm");
				alert1.showAndWait();
				return;
			}
		}

		if (sourceAnh.indexOf("/image/DoUong/") == -1) {
			sourceAnh = "/image/DoUong/" + sourceAnh;
		}
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		ConnectDB con = new ConnectDB();
		if (txtMaDU.getText().equals("Mã đồ uống tự sinh")) {

			maTu = con.layMaTU();
			String magg;
			Double phanTram = Double
					.parseDouble(txtNhapSearch.getText().substring(0, txtNhapSearch.getText().length() - 1)) / 100;
			if(tbThem.isSelected()) {
				if(!con.themGiamGia(phanTram)) {
					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.setTitle("Thông báo");
					alert2.setHeaderText("Thêm khuyến mãi thất bại!");
					alert2.showAndWait();
				}
			}
			magg = con.layMaGiamGia(phanTram);
//			System.out.println(magg);

			if (con.themThucUong(ch.chuanHoaChuoi(txtTenDU.getText().trim()), sourceAnh,
					Long.parseLong(txtGiaBan.getText().trim()), magg)) {
				Stage stage = (Stage) anchorPane.getScene().getWindow();
				// THem cong thuc pha che
				if (!con.themCongThucPhaChe(dsachCt, maTu)) {

					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.setTitle("Thông báo");
					alert2.setHeaderText("Thêm công thức pha chế thất bại!");
					alert2.showAndWait();
					// Xóa đồ uống vừa thêm
					return;
				}
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Thông báo");
				alert2.setHeaderText("Thêm đồ uống thành công!");
				alert2.showAndWait();

				// Thoat khoi stage
				stage.close();

			} else {
				Stage stage = (Stage) anchorPane.getScene().getWindow();
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Thông báo");
				alert1.setHeaderText("Thêm đồ uống thất bại !");
				alert1.showAndWait();
			}
		} else {

			String magg;
			Double phanTram = Double
					.parseDouble(txtNhapSearch.getText().substring(0, txtNhapSearch.getText().length() - 1)) / 100;
			
			if(tbThem.isSelected()) {
				if(!con.themGiamGia(phanTram)) {
					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.setTitle("Thông báo");
					alert2.setHeaderText("Thêm khuyến mãi thất bại!");
					alert2.showAndWait();
				}
			}
			
			magg = con.layMaGiamGia(phanTram);

			if (con.suaDoUong(txtMaDU.getText(), ch.chuanHoaChuoi(txtTenDU.getText().trim()), sourceAnh,
					Long.parseLong(txtGiaBan.getText().trim()), magg)) {
				Stage stage = (Stage) anchorPane.getScene().getWindow();
				// THem cong thuc pha che
				if (!con.themCongThucPhaChe(dsachCt, txtMaDU.getText())) {

					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.setTitle("Thông báo");
					alert2.setHeaderText("Sửa công thức pha chế thất bại!");
					alert2.showAndWait();
					// Xóa đồ uống vừa thêm
					return;
				}
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Thông báo");
				alert2.setHeaderText("Sửa đồ uống thành công!");
				alert2.showAndWait();

				// Thoat khoi stage
				stage.close();
			} else {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Thông báo");
				alert1.setHeaderText("Thêm đồ uống thất bại !");
				alert1.showAndWait();
			}
		}
	}

	@FXML
	void troVe(MouseEvent event) {
		Stage stage = (Stage) anchorPane.getScene().getWindow();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có chắc chắn trở về không?");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {

		} else {
			stage.close();
		}
	}

	// '
	public void setDuLieu(ThucUong tu) {
		txtNhapSearch.setText(tu.getKhuyenmai());
		sourceAnh = tu.getSourceAnh().substring(tu.getSourceAnh().lastIndexOf("/") + 1, tu.getSourceAnh().length());
		txtMaDU.setText(tu.getMaDoUong());
		txtGiaBan.setText(String.valueOf(tu.getGiaBan()));
		txtTenDU.setText(tu.getTenDoUong());
		// layMaGG

		Image image = new Image(tu.getSourceAnh());
		imgAnh.setImage(image);
		ConnectDB con = new ConnectDB();
		dsachCt = con.layDanhSachCTPC(tu.getMaDoUong());
		dsachNl = con.layDsnl();
		for (CongThucPhaChe ct : dsachCt) {
			dsachNl.removeIf(nl -> (ct.getMaNL().equals(nl.getMaNL())));
		}
		for (NguyenLieu nl : dsachNl) {
			System.out.println(nl.getMaNL());
		}
		loadDanhSachCTPC();
		loadDanhSachNguyenLieu();
		anh = true;
		giaBan = true;
		tenDoUong = true;
		giamGia = true;
	}

	@FXML
	void kiemTraGiaBan() {
		String bieuThucSoThuc = "^(\\d+\\.)?\\d+$";
		if (txtGiaBan.getText().trim().equals("")) {

			imgTBGiaBan.setImage(sai());
			txtGiaBan.setTooltip(toolTipValidate("Giá bán không thể để trống!"));
			txtGiaBan.getStyleClass().remove("error");
			txtGiaBan.getStyleClass().add("error");
			giaBan = false;

		} else if (!txtGiaBan.getText().trim().matches(bieuThucSoThuc)) {
			imgTBGiaBan.setImage(sai());
			txtGiaBan.setTooltip(toolTipValidate("Giá bán không hợp lệ!"));
			txtGiaBan.getStyleClass().remove("error");
			txtGiaBan.getStyleClass().add("error");
			giaBan = false;
		} else {
			txtGiaBan.getStyleClass().remove("error");
			imgTBGiaBan.setImage(dung());
			txtGiaBan.setTooltip(null);
			giaBan = true;
		}
	}

	@FXML
	void kiemTraTenDoUong() {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		ConnectDB con = new ConnectDB();
		if (txtTenDU.getText().trim().equals("")) {

			imgTBTenDU.setImage(sai());
			txtTenDU.setTooltip(toolTipValidate("Tên đồ uống không thể để trống!"));
			txtTenDU.getStyleClass().remove("error");
			txtTenDU.getStyleClass().add("error");
			tenDoUong = false;

		} else if (!con.kiemTraTenDoUong(txtTenDU.getText().trim())) {
			imgTBTenDU.setImage(sai());
			txtTenDU.setTooltip(toolTipValidate("Tên đồ uống bị trùng!"));
			txtTenDU.getStyleClass().remove("error");
			txtTenDU.getStyleClass().add("error");
			tenDoUong = false;
		} else {
			txtTenDU.getStyleClass().remove("error");
			imgTBTenDU.setImage(dung());
			txtTenDU.setTooltip(null);
			tenDoUong = true;
		}
	}

	@FXML
	void themCTPC(MouseEvent event) {
		NguyenLieu nl = tbNguyenLieu.getSelectionModel().getSelectedItem();

		if (nl == null)
			return;
		else {
			dsachNl.remove(nl);
			loadDanhSachNguyenLieu();
			CongThucPhaChe ctpc = new CongThucPhaChe(nl.getMaNL(), nl.getTenNL(), "", nl.getDonVi());

			dsachCt.add(ctpc);
			loadDanhSachCTPC();
			tbCongThuc.refresh();

		}
	}

	@FXML
	void truCTPC(MouseEvent event) {
		CongThucPhaChe ct = tbCongThuc.getSelectionModel().getSelectedItem();
		if (ct == null)
			return;
		else {
			dsachCt.remove(ct);
			loadDanhSachCTPC();
			tbCongThuc.refresh();

			NguyenLieu nl = new NguyenLieu();
			nl.setMaNL(ct.getMaNL());
			nl.setTenNL(ct.getTenNL());
			nl.setDonVi(ct.getDonVi());

			dsachNl.add(0, nl);
			loadDanhSachNguyenLieu();

		}
	}

//	@FXML
//	void kiemTraNhaCungCap() {
//		System.out.println(cbbGiamGia.getEditor().getText().trim());
//		if (cbbGiamGia.getEditor().getText().trim().length() == 0) {
//			Alert alert1 = new Alert(AlertType.ERROR);
//			alert1.setTitle("Thông báo");
//			alert1.setHeaderText("Giảm giá không thể để trống!");
//			alert1.showAndWait();
//			return;
//		}
//		if (dsgg.values().contains(cbbGiamGia.getEditor().getText())) {
//			imgTBKM.setImage(sai());
//			Alert alert1 = new Alert(AlertType.ERROR);
//			alert1.setTitle("Thông báo");
//			alert1.setHeaderText("Giảm giá bị trùng!");
//			alert1.showAndWait();
//		}
//	}

//	public boolean kiemTraGiamGia(Float ggMoi) {
//		for (String i : dsachGiamGia) {
//			if (!i.equals("Thêm giảm giá")) {
//				try {
//					if (Float.parseFloat(i.substring(0, i.length() - 1)) == ggMoi) {
//						cbbGiamGia.getSelectionModel().select(i);
//						imgTBKM.setImage(dung());
//						cbbGiamGia.getStyleClass().remove("error");
//						cbbGiamGia.setTooltip(null);
//						giamGia = true;
//						return false;
//					}
//
//				} catch (Exception e) {
//
//				}
//			}
//
//		}
//		return true;
//	}

//	public boolean kiemTraNhapGiamGia(String chuoiNhap) {
////		System.out.println(cbbGiamGia.getEditor().getText().toString());
//		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
//		if (!ch.kiemTraSoThuc(chuoiNhap.substring(0, chuoiNhap.length() - 1))) {
//			imgTBKM.setImage(sai());
//			cbbGiamGia.getStyleClass().remove("error");
//			cbbGiamGia.getStyleClass().add("error");
//			cbbGiamGia.setTooltip(toolTipValidate("Giảm giá không hợp lệ!"));
//			giamGia = false;
//			return false;
//		} else {
//			imgTBKM.setImage(dung());
//			cbbGiamGia.getStyleClass().remove("error");
//			cbbGiamGia.setTooltip(null);
//			giamGia = true;
//		}
//		return true;
//
//	}

	public boolean kiemTraTrung(String a) {
		for (String i : dsachGiamGia) {
			try {
				if (Float.parseFloat(i.substring(0, i.length() - 1)) == Float
						.parseFloat(a.substring(0, a.length() - 1))) {

					return false;
				}

			} catch (Exception e) {

			}
		}
		return true;
	}

	// Chon mã giảm giá
//	@FXML
//	public void chonGiamGia(ActionEvent event) {
//
//		if (cbbGiamGia.isEditable()) {
//
//			String chuoiMoi = cbbGiamGia.getEditor().getText();
//
////			System.out.print(chuoiMoi.substring(0, chuoiMoi.length() -1));
//
//			if (kiemTraTrung(chuoiMoi)) {
//				if (chuoiMoi.trim().length() == 0 || chuoiMoi.equals("Thêm giảm giá")) {
//					imgTBKM.setImage(sai());
//					cbbGiamGia.getSelectionModel().select("Thêm giảm giá");
//					cbbGiamGia.setTooltip(toolTipValidate("Vui lòng chọn giảm giá"));
//					cbbGiamGia.getStyleClass().remove("error");
//					cbbGiamGia.getStyleClass().add("error");
//					giamGia = false;
//					return;
//				}
//
//				if (chuoiMoi.charAt(chuoiMoi.length() - 1) != '%' && chuoiMoi.indexOf("%") != chuoiMoi.length() - 1) {
//					Alert alert1 = new Alert(AlertType.ERROR);
//					alert1.setTitle("Thông báo");
//					alert1.setHeaderText("Giảm giá nhập mới không hợp lệ!");
//					alert1.showAndWait();
//					cbbGiamGia.getSelectionModel().select("Thêm giảm giá");
//					giamGia = false;
//					return;
//				}
//
//				if (dsachGiamGia.indexOf(chuoiMoi) != -1) {
//					return;
//				}
//
//				String chuoiGiamGiaMoi = chuoiMoi.substring(0, chuoiMoi.length() - 1);
//				System.out.print(chuoiGiamGiaMoi);
//				float ggMoi = 0;
//				try {
//					ggMoi = Float.parseFloat(chuoiGiamGiaMoi);
//				} catch (Exception e) {
//					Alert alert1 = new Alert(AlertType.ERROR);
//					alert1.setTitle("Thông báo");
//					alert1.setHeaderText("Giảm giá nhập mới không hợp lệ!2");
//					alert1.showAndWait();
//					cbbGiamGia.getSelectionModel().select("Thêm giảm giá");
//					cbbGiamGia.setEditable(true);
//					giamGia = false;
//					return;
//				}
//
//				ConnectDB con = new ConnectDB();
//				if (con.themGiamGia(ggMoi / 100)) {
//					cbbGiamGia.setEditable(false);
//					cbbGiamGia.setTooltip(null);
//					imgTBKM.setImage(dung());
//					cbbGiamGia.getStyleClass().remove("error");
//					cbbGiamGia.setEditable(false);
//					giamGia = true;
//					dsachGiamGia.add(chuoiMoi);
//					dsachgg = FXCollections.observableArrayList(dsachGiamGia);
//					cbbGiamGia.setItems(dsachgg);
//					cbbGiamGia.getSelectionModel().select(chuoiMoi);
//				} else {
//					Alert alert1 = new Alert(AlertType.ERROR);
//					alert1.setTitle("Thông báo");
//					alert1.setHeaderText("Thêm giảm giá thất bại!");
//					cbbGiamGia.getSelectionModel().select("Thêm giảm giá");
//					alert1.showAndWait();
//					giamGia = false;
//				}
//
//			}
//		}
//
//		if (cbbGiamGia.getSelectionModel().getSelectedItem().equals("Thêm giảm giá")) {
//			imgTBKM.setImage(sai());
//			cbbGiamGia.setTooltip(toolTipValidate("Vui lòng chọn giảm giá"));
//			cbbGiamGia.getStyleClass().remove("error");
//			cbbGiamGia.getStyleClass().add("error");
//			giamGia = false;
//			cbbGiamGia.setEditable(true);
//
//		} else {
//			cbbGiamGia.setTooltip(null);
//			imgTBKM.setImage(dung());
//			cbbGiamGia.getStyleClass().remove("error");
//			cbbGiamGia.setEditable(false);
//			giamGia = true;
//		}
//
//	}

	public void loadGiamGia() {
		ConnectDB con = new ConnectDB();
		dsachGiamGia.clear();
		dsachGiamGia.addAll(con.layDanhSachGiamGia());
		lvGiamGia.getItems().addAll(dsachGiamGia);
	}

	private List<String> searchList(String searchWords, List<String> listOfStrings) {

		List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
		BoDauTiengViet bd = new BoDauTiengViet();

		return listOfStrings.stream().filter(input -> {
			return searchWordsArray.stream()
					.allMatch(word -> bd.boDau(input.toLowerCase()).contains(word.toLowerCase()));
		}).collect(Collectors.toList());
	}

	public boolean kiemTraKhuyenMai(String km) {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		if(km.replaceAll(" ","").length()== 0) {
			imgTBKM.setImage(sai());
			txtNhapSearch.getStyleClass().remove("error1");
			txtNhapSearch.getStyleClass().add("error1");
			txtNhapSearch.setTooltip(toolTipValidate("Giảm giá không thể để trống!"));
			giamGia = false;
			return false;
		}
		else if (km.indexOf("%") != km.length() - 1) {
			imgTBKM.setImage(sai());
			txtNhapSearch.getStyleClass().remove("error1");
			txtNhapSearch.getStyleClass().add("error1");
			txtNhapSearch.setTooltip(toolTipValidate("Khuyến mãi không hợp lệ!"));
			giamGia = false;
			return false;
		} else if (!ch.kiemTraSoThuc(km.substring(0, km.length() - 1))) {
			Double khuyenMai = Double.parseDouble(km.substring(0, km.length() - 1)) / 100;
			if (khuyenMai < 0 || khuyenMai > 1) {
				imgTBKM.setImage(sai());
				txtNhapSearch.getStyleClass().remove("error1");
				txtNhapSearch.getStyleClass().add("error1");
				txtNhapSearch.setTooltip(toolTipValidate("Khuyến mãi từ 0% -> 100%!"));
				giamGia = false;
				return false;
			}
			imgTBKM.setImage(sai());
			txtNhapSearch.getStyleClass().remove("error1");
			txtNhapSearch.getStyleClass().add("error1");
			txtNhapSearch.setTooltip(toolTipValidate("Khuyến mãi không hợp lệ!"));
			giamGia = false;
			return false;
		} 
		else {
			if(tbThem.isSelected()){
				if(!kiemTraTrung(km)){
					imgTBKM.setImage(sai());
					txtNhapSearch.getStyleClass().remove("error1");
					txtNhapSearch.getStyleClass().add("error1");
					txtNhapSearch.setTooltip(toolTipValidate("Khuyến mãi đã tồn tại!"));
					giamGia = false;
					return false;
				}
			}
			imgTBKM.setImage(dung());
			txtNhapSearch.getStyleClass().remove("error1");
			txtNhapSearch.setTooltip(null);
			giamGia = true;
		}
		return true;
	}

	@FXML
	public void search() {
		if (tbThem.isSelected()) {
			kiemTraKhuyenMai(txtNhapSearch.getText());
		} else {
			lvGiamGia.getItems().clear();
			lvGiamGia.getItems().addAll(searchList(txtNhapSearch.getText(), dsachGiamGia));
			lvGiamGia.refresh();
			kiemTraKhuyenMai(txtNhapSearch.getText());
		}

	}

	@FXML
	public void hienLv() {
		if(tbThem.isSelected()) {
			lvGiamGia.setVisible(false);
			kiemTraKhuyenMai(txtNhapSearch.getText());
		}
		else {
			lvGiamGia.setVisible(true);
		}
	}

	public void anLv() {
		lvGiamGia.setVisible(false);
		kiemTraKhuyenMai(txtNhapSearch.getText());
	}

	@FXML
	public void chonGiamGia() {
		String chon = null;
		try {
			chon = lvGiamGia.getSelectionModel().getSelectedItems().toString();
		} catch (Exception e) {

		}
		if (chon != null) {
			txtNhapSearch.setText(chon.replace("]", "").replace("[", ""));
			lvGiamGia.setVisible(false);
			kiemTraKhuyenMai(txtNhapSearch.getText());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ConnectDB con = new ConnectDB();

		loadGiamGia();

		dsachNl = con.layDsnl();

		loadDanhSachNguyenLieu();
		cDinhLuong.setCellFactory(TextFieldTableCell.forTableColumn());
		cDinhLuong.setOnEditCommit(
				(EventHandler<CellEditEvent<CongThucPhaChe, String>>) new EventHandler<CellEditEvent<CongThucPhaChe, String>>() {
					@Override
					public void handle(CellEditEvent<CongThucPhaChe, String> event) {
						CongThucPhaChe ct = event.getRowValue();
						ct.setDinhLuong(event.getNewValue());

						String giaTri = event.getNewValue().toString();

						String bieuThucSoThuc = "^(\\d+\\.)?\\d+$";
						try {
							if(Float.parseFloat(giaTri)==0) {
								Alert alert1 = new Alert(AlertType.ERROR);
								alert1.setTitle("Thông báo");
								alert1.setHeaderText("Định lượng không hợp lệ!");
								alert1.showAndWait();
								return;
							}
						}catch(Exception e) {
							
						}
						
						if (!giaTri.matches(bieuThucSoThuc)) {
							Stage stage = (Stage) anchorPane.getScene().getWindow();

							Alert alert1 = new Alert(AlertType.ERROR);
							alert1.setTitle("Thông báo");
							alert1.setHeaderText("Định lượng không hợp lệ!");
							alert1.showAndWait();
							return;
						}

						ct.setDinhLuong("");
						int viTri = dsachCt.indexOf(ct);
						dsachCt.remove(ct);
						ct.setDinhLuong(giaTri);
						dsachCt.add(viTri, ct);
					}
				});

		btnChonAnh.setOnAction(e -> {
			Stage stage = (Stage) anchorPane.getScene().getWindow();
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Chọn file ảnh");
			File file = chooser.showOpenDialog(stage);
			String goc = file.getAbsolutePath().substring(file.getAbsoluteFile().toString().indexOf(".") + 1,
					file.getAbsoluteFile().toString().length());
			// System.out.println(goc);

			if (file != null && (goc.equals("png") || goc.equals("jpg") || goc.equals("jfif"))) {

				sourceAnh = file.getAbsolutePath().toString().substring(
						file.getAbsoluteFile().toString().lastIndexOf("\\") + 1,
						file.getAbsoluteFile().toString().length());
				String tenanh = sourceAnh;
				ConnectDB con1 = new ConnectDB();
				int i = 1;
				while (!con1.kiemTraTenAnh(sourceAnh)) {
					sourceAnh = tenanh;
					sourceAnh += "(" + i++ + ")";
				}

				Path from, to;
				from = Paths.get(file.toURI());
				to = Paths.get("C:/Users/Nghi/eclipse-workspace/QLCF/src/image/DoUong/" + sourceAnh);

				try {
					Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
					anh = true;
				} catch (IOException e1) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Tên file trùng không thể chuyển file!");
					alert.setContentText("Vui lòng đổi tên file để hoàn thành tác vụ");
					alert.showAndWait();
					sourceAnh = "";
					anh = false;
					return;
				}

				String imagepath = file.getPath();
				Image image = new Image(imagepath);
				imgAnh.setImage(image);
			} else {

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Vui lòng chọn file ảnh!");
				alert.showAndWait();

			}
		});

	}
}
