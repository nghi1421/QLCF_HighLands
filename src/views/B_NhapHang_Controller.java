package views;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;

import Connect.BoDauTiengViet;
import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import model.Ctpn;
import model.NguyenLieu;
import model.PhieuNhap;

public class B_NhapHang_Controller implements Initializable {

	@FXML
	private TextField txtNhapSearch;

	@FXML
	private ListView<String> lvNcc;

	@FXML
	private VBox vbCTPN;

	@FXML
	private VBox vbBangPN;

	@FXML
	private CheckBox cbXacNhan;

	@FXML
	private TableColumn<NguyenLieu, String> cDonVi;

	@FXML
	private TableColumn<NguyenLieu, String> cTenNguyenLieu;

	@FXML
	private TableColumn<NguyenLieu, String> cMaNL;

	@FXML
	private TableView<NguyenLieu> tbNguyenLieu;

	ObservableList<NguyenLieu> dsnl;

	ArrayList<NguyenLieu> dsachNl = new ArrayList<>();

	PhieuNhap pnhap = new PhieuNhap();

	@FXML
	private JFXButton btnLuu;

	@FXML
	private JFXButton btnThoat;

	@FXML
	private JFXButton btnLapPhieu;

	@FXML
	private JFXButton btnXoa;

	@FXML
	private JFXButton btnThem;

	@FXML
	private JFXButton btnBot;

	@FXML
	private TableColumn<Ctpn, String> cDonGia;

	@FXML
	private TableColumn<Ctpn, String> cDonVi2;

	@FXML
	private TableColumn<Ctpn, String> cMaNL2;

	@FXML
	private TableColumn<PhieuNhap, String> cMaPN;

	@FXML
	private TableColumn<Ctpn, String> cSoLuong;

	@FXML
	private TableColumn<PhieuNhap, String> cTenNCC;

	@FXML
	private TableColumn<Ctpn, String> cTenNL;

	@FXML
	private TableColumn<PhieuNhap, String> cTenNV;

	@FXML
	private TableColumn<PhieuNhap, String> cThoiGian;

	@FXML
	private TableColumn<PhieuNhap, String> cTrangThai;

	boolean ncc = false;

	@FXML
	private ImageView imgTBNCC;

	@FXML
	private Label lbMaPN;

	@FXML
	private Label lbTenNV;

	@FXML
	private TableView<Ctpn> tbCTPN;

	@FXML
	private TableView<PhieuNhap> tbPN;

	@FXML
	private VBox vbThemBot;

	@FXML
	private Label lbThoiGian;

	@FXML
	private HBox hbNut;

	@FXML
	private TextField txtTimKiem;

	@FXML
	private JFXToggleButton tbNcc;

	@FXML
	private Label lbNcc;

	ArrayList<PhieuNhap> dsachpn = new ArrayList<>();
	ObservableList<PhieuNhap> dspn;

	ArrayList<Ctpn> dsachctpn = new ArrayList<>();
	ObservableList<Ctpn> dsctpn;

	ArrayList<String> dsncc = new ArrayList<>();
	ObservableList<String> dsachncc;

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

	// load danh sách nguyên liệu
	public void loadDanhSachPhieuNhap() {
		ConnectDB con = new ConnectDB();
		dsachpn = con.layDanhSachPhieuNhap();
		dspn = FXCollections.observableArrayList(dsachpn);
		cMaPN.setCellValueFactory(new PropertyValueFactory<PhieuNhap, String>("maPn"));
		cTenNV.setCellValueFactory(new PropertyValueFactory<PhieuNhap, String>("tenNv"));
		cThoiGian.setCellValueFactory(new PropertyValueFactory<PhieuNhap, String>("thoiGianLap"));
		cTenNCC.setCellValueFactory(new PropertyValueFactory<PhieuNhap, String>("tenNCC"));
		cTrangThai.setCellValueFactory(new PropertyValueFactory<PhieuNhap, String>("trangThai"));
		tbPN.setItems(dspn);
		tbPN.refresh();
		BoDauTiengViet bd = new BoDauTiengViet();

		FilteredList<PhieuNhap> filterPhieuNhap = new FilteredList<>(dspn, b -> true);
		txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filterPhieuNhap.setPredicate(kh -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (kh.getTenNv().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(kh.getTenNv()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getTenNCC().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(kh.getTenNCC()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getMaPn().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getThoiGianLap().indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false;
			});
		});
		SortedList<PhieuNhap> sortedData = new SortedList<>(filterPhieuNhap);
		sortedData.comparatorProperty().bind(tbPN.comparatorProperty());
		tbPN.setItems(sortedData);
	}

