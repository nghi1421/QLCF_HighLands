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

//Ki???m tra t??n nguy??n li???u

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
			tt.setText("T??n nguy??n li???u kh??ng ???????c ????? tr???ng");
			tt.getStyleClass().remove("tooltipError");
			tt.getStyleClass().add("tooltipError");
			txtTenNL.setTooltip(tt);
			txtTenNL.getStyleClass().add("error");
			tenNL = false;
		} else if (!ch.kiemTraTenNL(txtTenNL.getText()) && !txtTenNL.getText().trim().equals(nl.getTenNL())) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBTenNL.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("T??n nguy??n li???u b??? tr??ng.");
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

//Ki???m tra s??? l?????ng nguy??n li???u nh???p v??o

	@FXML
	void kiemTraSoLuong(KeyEvent e) {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		if (txtSoLuong.getText().trim().equals("")) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBSoLuong.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Vui l??ng nh???p s??? l?????ng");
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
			tt.setText("S??? li???u kh??ng h???p l???.");
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

//Ki???m tra d??? li???u ????n v??? nguy??n li???u	
	@FXML
	void kiemTraDonVi(KeyEvent e) {
		if (txtDonVi.getText().trim().equals("")) {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_error_48px.png"));
			imgTBDonVi.setImage(image);
			Tooltip tt = new Tooltip();
			tt.setText("Vui l??ng nh???p ????n v???");
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
			tt.setText("????n v??? v?????t qu?? ????? d??i quy ?????nh (10 k?? t???)");
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

//Th??m nguy??n li???u m???i
	@FXML
	void clickLuuNL(MouseEvent event) {
		if (nl == null) {
			if (tenNL && donVi) {
				NguyenLieu nl = new NguyenLieu("", txtTenNL.getText(), txtDonVi.getText(),
						Float.parseFloat(txtSoLuong.getText()));
				ConnectDB con = new ConnectDB();
				if (con.themNguyenLieu(nl)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Th??ng b??o");
					alert.setHeaderText("Th??m nguy??n li???u th??nh c??ng");
					alert.showAndWait();
					reloadNL(null);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Th??ng b??o");
					alert.setHeaderText("Th??m nguy??n li???u th???t b???i");
					alert.showAndWait();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Th??ng b??o");
				alert.setHeaderText("Th??m nguy??n li???u th???t b???i");
				alert.setContentText("Vui l??ng ki???m tra d??? li???u th??m v??o");
				alert.showAndWait();
			}
		} else {
			if (tenNL && donVi && soLuong) {

				ConnectDB con = new ConnectDB();
				ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
				if (con.suaNguyenLieu(txtMaNL.getText(), ch.chuanHoaChuoi(txtTenNL.getText()),
						ch.chuanHoaChuoi(txtDonVi.getText()), Float.parseFloat(txtSoLuong.getText()))) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Th??ng b??o");
					alert.setHeaderText("S???a nguy??n li???u th??nh c??ng");
					alert.showAndWait();
					reloadNL(null);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Th??ng b??o");
					alert.setHeaderText("S???a nguy??n li???u th???t b???i");
					alert.showAndWait();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Th??ng b??o");
				alert.setHeaderText("S???a nguy??n li???u th???t b???i");
				alert.setContentText("Vui l??ng ki???m tra d??? li???u th??m v??o");
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
		txtMaNL.setText("M?? nguy??n li???u t??? sinh");
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
				alert.setTitle("Th??ng b??o");
				alert.setHeaderText("B???n c?? ch???c ch???n x??a nguy??n li???u n??y kh??ng?");
				Optional<ButtonType> result = alert.showAndWait();
				if (!result.isPresent() || result.get() != ButtonType.OK) {
					return;
				} else {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setTitle("Th??ng b??o");
					alert1.setHeaderText("X??a th??ng tin nguy??n li???u th??nh c??ng!");
					alert1.showAndWait();
					reloadNL(null);
				}
			} else {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Th??ng b??o");
				alert1.setHeaderText("X??a nguy??n li???u th???t b???i !");
				alert1.showAndWait();
			}
		} else if (con.kiemTraKhoaNgoai_NguyenLieu(nl.getMaNL()) == 1) {
			// Bao loi
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Th??ng b??o");
			alert1.setHeaderText("Nguy??n li???u ???? ???????c l???p phi???u nh???p ho???c c??ng th???c pha ch???! Kh??ng th??? x??a!");
			alert1.showAndWait();

		} else {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Th??ng b??o");
			alert1.setHeaderText("L???i h??? th???ng!");
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
