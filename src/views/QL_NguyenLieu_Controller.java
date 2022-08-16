package views;

import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Connect.BoDauTiengViet;
import Connect.ChuanHoaDuLieu;
import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.NguyenLieu;
import model.ThucUong;

public class QL_NguyenLieu_Controller implements Initializable {

	@FXML
	private TableView<NguyenLieu> tbNguyenLieu;

	@FXML
	private TableColumn<NguyenLieu, String> cTenNL;

	@FXML
	private TableColumn<NguyenLieu, String> cMaNL;

	@FXML
	private TableColumn<NguyenLieu, Date> cDonVi;

	@FXML
	private TableColumn<NguyenLieu, String> cSoLuong;

	ObservableList<NguyenLieu> dsnl;

	boolean tenNL = false;
	boolean donVi = false;
	boolean soLuong = false;

	NguyenLieu nl = new NguyenLieu();

	@FXML
	private Button btnLuu;
	@FXML
	private ImageView imgTBDonVi;

	@FXML
	private ImageView imgTBSoLuong;

	@FXML
	private ImageView imgTBTenNL;

	@FXML
	private Tooltip ttDonVi;

	@FXML
	private Tooltip ttMaNL;

	@FXML
	private Tooltip ttSoLuong;

	@FXML
	private Tooltip ttTenNL;

	@FXML
	private TextField txtDonVi;

	@FXML
	private TextField txtTenNL;

	@FXML
	private Label txtMaNL;

	@FXML
	private TextField txtSoLuong;

	@FXML
	private TextField txtTimKiem;

	@FXML
	private JFXButton btnXoa;

//Kiểm tra tên nguyên liệu

	@FXML
	void kiemTraTenNguyenLieu(KeyEvent e) {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		tenNL = false;
//		if(!txtTenNL.isFocused()) {
//			System.out.println("Vl");
//		}
		if (txtTenNL.getText().trim().equals("")) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBTenNL.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Tên nguyên liệu không được để trống");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtTenNL.setTooltip(tt);
			txtTenNL.getStyleClass().add("error");
			tenNL = false;
		} else if (!ch.kiemTraTenNL(txtTenNL.getText()) && !txtTenNL.getText().trim().equals(nl.getTenNL())) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBTenNL.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Tên nguyên liệu bị trùng.");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtTenNL.setTooltip(tt);
			txtTenNL.getStyleClass().remove("error");
			txtTenNL.getStyleClass().add("error");
			tenNL = false;
		} else if (ch.chuanHoaChuoi(txtTenNL.getText()).length() > 50) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBTenNL.setImage(image);
			Tooltip tt = new Tooltip();
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtTenNL.setTooltip(tt);
			txtTenNL.getStyleClass().remove("error");
			txtTenNL.getStyleClass().add("error");
			tenNL = false;
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtTenNL.getStyleClass().remove("error");
			imgTBTenNL.setImage(image);
			txtTenNL.setTooltip(null);
			tenNL = true;

		}
	}

//Kiểm tra số lượng nguyên liệu nhập vào

	@FXML
	void kiemTraSoLuong(KeyEvent e) {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		if (txtSoLuong.getText().trim().equals("")) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBSoLuong.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Vui lòng nhập số lượng");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtSoLuong.setTooltip(tt);
			txtSoLuong.getStyleClass().remove("error");
			txtSoLuong.getStyleClass().add("error");
			soLuong = false;
		} else if (ch.kiemTraSoThuc(txtSoLuong.getText()) == false) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBSoLuong.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Số liệu không hợp lệ.");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtSoLuong.setTooltip(tt);
			txtSoLuong.getStyleClass().remove("error");
			txtSoLuong.getStyleClass().add("error");
			soLuong = false;
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtSoLuong.getStyleClass().remove("error");
			imgTBSoLuong.setImage(image);
			txtSoLuong.setTooltip(null);
			soLuong = true;
		}
	}

//Kiểm tra dữ liệu Đơn vị nguyên liệu	
	@FXML
	void kiemTraDonVi(KeyEvent e) {
		if (txtDonVi.getText().trim().equals("")) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBDonVi.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Vui lòng nhập đơn vị");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtDonVi.setTooltip(tt);
			txtDonVi.getStyleClass().remove("error");
			txtDonVi.getStyleClass().add("error");
			donVi = false;
		} else if (txtDonVi.getText().trim().length() > 10) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBDonVi.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Đơn vị vượt quá độ dài quy định (10 kí tự)");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtDonVi.setTooltip(tt);
			txtDonVi.getStyleClass().remove("error");
			txtDonVi.getStyleClass().add("error");
			donVi = false;
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtDonVi.getStyleClass().remove("error");
			imgTBDonVi.setImage(image);
			txtDonVi.setTooltip(null);
			donVi = true;
		}
	}