	// load danh sách nguyên liệu
	public void loadDanhSachNguyenLieu() {
		dsnl = FXCollections.observableArrayList(dsachNl);
		cTenNguyenLieu.setCellValueFactory(new PropertyValueFactory<NguyenLieu, String>("tenNL"));
		cDonVi.setCellValueFactory(new PropertyValueFactory<NguyenLieu, String>("donVi"));
		cMaNL.setCellValueFactory(new PropertyValueFactory<NguyenLieu, String>("maNL"));
		tbNguyenLieu.setItems(dsnl);
		tbNguyenLieu.refresh();
	}

	public void loadCTPN() {
		dsctpn = FXCollections.observableArrayList(dsachctpn);
		cTenNL.setCellValueFactory(new PropertyValueFactory<Ctpn, String>("tenNl"));
		cSoLuong.setCellValueFactory(new PropertyValueFactory<Ctpn, String>("soLuong"));
		cDonVi2.setCellValueFactory(new PropertyValueFactory<Ctpn, String>("donVi"));
		cMaNL2.setCellValueFactory(new PropertyValueFactory<Ctpn, String>("maNl"));
		cDonGia.setCellValueFactory(new PropertyValueFactory<Ctpn, String>("donGia"));
		tbCTPN.setItems(dsctpn);
		tbCTPN.refresh();
	}

	@FXML
	public void layCTPN() {
//		dsctpn.clear();
//		dsachctpn.clear();
		pnhap = tbPN.getSelectionModel().getSelectedItem();
		if (pnhap != null) {
			if (!Main.quyen.equals("QUANLI")) {
				vbCTPN.setDisable(false);
				vbBangPN.setDisable(true);
				cbXacNhan.setDisable(true);

				lbMaPN.setText(pnhap.getMaPn());
				lbTenNV.setText(pnhap.getTenNv());
				lbThoiGian.setText(pnhap.getThoiGianLap());
				txtNhapSearch.setText(pnhap.getTenNCC());
				
				//
				if (pnhap.getTrangThai().equals("Đợi kiểm tra")) {
//					lvNcc.setVisible(true);
					btnLuu.setDisable(false);
					btnXoa.setDisable(false);
					cbXacNhan.setSelected(false);

					lbNcc.setDisable(false);
					tbNcc.setDisable(false);

					tbNcc.setVisible(true);
					lbNcc.setVisible(true);
					
					btnThem.setVisible(true);
					btnBot.setVisible(true);

				} else {
					cbXacNhan.setSelected(true);
					btnLuu.setDisable(true);
					btnXoa.setDisable(true);
					vbThemBot.setDisable(false);
					lvNcc.setVisible(false);
					tbNcc.setVisible(false);
					lbNcc.setVisible(false);
					
					btnThem.setVisible(false);
					btnBot.setVisible(false);
				}

			} else {
				cbXacNhan.setDisable(false);
				btnLuu.setVisible(true);
				btnLuu.setDisable(false);

				btnXoa.setDisable(true);
				
				lbMaPN.setText(pnhap.getMaPn());
				lbTenNV.setText(pnhap.getTenNv());
				lbThoiGian.setText(pnhap.getThoiGianLap());

				txtNhapSearch.setText(pnhap.getTenNCC());
				if (pnhap.getTrangThai().equals("Đợi kiểm tra")) {
					cbXacNhan.setSelected(false);
				}
				else {
					cbXacNhan.setSelected(true);
				}
			}

			ncc = true;
			ConnectDB con = new ConnectDB();
			dsachctpn = con.layDanhSach_CTPN(pnhap.getMaPn());
			dsachNl = con.layDsnl();

			for (Ctpn ct : dsachctpn) {
				dsachNl.removeIf(nl -> nl.getMaNL().equals(ct.getMaNl()));
			}

			loadDanhSachNguyenLieu();
			loadCTPN();

//			loadNCC();

//			System.out.println(pnhap.getTenNCC());
//			cbbNCC.getSelectionModel().select(null);

//			lvNcc.setVisible(false);
		}

	}

