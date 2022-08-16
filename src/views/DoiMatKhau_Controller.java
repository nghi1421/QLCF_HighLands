package views;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Connect.ConnectDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

public class DoiMatKhau_Controller implements Initializable{
	boolean matKhauCu = false;
	boolean matKhauMoi =  false;
	boolean xnMatKhauMoi = false;
	
	@FXML
	private JFXButton btnTroVe;
	
    @FXML
    private JFXButton btnLuu;

    @FXML
    private ImageView imgTBDonVi;

    @FXML
    private ImageView imgTBSoLuong;

    @FXML
    private ImageView imgTBTenNL;

    @FXML
    private Tooltip ttTenNL;

    @FXML
    private PasswordField txtMatKhauCu;

    @FXML
    private PasswordField txtMatKhauMoi;

    @FXML
    private PasswordField txtNhapLaiMatKhau;

    @FXML
    private Label txtTenDangNhap;

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
    void clickTroVeNL(MouseEvent event) {
    	Stage stage = (Stage)btnTroVe.getScene().getWindow();
	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText("Bạn có chắc chắn trở về không?");
		Optional<ButtonType> result = alert.showAndWait();
		if(!result.isPresent() || result.get() != ButtonType.OK) {
			stage.setAlwaysOnTop(true);
		} else {
			stage.close();
		}
    }

    @FXML
    void doiMatKhau(MouseEvent event) {
    	ConnectDB con = new ConnectDB();
    	if(matKhauCu && matKhauMoi && xnMatKhauMoi) {
    		if(!txtMatKhauCu.getText().equals(Main.matKhau)) {

        		Alert alert1 = new Alert(AlertType.ERROR);
    			alert1.setTitle("Thông báo");
    			alert1.setHeaderText("Mật khẩu cũ không đúng");
    			alert1.showAndWait();
    			return;
        	}
    		if(!txtMatKhauMoi.getText().equals(txtNhapLaiMatKhau.getText())){
    			Alert alert1 = new Alert(AlertType.ERROR);
    			alert1.setTitle("Thông báo");
    			alert1.setHeaderText("Nhập lại mật khẩu mới không khớp với mật khẩu mới!");
    			alert1.showAndWait();
    			return;
        	}
        	if(con.doiMatKhau(txtTenDangNhap.getText(), txtMatKhauMoi.getText())) {
        		
        		Alert alert1 = new Alert(AlertType.INFORMATION);
    			alert1.setTitle("Thông báo");
    			alert1.setHeaderText("Đổi mật khẩu thành công!");
    			alert1.showAndWait();
        		Stage stage1 = (Stage)btnTroVe.getScene().getWindow();
        		stage1.close();
        		Stage stage = new Stage();
        		BorderPane root;
        		try {
        			root = (BorderPane) FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        			stage.initStyle(StageStyle.UNDECORATED);
        			stage.initStyle(StageStyle.TRANSPARENT);
        			stage.setMaximized(false);
        			Scene scene = new Scene(root);
        			scene.setFill(Color.TRANSPARENT);
        			stage.setScene(scene);
        			stage.show();
        		} catch (IOException e) {

        		}
        	}
        	else {
        		Alert alert1 = new Alert(AlertType.ERROR);
    			alert1.setTitle("Thông báo");
    			alert1.setHeaderText("Đổi mật khẩu thất bại!");
    			alert1.showAndWait();
    			return;
        	}
        	
    	}
    	else {
    		if(!matKhauCu) {
    			Alert alert1 = new Alert(AlertType.ERROR);
    			alert1.setTitle("Thông báo");
    			alert1.setHeaderText("Mật khẩu cũ không hợp lệ!");
    			alert1.showAndWait();
    			return;
    		}
    		else if(!matKhauMoi){
    			Alert alert1 = new Alert(AlertType.ERROR);
    			alert1.setTitle("Thông báo");
    			alert1.setHeaderText("Mật khẩu am không hợp lệ!");
    			alert1.showAndWait();
    			return;
    		}
    		else if(!xnMatKhauMoi){
    			Alert alert1 = new Alert(AlertType.ERROR);
    			alert1.setTitle("Thông báo");
    			alert1.setHeaderText("Nhập lại mật khẩu mới không hợp lệ!");
    			alert1.showAndWait();
    			return;
    		}
    	}
    	
    }