//Thêm nguyên liệu mới
	@FXML
	void clickLuuNL(MouseEvent event) {
		if (nl == null) {
			if (tenNL && donVi) {
				NguyenLieu nl = new NguyenLieu("", txtTenNL.getText(), txtDonVi.getText(),
						Float.parseFloat(txtSoLuong.getText()));
				ConnectDB con = new ConnectDB();
				if (con.themNguyenLieu(nl)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Thêm nguyên liệu thành công");
					alert.showAndWait();
					reloadNL(null);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Thêm nguyên liệu thất bại");
					alert.showAndWait();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Thêm nguyên liệu thất bại");
				alert.setContentText("Vui lòng kiểm tra dữ liệu thêm vào");
				alert.showAndWait();
			}
		} else {
			if (tenNL && donVi && soLuong) {

				ConnectDB con = new ConnectDB();
				ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
				if (con.suaNguyenLieu(txtMaNL.getText(), ch.chuanHoaChuoi(txtTenNL.getText()),
						ch.chuanHoaChuoi(txtDonVi.getText()), Float.parseFloat(txtSoLuong.getText()))) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Sửa nguyên liệu thành công");
					alert.showAndWait();
					reloadNL(null);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Sửa nguyên liệu thất bại");
					alert.showAndWait();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Sửa nguyên liệu thất bại");
				alert.setContentText("Vui lòng kiểm tra dữ liệu thêm vào");
				alert.showAndWait();
			}
		}
	}

	@FXML
	public void chonNguyenLieu(MouseEvent e) {

		NguyenLieu nl = tbNguyenLieu.getSelectionModel().getSelectedItem();
		System.out.println(nl.getMaNL());
		if (nl != null) {
			this.nl = nl;
			txtMaNL.setText(nl.getMaNL());
			txtTenNL.setText(nl.getTenNL());
			txtSoLuong.setText(String.valueOf(nl.getKhoiLuong()));
			txtDonVi.setText(nl.getDonVi());
			tenNL = true;
			donVi = true;
			soLuong = true;
			txtSoLuong.setDisable(false);
			btnXoa.setDisable(false);
		}
	}

	public void setTrong() {
		imgTBTenNL.setImage(null);
		imgTBSoLuong.setImage(null);
		imgTBDonVi.setImage(null);
		nl = null;
		txtMaNL.setText(null);
		txtTenNL.setText(null);
		txtSoLuong.setText(null);
		txtDonVi.setText(null);
		txtMaNL.setText("Mã nguyên liệu tự sinh");
		try {
			tbNguyenLieu.getSelectionModel().select(null);
		} catch (Exception e) {

		}
		txtSoLuong.setText("0");
		txtSoLuong.setDisable(true);
		btnXoa.setDisable(true);
		
		tbNguyenLieu.refresh();
		tenNL = false;
		donVi = false;
		soLuong = false;
	}

	@FXML
	public void boTimKiem() {
		setTrong();
		txtSoLuong.setText("0");
		txtSoLuong.setDisable(true);
		btnXoa.setDisable(true);
	}

	@FXML
	public void xoaNguyenLieu() {
		NguyenLieu nl = null;
		try {
			nl = tbNguyenLieu.getSelectionModel().getSelectedItem();
		} catch (Exception e) {

		}

		ConnectDB con = new ConnectDB();
		if (con.kiemTraKhoaNgoai_NguyenLieu(nl.getMaNL()) == 0) {
			// xoa
			if (con.xoaNguyenLieu(nl.getMaNL())) {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Bạn có chắc chắn xóa nguyên liệu này không?");
				Optional<ButtonType> result = alert.showAndWait();
				if (!result.isPresent() || result.get() != ButtonType.OK) {
					return;
				} else {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setTitle("Thông báo");
					alert1.setHeaderText("Xóa thông tin nguyên liệu thành công!");
					alert1.showAndWait();
					reloadNL(null);
				}
			} else {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Thông báo");
				alert1.setHeaderText("Xóa nguyên liệu thất bại !");
				alert1.showAndWait();
			}
		} else if (con.kiemTraKhoaNgoai_NguyenLieu(nl.getMaNL()) == 1) {
			// Bao loi
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Nguyên liệu đã được lập phiếu nhập hoặc công thức pha chế! Không thể xóa!");
			alert1.showAndWait();

		} else {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Lỗi hệ thống!");
			alert1.showAndWait();
		}
	}

	@FXML
	private void reloadNL(MouseEvent e) {
		dsnl.clear();
		setTrong();
		ConnectDB cn = new ConnectDB();
		dsnl = FXCollections.observableArrayList(cn.layDsnl());
		cMaNL.setCellValueFactory(new PropertyValueFactory<>("maNL"));
		cTenNL.setCellValueFactory(new PropertyValueFactory<>("tenNL"));
		cSoLuong.setCellValueFactory(new PropertyValueFactory<>("khoiLuong"));
		cDonVi.setCellValueFactory(new PropertyValueFactory<>("donVi"));
		tbNguyenLieu.setItems(dsnl);
		tbNguyenLieu.refresh();
		
		BoDauTiengViet bd = new BoDauTiengViet();
		FilteredList<NguyenLieu> filterNguyenLieu = new FilteredList<>(dsnl, b -> true);
		txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filterNguyenLieu.setPredicate(kh -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (kh.getMaNL().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getTenNL().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(kh.getTenNL()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(kh.getKhoiLuong()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getDonVi().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(kh.getDonVi()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else
					return false;
			});
		});
		SortedList<NguyenLieu> sortedData = new SortedList<>(filterNguyenLieu);
		sortedData.comparatorProperty().bind(tbNguyenLieu.comparatorProperty());
		tbNguyenLieu.setItems(sortedData);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ConnectDB cn = new ConnectDB();
		nl =null;
		txtSoLuong.setText("0");
		txtSoLuong.setDisable(true);
		btnXoa.setDisable(true);
		dsnl = FXCollections.observableArrayList(cn.layDsnl());
		cMaNL.setCellValueFactory(new PropertyValueFactory<>("maNL"));
		cTenNL.setCellValueFactory(new PropertyValueFactory<>("tenNL"));
		cSoLuong.setCellValueFactory(new PropertyValueFactory<>("khoiLuong"));
		cDonVi.setCellValueFactory(new PropertyValueFactory<>("donVi"));
		tbNguyenLieu.setItems(dsnl);

	}

}
