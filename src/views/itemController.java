package views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.MyListener;
import model.DrinkForOrdersViews;

public class itemController {
	@FXML
    private ImageView imgDrink;

    @FXML
    private Label lbName;

    @FXML
    private Label lbPrice;

    @FXML
    private Label lbSLCon;
    
    @FXML
    private Label lbGiamGia;
    
    @FXML
    private void click(MouseEvent event) {
    	myListener.onClickListener(drink);
    }
    
    
    private DrinkForOrdersViews drink =  new DrinkForOrdersViews();
    private MyListener myListener;
    
	public void setData(DrinkForOrdersViews d, MyListener m) {
		drink = d;
		myListener = m;
		lbName.setText(drink.getTenDoUong());
		lbPrice.setText(String.valueOf(drink.getGiaBan())+" VND");
		Image image = new Image(getClass().getResourceAsStream(drink.getSourceAnh())); 
		imgDrink.setImage(image);
		lbSLCon.setText(String.valueOf(drink.getSlCoTheThucHien()));
		if(d.getGiamGia()!=0)
			lbGiamGia.setText(String.valueOf(d.getGiamGia()*100)+"%");
	}
}