    @FXML
    void kiemTraMatKhauCu(KeyEvent event) {
		if (txtMatKhauCu.getText().length() == 0) {
			imgTBTenNL.setImage(sai());
			txtMatKhauCu.setTooltip(toolTipValidate("Mật khẩu cũ không thể để trống!"));
			txtMatKhauCu.getStyleClass().remove("error");
			txtMatKhauCu.getStyleClass().add("error");
			matKhauCu= false;
		}else if (txtMatKhauCu.getText().length() <6) {
			imgTBTenNL.setImage(sai());
			txtMatKhauCu.setTooltip(toolTipValidate("Mật khẩu cũ phải có ít nhất 6 kí tự!"));
			txtMatKhauCu.getStyleClass().remove("error");
			txtMatKhauCu.getStyleClass().add("error");
			matKhauCu= false;
		} 
		
		else if (txtMatKhauCu.getText().length() > 50) {
			imgTBTenNL.setImage(sai());
			txtMatKhauCu.setTooltip(toolTipValidate("Mật khẩu cũ không thể vượt quá 50 kí tự!"));
			txtMatKhauCu.getStyleClass().remove("error");
			txtMatKhauCu.getStyleClass().add("error");
			matKhauCu= false;
		} else {
			imgTBTenNL.setImage(null);
			txtMatKhauCu.setTooltip(null);
			txtMatKhauCu.getStyleClass().remove("error");
			matKhauCu= true;
		}
    }

    @FXML
    void kiemTraMatKhauMoi(KeyEvent event) {
    	if (txtMatKhauMoi.getText().length() == 0) {
    		imgTBSoLuong.setImage(sai());
			txtMatKhauMoi.setTooltip(toolTipValidate("Mật khẩu mới không thể để trống!"));
			txtMatKhauMoi.getStyleClass().remove("error");
			txtMatKhauMoi.getStyleClass().add("error");
			matKhauMoi= false;
		}else if (txtMatKhauCu.getText().length() <6) {
			imgTBSoLuong.setImage(sai());
			txtMatKhauMoi.setTooltip(toolTipValidate("Mật khẩu mới phải có ít nhất 6 kí tự!"));
			txtMatKhauMoi.getStyleClass().remove("error");
			txtMatKhauMoi.getStyleClass().add("error");
			matKhauMoi= false;
		} 
		
		else if (txtMatKhauCu.getText().length() > 50) {
			imgTBSoLuong.setImage(sai());
			txtMatKhauMoi.setTooltip(toolTipValidate("Mật khẩu mới không thể vượt quá 50 kí tự!"));
			txtMatKhauMoi.getStyleClass().remove("error");
			txtMatKhauMoi.getStyleClass().add("error");
			matKhauMoi= false;
		} else {
			imgTBSoLuong.setImage(null);
			txtMatKhauMoi.setTooltip(toolTipValidate("Mật khẩu cũ không thể để trống!"));
			txtMatKhauMoi.getStyleClass().remove("error");
			matKhauMoi= true;
		}
    }

    @FXML
    void kiemTraNhapLaiMatKhauMoi(KeyEvent event) {
    	if (txtNhapLaiMatKhau.getText().length() == 0) {
    		imgTBDonVi.setImage(sai());
    		txtNhapLaiMatKhau.setTooltip(toolTipValidate("Mật khẩu mới không thể để trống!"));
    		txtNhapLaiMatKhau.getStyleClass().remove("error");
    		txtNhapLaiMatKhau.getStyleClass().add("error");
			xnMatKhauMoi= false;
		}else if (txtNhapLaiMatKhau.getText().length() <6) {
			imgTBDonVi.setImage(sai());
			txtNhapLaiMatKhau.setTooltip(toolTipValidate("Mật khẩu mới phải có ít nhất 6 kí tự!"));
			txtNhapLaiMatKhau.getStyleClass().remove("error");
			txtNhapLaiMatKhau.getStyleClass().add("error");
			xnMatKhauMoi= false;
		} 
		
		else if (txtNhapLaiMatKhau.getText().length() > 50) {
			imgTBDonVi.setImage(sai());
			txtNhapLaiMatKhau.setTooltip(toolTipValidate("Mật khẩu mới không thể vượt quá 50 kí tự!"));
			txtNhapLaiMatKhau.getStyleClass().remove("error");
			txtNhapLaiMatKhau.getStyleClass().add("error");
			xnMatKhauMoi= false;
		} else {
			imgTBDonVi.setImage(null);
			txtNhapLaiMatKhau.setTooltip(toolTipValidate("Mật khẩu cũ không thể để trống!"));
			txtNhapLaiMatKhau.getStyleClass().remove("error");
			xnMatKhauMoi= true;
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		txtTenDangNhap.setText(Main.tenDangNhap);
		
	}

}

