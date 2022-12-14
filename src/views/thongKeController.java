package views;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Connect.ChuanHoaDuLieu;
import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.ThongKe;

public class thongKeController implements Initializable {
	@FXML
	private BarChart<String, Number> bcThongKe;

	@FXML
	private NumberAxis yDoanhThu;

	@FXML
	private CategoryAxis xThang;

	@FXML
	private Pane pThongKe;

	@FXML
	private Label lbTieuDe;

	@FXML
	private ComboBox<String> cbbLoaiTg;

//	@FXML
//	private ComboBox<String> cbbDanhMuc;

	@FXML
	private PieChart pcThongKe;

	@FXML
	private JFXButton btnXacNhanTK;

	@FXML
	private TextField txtNgay;

	@FXML
	private ImageView imgTBThoiGian;

	@FXML
	private TableColumn<ThongKe, Integer> cSoLuong;

	@FXML
	private TableColumn<ThongKe, String> cTenDU;

	@FXML
	private TableView<ThongKe> tbCT;

	@FXML
	private VBox tkDoanhThu;

	boolean ngay = false;;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtNgay.setDisable(true);
		ConnectDB con = new ConnectDB();
		int nam = 2022;
		ArrayList<Long> doanhThuNam = con.thongKeDoanhThuNam(nam);
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Doanh thu th??ng");
		for (int i = 1; i < 13; i++) {
			series.getData().add(new XYChart.Data<>("Th??ng " + String.valueOf(i), doanhThuNam.get(i - 1)));
		}
		bcThongKe.getData().add(series);
		bcThongKe.setTitle("Th???ng k?? doanh thu n??m " + String.valueOf(nam));