	public void loadNCC() {
		ConnectDB con = new ConnectDB();
		dsncc.clear();
		dsncc.addAll(con.layDanhSachNCC());
	}

	////
	@FXML
	void themCTPC(MouseEvent event) {
		NguyenLieu nl = tbNguyenLieu.getSelectionModel().getSelectedItem();

		if (nl == null)
			return;
		else {
			dsachNl.remove(nl);
			loadDanhSachNguyenLieu();
			Ctpn ctpn = new Ctpn();
			ctpn.setMaNl(nl.getMaNL());
			ctpn.setTenNl(nl.getTenNL());
			ctpn.setDonVi(nl.getDonVi());
			dsachctpn.add(ctpn);
			loadCTPN();
			tbCTPN.refresh();
		}
	}

	@FXML
	void truCTPC(MouseEvent event) {
		Ctpn ct = tbCTPN.getSelectionModel().getSelectedItem();
		if (ct == null)
			return;
		else {
			dsachctpn.remove(ct);
			loadCTPN();
			tbCTPN.refresh();

			NguyenLieu nl = new NguyenLieu();
			nl.setMaNL(ct.getMaNl());
			nl.setTenNL(ct.getTenNl());
			nl.setDonVi(ct.getDonVi());

			dsachNl.add(0, nl);
			loadDanhSachNguyenLieu();

		}
	}

