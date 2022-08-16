package views;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import Connect.ChuanHoaDuLieu;
import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.NguyenLieu;

public class B_CapNhapNguyenLieu_Controller  implements Initializable {

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
	private ImageView imgTBSoLuong;



	@FXML
	private Label txtDonVi;

	@FXML
	private Label txtTenNL;

	@FXML
	private Label txtMaNL;

	@FXML
	private TextField txtSoLuong;

//Kiểm tra tên nguyên liệu

	
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
		} else {
			Image image = new Image(getClass().getResourceAsStream("/image/QL/icons8_ok_48px.png"));
			txtSoLuong.getStyleClass().remove("error");
			imgTBSoLuong.setImage(image);
			txtSoLuong.setTooltip(null);
			soLuong = true;
		}
	}

//Thêm nguyên liệu mới
	@FXML
	void clickLuuNL(MouseEvent event) {
		if(nl == null) {
			if (tenNL && donVi && soLuong) {
				NguyenLieu nl = new NguyenLieu("", txtTenNL.getText(), txtDonVi.getText(),
						Float.parseFloat(txtSoLuong.getText()));
				ConnectDB con = new ConnectDB();
				if (con.themNguyenLieu(nl)) {
					Stage stage = (Stage) btnLuu.getScene().getWindow();
					stage.setAlwaysOnTop(false);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Thêm nguyên liệu thành công");
					alert.showAndWait();
					stage.close();
				} else {
					Stage stage = (Stage) btnLuu.getScene().getWindow();
					stage.setAlwaysOnTop(false);
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Thêm nguyên liệu thất bại");
					alert.showAndWait();
					stage.setAlwaysOnTop(true);
				}

			} else {
				Stage stage = (Stage) btnLuu.getScene().getWindow();
				stage.setAlwaysOnTop(false);
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Thêm nguyên liệu thất bại");
				alert.setContentText("Vui lòng kiểm tra dữ liệu thêm vào");
				alert.showAndWait();
				stage.setAlwaysOnTop(true);
			}
		}
		else {
			if (txtTenNL.getTooltip() == null && txtDonVi.getTooltip() == null && txtSoLuong.getTooltip() == null) {
				
				ConnectDB con = new ConnectDB();
				ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
				if (con.suaNguyenLieu(txtMaNL.getText(),ch.chuanHoaChuoi(txtTenNL.getText()),ch.chuanHoaChuoi(txtDonVi.getText()),Float.parseFloat(txtSoLuong.getText()))) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Thông báo");
					alert.setHeaderText("Sửa nguyên liệu thành công");
					alert.showAndWait();
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
	public void	chonNguyenLieu(MouseEvent e) {
		
		NguyenLieu nl = tbNguyenLieu.getSelectionModel().getSelectedItem();
		System.out.println(nl.getMaNL());
		if(nl!=null) {
			this.nl = nl;
			txtMaNL.setText(nl.getMaNL());
			txtTenNL.setText(nl.getTenNL());
			txtSoLuong.setText(String.valueOf(nl.getKhoiLuong()));
			txtDonVi.setText(nl.getDonVi());
		}
	}
	
	public void setTrong() {
		imgTBSoLuong.setImage(null);
		nl = null;
		txtMaNL.setText(null);
		txtTenNL.setText(null);
		txtSoLuong.setText(null);
		txtDonVi.setText(null);
	}
	
	@FXML
	public void boTimKiem() {
		
		setTrong();
			
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
		
		
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ConnectDB cn = new ConnectDB();
		dsnl = FXCollections.observableArrayList(cn.layDsnl());
		cMaNL.setCellValueFactory(new PropertyValueFactory<>("maNL"));
		cTenNL.setCellValueFactory(new PropertyValueFactory<>("tenNL"));
		cSoLuong.setCellValueFactory(new PropertyValueFactory<>("khoiLuong"));
		cDonVi.setCellValueFactory(new PropertyValueFactory<>("donVi"));
		tbNguyenLieu.setItems(dsnl);
	}
}