		tkDoanhThu.setVisible(false);
		cbbLoaiTg.getItems().add("Doanh s??? s???n ph???m - theo ng??y");
		cbbLoaiTg.getItems().add("Doanh s??? s???n ph???m - theo th??ng");
		cbbLoaiTg.getItems().add("Doanh s??? s???n ph???m - theo n??m");
		cbbLoaiTg.getItems().add("Doanh thu b??n h??ng - theo n??m");

//		cbbLoaiTg.getItems().add("Doanh thu");
//		cbbDanhMuc.getItems().add("Theo ng??y");
//		cbbDanhMuc.getItems().add("Theo th??ng");
//		cbbDanhMuc.getItems().add("Theo n??m");
	}

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

	@FXML
	public void kiemTraNgay() {
		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
		String loaiNgay = null;
		String loaiTk = null;
		try {
//			loaiNgay = cbbDanhMuc.getSelectionModel().getSelectedItem();
			loaiTk = cbbLoaiTg.getSelectionModel().getSelectedItem();
		} catch (Exception e) {

		}
		System.out.println(loaiNgay);
		if (loaiTk.equals("Doanh s??? s???n ph???m - theo ng??y")) {

			if (txtNgay.getText().trim().length() == 0) {
				imgTBThoiGian.setImage(sai());
				txtNgay.setTooltip(toolTipValidate("Vui l??ng nh???p ng??y!"));
				txtNgay.getStyleClass().remove("error");
				txtNgay.getStyleClass().add("error");

				ngay = false;
			} else if (!ch.kiemTraDate(ch.chuanHoaChuoi(txtNgay.getText().toString()))) {
				imgTBThoiGian.setImage(sai());
				txtNgay.setTooltip(toolTipValidate("Vui l??ng nh???p ng??y!"));
				txtNgay.getStyleClass().remove("error");
				txtNgay.getStyleClass().add("error");
				ngay = false;
			} else {
				imgTBThoiGian.setImage(dung());
				txtNgay.setTooltip(null);
				txtNgay.getStyleClass().remove("error");
				ngay = true;
			}
		} else if (loaiTk.equals("Doanh s??? s???n ph???m - theo th??ng")) {
			if (txtNgay.getText().trim().length() == 0) {
				imgTBThoiGian.setImage(sai());
				txtNgay.setTooltip(toolTipValidate("Vui l??ng nh???p th??ng n??m!"));
				txtNgay.getStyleClass().remove("error");
				txtNgay.getStyleClass().add("error");

				ngay = false;
			} else if (!txtNgay.getText().matches("(0?[1-9]|1[012])/([0-9]{4})")) {
				imgTBThoiGian.setImage(sai());
				txtNgay.setTooltip(toolTipValidate("Vui l??ng nh???p th??ng kh??ng l???p l???!"));
				txtNgay.getStyleClass().remove("error");
				txtNgay.getStyleClass().add("error");
				ngay = false;
			} else {
				imgTBThoiGian.setImage(dung());
				txtNgay.setTooltip(null);
				txtNgay.getStyleClass().remove("error");
				ngay = true;
			}
		} else if (loaiTk.equals("Doanh s??? s???n ph???m - theo n??m") || loaiTk.equals("Doanh thu b??n h??ng - theo n??m")) {

			if (txtNgay.getText().trim().length() == 0) {
				imgTBThoiGian.setImage(sai());
				txtNgay.setTooltip(toolTipValidate("Vui l??ng nh???p n??m!"));
				txtNgay.getStyleClass().remove("error");
				txtNgay.getStyleClass().add("error");

				ngay = false;
			} else if (!txtNgay.getText().toString().matches("[0-9]{4}")) {
				imgTBThoiGian.setImage(sai());
				txtNgay.setTooltip(toolTipValidate("Vui l??ng nh???p n??m kh??ng l???p l???!"));
				txtNgay.getStyleClass().remove("error");
				txtNgay.getStyleClass().add("error");
				ngay = false;
			} else {
				imgTBThoiGian.setImage(dung());
				txtNgay.setTooltip(null);
				txtNgay.getStyleClass().remove("error");
				ngay = true;
			}
		} else {

		}

	}

	public void loadDanhSachCT() {

	}

	public void setOn() {
		cbbLoaiTg.setDisable(true);
//		cbbDanhMuc.setDisable(true);
		txtNgay.setDisable(true);
		txtNgay.setText("");
		btnXacNhanTK.setDisable(true);
		txtNgay.setPromptText("Nh???p th???i gian");
		imgTBThoiGian.setImage(null);
		ngay = false;
		pThongKe.setVisible(true);
	}

	public void xacNhan(ActionEvent event) {
		String loaiThongKe = null;
		try
		{
			loaiThongKe = cbbLoaiTg.getSelectionModel().getSelectedItem();
		} catch (Exception e) {

		}

		if (loaiThongKe.equals("Doanh s??? s???n ph???m - theo ng??y")) {
			if (ngay) {
				lbTieuDe.setText("Doanh s??? s???n ph???m ng??y " + txtNgay.getText());
				pcThongKe.getData().clear();
				ConnectDB con = new ConnectDB();
				ArrayList<ThongKe> dstk = con.thongKeSLMuaTheoNgay(txtNgay.getText());
				ObservableList<ThongKe> dsachtk = FXCollections.observableArrayList(dstk);

				cTenDU.setCellValueFactory(new PropertyValueFactory<>("tendu"));
				cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soluong"));

				tbCT.setItems(dsachtk);

				for (ThongKe tk : dstk) {
					PieChart.Data DoUong1 = new PieChart.Data(tk.getTendu(), tk.getSoluong());
					pcThongKe.getData().add(DoUong1);
				}
				setOn();

			} else {
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Th??ng b??o");
				alert1.setHeaderText("Ng??y nh???p kh??ng ????ng ?????nh d???ng dd/MM/yyyy");
				alert1.showAndWait();
				return;
			}
		} else if (loaiThongKe.equals("Doanh s??? s???n ph???m - theo th??ng")) {
			if (ngay) {
				lbTieuDe.setText("Doanh s??? s???n ph???m th??ng " + txtNgay.getText());
				pcThongKe.getData().clear();
				ConnectDB con = new ConnectDB();
				ArrayList<ThongKe> dstk = con.thongKeSLMuaTheoThang(txtNgay.getText());
				ObservableList<ThongKe> dsachtk = FXCollections.observableArrayList(dstk);

				cTenDU.setCellValueFactory(new PropertyValueFactory<>("tendu"));
				cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soluong"));

				tbCT.setItems(dsachtk);

				for (ThongKe tk : dstk) {
					PieChart.Data DoUong1 = new PieChart.Data(tk.getTendu(), tk.getSoluong());
					pcThongKe.getData().add(DoUong1);
				}

				setOn();

			} else {

				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Th??ng b??o");
				alert1.setHeaderText("Th??ng n??m kh??ng nh???p ????ng ?????nh d???ng mm/yyyy");
				alert1.showAndWait();
				return;
			}
		} else if (loaiThongKe.equals("Doanh s??? s???n ph???m - theo n??m")) {
			if (ngay) {
				lbTieuDe.setText("Doanh s??? s???n ph???m n??m " + txtNgay.getText());
				pcThongKe.getData().clear();
				ConnectDB con = new ConnectDB();
				ArrayList<ThongKe> dstk = con.thongKeSLMuaTheoNam(txtNgay.getText());
				ObservableList<ThongKe> dsachtk = FXCollections.observableArrayList(dstk);

				cTenDU.setCellValueFactory(new PropertyValueFactory<>("tendu"));
				cSoLuong.setCellValueFactory(new PropertyValueFactory<>("soluong"));

				tbCT.setItems(dsachtk);

				for (ThongKe tk : dstk) {
					PieChart.Data DoUong1 = new PieChart.Data(tk.getTendu(), tk.getSoluong());
					pcThongKe.getData().add(DoUong1);
				}
				setOn();
			} else {
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Th??ng b??o");
				alert1.setHeaderText("N??m kh??ng h???p l???!");
				alert1.showAndWait();
				return;
			}
		} else if (loaiThongKe.equals("Doanh thu b??n h??ng - theo n??m")) {
			tkDoanhThu.setVisible(true);
			ConnectDB con = new ConnectDB();
			if (ngay) {
				int nam = 0;
				try {
					nam = Integer.parseInt(txtNgay.getText());
				} catch (Exception e) {
					
				}
				bcThongKe.getData().clear();
				ArrayList<Long> doanhThuNam = con.thongKeDoanhThuNam(nam);
				XYChart.Series<String, Number> series = new XYChart.Series<>();
				series.setName("Doanh thu th??ng");
				for (int i = 1; i < 13; i++) {
					series.getData().add(new XYChart.Data<>("Th??ng " + String.valueOf(i), doanhThuNam.get(i - 1)));
				}
				bcThongKe.getData().add(series);
				bcThongKe.setTitle("Th???ng k?? doanh thu n??m " + String.valueOf(nam));
				setOn();
				pThongKe.setVisible(false);
			}
			else {
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("Th??ng b??o");
				alert1.setHeaderText("N??m kh??ng h???p l???!");
				alert1.showAndWait();
				return;
			}
		} else {

			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("Th??ng b??o");
			alert1.setHeaderText("Vui l??ng ch???n danh m???c th???ng k??!");
			alert1.showAndWait();
		}
	}

	@FXML
	public void chonDanhMuc() {
		String loaiTk = null;
		try {
			loaiTk = cbbLoaiTg.getSelectionModel().getSelectedItem();
			txtNgay.setDisable(false);
		} catch (Exception e) {

		}
		if (loaiTk.equals("Doanh s??? s???n ph???m - theo ng??y")) {
			txtNgay.setPromptText("Nh???p ng??y dd/MM/yyyy");
		} else if (loaiTk.equals("Doanh s??? s???n ph???m - theo th??ng")) {
			txtNgay.setPromptText("Nh???p th??ng MM/yyyy");
		} else if (loaiTk.equals("Doanh s??? s???n ph???m - theo n??m")||loaiTk.equals("Doanh thu b??n h??ng - theo n??m")) {
			txtNgay.setPromptText("Nh???p n??m yyyy");
		}
	}

	@FXML
	public void lamMoi() {
		txtNgay.setDisable(true);
		pThongKe.setVisible(false);
		tkDoanhThu.setVisible(false);
		pcThongKe.getData().clear();
		lbTieuDe.setText("Th???ng k??");
		cbbLoaiTg.setDisable(false);
//		cbbDanhMuc.setDisable(false);
		txtNgay.setDisable(false);
		txtNgay.setText("");
		btnXacNhanTK.setDisable(false);
		txtNgay.setPromptText("Nh???p th???i gian");
		imgTBThoiGian.setImage(null);
		cbbLoaiTg.getSelectionModel().select(-1);
//		cbbDanhMuc.getSelectionModel().select(null);
	}
}
