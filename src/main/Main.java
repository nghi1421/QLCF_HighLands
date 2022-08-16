package main;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

	private double xOffset = 0;
    private double yOffset = 0;
    public static String maNv = "";
    public static String hoTenNv = "";
    public static String quyen = "";
    public static String tenDangNhap = "";
    public static String matKhau = "";
	@Override
	public void start(Stage stage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
//			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/views/TN_.fxml"));
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initStyle(StageStyle.TRANSPARENT);
	        stage.setMaximized(false);

	        //grab your root here
//	        root.setOnMousePressed(new EventHandler<MouseEvent>() {
//	            @Override
//	            public void handle(MouseEvent event) {
//	                xOffset = event.getSceneX();
//	                yOffset = event.getSceneY();
//	            }
//	        });

	        //sorry about that - Windows defender issue.
	        //move around here
//	        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//	            @Override
//	            public void handle(MouseEvent event) {
//	                stage.setX(event.getScreenX() - xOffset);
//	                stage.setY(event.getScreenY() - yOffset);
//	            }
//	        });
	        
	        Scene scene = new Scene(root);
	        scene.setFill(Color.TRANSPARENT);
	        stage.setScene(scene);
	        stage.show();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		//(String maNv, String hoTen, String cccd, String sdt, String ngaySinh, String gioiTinh, String diaChi,
		//String ngayVaoLam, BigDecimal luong, Boolean trangThaiNghi, String maCV) 
//		NhanVien nv = new NhanVien("","nguyen thanh nghi", "352633495", "0336593650",
//				"20010401", "Nam","97 Man Thien", "20220327",
//				new BigDecimal("80000000"), false, "B");
//		ConnectDB con =  new ConnectDB();
//		con.layChucVu();
		
//		ChuanHoaDuLieu ch = new ChuanHoaDuLieu();
//		System.out.println(ch.kiemTraDate("31/12/2001"));

//		System.out.println(ch.kiemTraSoLuongNL("d"));
//		System.out.println(ch.chuanHoaHoTen("   nguyen thanh nghi    "));
//		List<String> list =  new ArrayList<>();
//		list.add("123");
//		list.add("22");
//		list.add("22");
//		list.add("1");
//		
//		if(list.contains("1")) {
//			System.out.println("Dungs");
//		}
	}
}