	@FXML
	void XoaPN(MouseEvent event) {
		ConnectDB con = new ConnectDB();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có thật sự muốn thoát không?");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return;
		} else {
			if (!con.xoaPhieuNhap(lbMaPN.getText())) {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Thông báo");
				alert1.setHeaderText("Xóa phiếu nhập thất bại!");
				alert1.showAndWait();
				return;
			}
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Xóa phiếu nhập thành công!");
			alert1.showAndWait();

			vbCTPN.setDisable(true);
			vbBangPN.setDisable(false);
			loadDanhSachNguyenLieu();
			loadCTPN();
			lbMaPN.setText("Mã phiếu nhập tự sinh");
			lbTenNV.setText("");
			imgTBNCC.setImage(null);
			lbThoiGian.setText("");
			loadNCC();
			lvNcc.setVisible(false);
			imgTBNCC.setImage(null);
			txtNhapSearch.setText("");
			ncc = false;

			tbNcc.setDisable(true);
			tbNcc.setSelected(false);

			lbNcc.setDisable(true);
			lvNcc.setVisible(false);

			tbPN.getSelectionModel().select(null);
			tbPN.refresh();
			dsnl.clear();
			tbNguyenLieu.refresh();
			dsctpn.clear();
			tbCTPN.refresh();
			loadDanhSachPhieuNhap();
			tbPN.refresh();
		}
	}

	@FXML
	void boTimKiem(MouseEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có thật sự muốn thoát không?");
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return;
		} else {
			vbCTPN.setDisable(true);
			vbBangPN.setDisable(false);
			
			loadCTPN();
			lbMaPN.setText("Mã phiếu nhập tự sinh");
			lbTenNV.setText("");
			imgTBNCC.setImage(null);
			lbThoiGian.setText("");
			loadNCC();
			lvNcc.setVisible(false);
			imgTBNCC.setImage(null);
			txtNhapSearch.setText("");
			ncc = false;

			tbNcc.setDisable(true);
			tbNcc.setSelected(false);

			lbNcc.setDisable(true);
			lvNcc.setVisible(false);

			tbPN.getSelectionModel().select(null);
			tbPN.refresh();

			dsnl.clear();
			tbNguyenLieu.refresh();

			dsctpn.clear();
			tbCTPN.refresh();
			
			loadDanhSachNguyenLieu();

		}

	}

	@FXML
	void lamMoiPhieuNhap(MouseEvent event) {
		loadDanhSachPhieuNhap();
		tbPN.refresh();
		
		lbMaPN.setText("Mã phiếu nhập tự sinh");
		lbTenNV.setText("");
		imgTBNCC.setImage(null);
		lbThoiGian.setText("");

		txtNhapSearch.setText("");
		
		dsnl.clear();
		tbNguyenLieu.refresh();
		dsctpn.clear();
		tbCTPN.refresh();
		
		cbXacNhan.setSelected(false);
	}

	@FXML
	void luuPhieuNhap(MouseEvent event) {
//		for (Ctpn ct : dsachctpn) {
//			System.out.println(ct.getMaNl());
//		}
		if(Main.quyen.equals("QUANLI")) {
			ConnectDB con1 =new ConnectDB();
			PhieuNhap pn = tbPN.getSelectionModel().getSelectedItem();
			int vitri = tbPN.getSelectionModel().getSelectedIndex();
			if(cbXacNhan.isSelected()) {
				if(pn.getTrangThai().equals("Đã xác nhận")) {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setTitle("Thông báo");
					alert1.setHeaderText("Không có thông tin thay đổi!");
					alert1.showAndWait();
					return;
				}
				else {
					if(con1.suaPhieuNhap_TrangThai(lbMaPN.getText(), "X")) {
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setTitle("Thông báo");
						alert1.setHeaderText("Lưu thông tin phiếu nhập thành công!");
						alert1.showAndWait();
						
						loadDanhSachPhieuNhap();
						tbPN.getSelectionModel().select(vitri);
						tbPN.refresh();
						layCTPN();
						
						return;
					}
					else {
						Alert alert1 = new Alert(AlertType.ERROR);
						alert1.setTitle("Thông báo");
						alert1.setHeaderText("Lưu thông tin phiếu nhập thất bại!");
						alert1.showAndWait();
						return;
					}
				}
			}
			else {
				if(pn.getTrangThai().equals("Đợi kiểm tra")) {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setTitle("Thông báo");
					alert1.setHeaderText("Không có thông tin thay đổi!");
					alert1.showAndWait();
					return;
				}
				else {
					if(con1.suaPhieuNhap_TrangThai(lbMaPN.getText(), "D")) {
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setTitle("Thông báo");
						alert1.setHeaderText("Lưu thông tin phiếu nhập thành công!");
						alert1.showAndWait();
						
						loadDanhSachPhieuNhap();

						tbPN.getSelectionModel().select(vitri);
						tbPN.refresh();
						layCTPN();
						
						return;
					}
					else {
						Alert alert1 = new Alert(AlertType.ERROR);
						alert1.setTitle("Thông báo");
						alert1.setHeaderText("Lưu thông tin phiếu nhập thất bại!");
						alert1.showAndWait();
						return;
					}
				}
			}
		}
		
		if (!ncc || dsachctpn.size() == 0) {
			Alert alert1 = new Alert(AlertType.WARNING);
			alert1.setTitle("Thông báo");
			alert1.setHeaderText("Vui lòng kiểm tra thông tin phiếu!");
			alert1.showAndWait();
		} else {
			for (Ctpn ct : dsachctpn) {
				try {
					Float.parseFloat(ct.getSoLuong());

				} catch (Exception e) {
					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("Thông báo");
					alert1.setHeaderText("Số lượng nguyên liệu không hợp lê!");
					alert1.setContentText("Vui lòng kiểm tra số lượng nguyên liệu trong phiếu nhập");
					alert1.showAndWait();
					return;
				}
				try {
					Long.parseLong(ct.getDonGia());
				} catch (Exception e) {
					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("Thông báo");
					alert1.setHeaderText("Đơn giá nguyên liệu không hợp lệ!");
					alert1.setContentText("Vui lòng kiểm tra đơn giá nguyên liệu trong phiếu nhập");
					alert1.showAndWait();
					return;
				}
			}
			if (lbMaPN.getText().equals("Mã phiếu nhập tự sinh")) {

				ConnectDB con = new ConnectDB();
				String mapn = null;
				if (tbNcc.isSelected()) {
					if (con.themNhaCungCap(txtNhapSearch.getText())) {
						if (ncc)
							mapn = con.themPhieuNhap(Main.maNv, con.layMaNCC(txtNhapSearch.getText()));
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Lập phiếu thất bại!");
						alert.setContentText("Thêm nhà cung cấp thất bại!");
						alert.showAndWait();
						return;
					}

				} else {
					if (ncc) {
						mapn = con.themPhieuNhap(Main.maNv, con.layMaNCC(txtNhapSearch.getText()));
					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Nhà cung cấp không tồn tại");
						alert.setContentText("Nhà cung cấp không tồn tại vui lòng chọn chế độ thêm nhà cung cấp mới!");
						alert.showAndWait();
						return;
					}
				}

				if (mapn != null) {
					// Them ctpc
					for (Ctpn ct : dsachctpn) {
						if (!con.themCTPN(ct.getMaNl(), mapn, Float.parseFloat(ct.getSoLuong()),
								Long.parseLong(ct.getDonGia()))) {
							// Xoa tat ca thong tin them
							Alert alert1 = new Alert(AlertType.INFORMATION);
							alert1.setTitle("Thông báo");
							alert1.setHeaderText("Lập phiếu nhập thất bại!");
							alert1.showAndWait();
							return;
						}
					}
					vbCTPN.setDisable(true);
					vbBangPN.setDisable(false);
					loadDanhSachNguyenLieu();
					loadCTPN();
					lbMaPN.setText("Mã phiếu nhập tự sinh");
					lbTenNV.setText("");
					imgTBNCC.setImage(null);
					lbThoiGian.setText("");
					loadNCC();
					txtNhapSearch.setText("");
					lvNcc.setVisible(false);
					imgTBNCC.setImage(null);
					txtNhapSearch.setText("");
					ncc = false;

					tbNcc.setDisable(true);
					tbNcc.setSelected(false);

					lbNcc.setDisable(true);
					lvNcc.setVisible(false);

					tbPN.getSelectionModel().select(null);
					tbPN.refresh();
					dsnl.clear();
					tbNguyenLieu.refresh();
					dsctpn.clear();
					tbCTPN.refresh();
					loadDanhSachPhieuNhap();
					tbPN.refresh();

				} else {
					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("Thông báo");
					alert1.setHeaderText("Lỗi hệ thống!");
					alert1.showAndWait();
					return;
				}
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Thông báo");
				alert1.setHeaderText("Lập phiếu nhập thành công!");
				alert1.showAndWait();

			} else {
				// Sửa thong tin
				ConnectDB con = new ConnectDB();
				String mapn = lbMaPN.getText();

				if (tbNcc.isSelected()) {
					if (con.themNhaCungCap(txtNhapSearch.getText())) {
						if (ncc)
							if (!con.suaPhieuNhap_NCC(lbMaPN.getText(), con.layMaNCC(txtNhapSearch.getText()))) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Thông báo");
								alert.setHeaderText("Sửa phiếu nhập thất bại!");
								alert.setContentText("Vui lòng chọn nhà cung cấp!");
								alert.showAndWait();
								return;
							}
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Sửa phiếu thất bại!");
						alert.setContentText("Thêm nhà cung cấp mới thất bại!");
						alert.showAndWait();
						return;
					}

				} else {
					if (ncc) {
						if (!con.suaPhieuNhap_NCC(lbMaPN.getText(), con.layMaNCC(txtNhapSearch.getText()))) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Thông báo");
							alert.setHeaderText("Sửa phiếu nhập thất bại!");
							alert.setContentText("Vui lòng chọn nhà cung cấp!");
							alert.showAndWait();
							return;
						}
					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Nhà cung cấp không tồn tại");
						alert.setContentText("Nhà cung cấp không tồn tại vui lòng chọn chế độ thêm nhà cung cấp mới!");
						alert.showAndWait();
						return;
					}
				}

				for (Ctpn ct : dsachctpn) {
					if (!con.suaPhieuNhap(ct.getMaNl(), mapn, Float.parseFloat(ct.getSoLuong()),
							Long.parseLong(ct.getDonGia()))) {
						// Xoa tat ca thong tin them
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setTitle("Thông báo");
						alert1.setHeaderText("Sửa phiếu nhập thất bại!");
						alert1.showAndWait();
						return;
					}
				}

				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Thông báo");
				alert1.setHeaderText("Sửa phiếu nhập thành công!");
				alert1.showAndWait();

				vbCTPN.setDisable(true);
				vbBangPN.setDisable(false);
				loadDanhSachNguyenLieu();
				loadCTPN();
				lbMaPN.setText("Mã phiếu nhập tự sinh");
				lbTenNV.setText("");
				imgTBNCC.setImage(null);
				lbThoiGian.setText("");
				loadNCC();
				lvNcc.setVisible(false);
				imgTBNCC.setImage(null);
				txtNhapSearch.setText("");
				ncc = false;

				tbNcc.setDisable(true);
				tbNcc.setSelected(false);

				lbNcc.setDisable(true);
				lvNcc.setVisible(false);

				tbPN.getSelectionModel().select(null);
				tbPN.refresh();
				dsnl.clear();
				tbNguyenLieu.refresh();
				dsctpn.clear();
				tbCTPN.refresh();
				loadDanhSachPhieuNhap();
				tbPN.refresh();

			}
		}

	}

	@FXML
	void kiemTraNhaCungCap() {
		if (txtNhapSearch.getText().trim().length() == 0) {
			imgTBNCC.setImage(sai());
			txtNhapSearch.setTooltip(toolTipValidate("Tên nhà cung cấp không thể để trống!"));
			txtNhapSearch.getStyleClass().remove("error1");
			txtNhapSearch.getStyleClass().add("error1");
			ncc = false;
		} else if (dsncc.indexOf(txtNhapSearch.getText().trim()) != -1) {
			imgTBNCC.setImage(sai());
			txtNhapSearch.setTooltip(toolTipValidate("Tên nhà cung cấp bị trùng!"));
			txtNhapSearch.getStyleClass().remove("error1");
			txtNhapSearch.getStyleClass().add("error1");
			ncc = false;
		} else {
			txtNhapSearch.getStyleClass().remove("error1");
			imgTBNCC.setImage(dung());
			txtNhapSearch.setTooltip(null);
			ncc = true;
		}
	}

	@FXML
	public void lapPhieu() {

		vbCTPN.setDisable(false);
		vbBangPN.setDisable(true);
		btnXoa.setDisable(true);
		lvNcc.setVisible(false);

		ConnectDB con = new ConnectDB();

		dsachctpn.clear();

		dsachNl = con.layDsnl();
		loadDanhSachNguyenLieu();
		loadCTPN();

		lbMaPN.setText("Mã phiếu nhập tự sinh");
		lbTenNV.setText("");
		imgTBNCC.setImage(null);
		lbThoiGian.setText("");

		tbNcc.setDisable(false);
		lbNcc.setDisable(false);
	}

	private List<String> searchList(String searchWords, List<String> listOfStrings) {

		List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
		BoDauTiengViet bd = new BoDauTiengViet();

		return listOfStrings.stream().filter(input -> {
			return searchWordsArray.stream()
					.allMatch(word -> bd.boDau(input.toLowerCase()).contains(word.toLowerCase()));
		}).collect(Collectors.toList());
	}

	@FXML
	public void search() {

		if (tbNcc.isSelected()) {
			kiemTraNhaCungCap();
		} else {
			lvNcc.getItems().clear();
			lvNcc.getItems().addAll(searchList(txtNhapSearch.getText(), dsncc));
			lvNcc.refresh();
			txtNhapSearch.getStyleClass().remove("error1");
			imgTBNCC.setImage(dung());
			txtNhapSearch.setTooltip(null);
		}

	}

	@FXML
	public void chonNcc() {
		txtNhapSearch
				.setText(lvNcc.getSelectionModel().getSelectedItems().toString().replace("]", "").replace("[", ""));
		lvNcc.setVisible(false);

		txtNhapSearch.getStyleClass().remove("error1");
		imgTBNCC.setImage(dung());
		txtNhapSearch.setTooltip(null);
		ncc = true;

		ncc = true;
	}

	@FXML
	public void hienLv() {
		if (Main.quyen.equals("QUANLI")) {
			lvNcc.setVisible(false);
			return;
		}
		if (!tbNcc.isSelected()) {
			txtNhapSearch.getStyleClass().remove("error1");
			imgTBNCC.setImage(null);
			txtNhapSearch.setTooltip(null);
			lvNcc.setVisible(true);
		}

	}

	@FXML
	public void clickOnNccMoi() {
		if (tbNcc.isSelected()) {
			lvNcc.setVisible(false);
			kiemTraNhaCungCap();
		} else {
			if (dsncc.indexOf(txtNhapSearch.getText().trim()) != -1) {
				txtNhapSearch.getStyleClass().remove("error1");
				imgTBNCC.setImage(dung());
				txtNhapSearch.setTooltip(null);
				ncc = true;
			} else {
				imgTBNCC.setImage(sai());
				txtNhapSearch.setTooltip(toolTipValidate("Tên nhà cung cấp không hợp lệ!"));
				txtNhapSearch.getStyleClass().remove("error1");
				txtNhapSearch.getStyleClass().add("error1");
				ncc = false;
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		lvNcc.setVisible(false);
		ConnectDB con = new ConnectDB();
		loadDanhSachPhieuNhap();

		dsncc = con.layDanhSachNCC();
		lvNcc.getItems().addAll(dsncc);

//		FxUtilTest.autoCompleteComboBoxPlus(cbbNCC, (typedText, itemToCompare) -> itemToCompare.toString().toLowerCase().contains(typedText.toLowerCase()));

		BoDauTiengViet bd = new BoDauTiengViet();

		FilteredList<PhieuNhap> filterPhieuNhap = new FilteredList<>(dspn, b -> true);
		txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filterPhieuNhap.setPredicate(kh -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (kh.getTenNv().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(kh.getTenNv()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getTenNCC().toLowerCase().indexOf(lowerCaseFilter) != -1
						|| bd.boDau(kh.getTenNCC()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getMaPn().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (kh.getThoiGianLap().indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false;
			});
		});
		SortedList<PhieuNhap> sortedData = new SortedList<>(filterPhieuNhap);
		sortedData.comparatorProperty().bind(tbPN.comparatorProperty());
		tbPN.setItems(sortedData);

		if (!Main.quyen.equals("QUANLI")) {
			vbCTPN.setDisable(true);
			vbBangPN.setDisable(false);
			cSoLuong.setCellFactory(TextFieldTableCell.forTableColumn());
			cSoLuong.setOnEditCommit(
					(EventHandler<CellEditEvent<Ctpn, String>>) new EventHandler<CellEditEvent<Ctpn, String>>() {
						@Override
						public void handle(CellEditEvent<Ctpn, String> event) {

							String giaTri = event.getNewValue().toString();

							String bieuThucSoThuc = "^(\\d+\\.)?\\d+$";

							if (giaTri.trim().length() == 0) {
								Alert alert1 = new Alert(AlertType.ERROR);
								alert1.setTitle("Thông báo");
								alert1.setHeaderText("Vui lòng nhập số lượng!");
								alert1.showAndWait();
								// return;
							} else if (!giaTri.matches(bieuThucSoThuc)) {
								Alert alert1 = new Alert(AlertType.ERROR);
								alert1.setTitle("Thông báo");
								alert1.setHeaderText("Số lượng không hợp lệ!");
								alert1.showAndWait();
								// return;
							} else {
								Ctpn ct = event.getRowValue();
								ct.setSoLuong(giaTri);
							}

						}
					});
			cDonGia.setCellFactory(TextFieldTableCell.forTableColumn());
			cDonGia.setOnEditCommit(
					(EventHandler<CellEditEvent<Ctpn, String>>) new EventHandler<CellEditEvent<Ctpn, String>>() {
						@Override
						public void handle(CellEditEvent<Ctpn, String> event) {

							String giaTri = event.getNewValue().toString();

							if (giaTri.trim().length() == 0) {
								Alert alert1 = new Alert(AlertType.ERROR);
								alert1.setTitle("Thông báo");
								alert1.setHeaderText("Vui lòng nhập đơn giá!");
								alert1.showAndWait();
								return;
							} else if (!giaTri.trim().matches("\\d{0,15}")) {
								Alert alert1 = new Alert(AlertType.ERROR);
								alert1.setTitle("Thông báo");
								alert1.setHeaderText("Đơn giá không hợp lệ!");
								alert1.showAndWait();
								return;
							} else {
								Ctpn ct = event.getRowValue();
								ct.setDonGia(giaTri);
							}

						}
					});

//			cDonGia.setCellValueFactory(new PropertyValueFactory<Ctpn, Long>("donGia"));
//			cDonGia.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
//			cDonGia.setOnEditCommit(new EventHandler<CellEditEvent<Ctpn, Long>>() {
//				@Override
//				public void handle(CellEditEvent<Ctpn, Long> event) {
//					
//					String giaTri = event.getNewValue().toString();
//
//					if (giaTri.trim().length() == 0) {
//						Alert alert1 = new Alert(AlertType.ERROR);
//						alert1.setTitle("Thông báo");
//						alert1.setHeaderText("Vui lòng nhập đơn giá!");
//						alert1.showAndWait();
//						event.getTableColumn().getStyleClass().add("error");
//			//			return;
//					} else if (!giaTri.trim().matches("\\d{0,15}")) {
//						Alert alert1 = new Alert(AlertType.ERROR);
//						alert1.setTitle("Thông báo");
//						alert1.setHeaderText("Đơn giá không hợp lệ!");
//						alert1.showAndWait();
//						
//			//		return;
//					} else {
//						Ctpn ct = event.getRowValue();
//						
//						ct.setDonGia(giaTri);
//					}
//				}
//			});
		} else {

			btnLapPhieu.setVisible(false);
			tbNguyenLieu.setVisible(false);
			vbThemBot.setVisible(false);
			hbNut.setVisible(true);
			btnThoat.setVisible(false);
			tbNguyenLieu.setPrefHeight(0);
			tbNguyenLieu.setPrefWidth(0);

			btnBot.setPrefHeight(0);
			btnThem.setPrefWidth(0);
			vbThemBot.setPrefHeight(0);
			vbThemBot.setPrefWidth(0);

			lbNcc.setVisible(false);
			lvNcc.setVisible(false);
			tbNcc.setVisible(false);

//			btnLuu.setPrefHeight(0);
//			btnLuu.setPrefWidth(0);
//
//			btnXoa.setPrefHeight(0);
//			btnXoa.setPrefWidth(0);

//			hbNut.setPrefHeight(0);
//			hbNut.setPrefWidth(0);

			tbCTPN.setPrefWidth(460);
			tbCTPN.setPrefHeight(450);
			tbCTPN.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		}

		loadNCC();

	}
}
