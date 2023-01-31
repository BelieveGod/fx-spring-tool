package cn.nannar.tool.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author LTJ
 * @date 2023/1/29
 */
@Controller
public class TitleBarController implements Initializable {
    @FXML
    private ImageView minusIv;
    @FXML
    private ImageView expandIv;
    @FXML
    private ImageView closeIv;
    @FXML
    private BorderPane titleBorderpane;

    private double titelBarMousePressedX;
    private double titelBarMousePressedY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minusIv.setCursor(Cursor.DEFAULT);
        expandIv.setCursor(Cursor.DEFAULT);
        closeIv.setCursor(Cursor.DEFAULT);
        minusIv.setPickOnBounds(true);
        expandIv.setPickOnBounds(true);
        closeIv.setPickOnBounds(true);
        titleBorderpane.setCursor(Cursor.DEFAULT);
    }

    @FXML
    public void onClickMinimize(MouseEvent mouseEvent){
        if(mouseEvent.getButton()== MouseButton.PRIMARY){
            Stage stage = (Stage) minusIv.getScene().getWindow();
            stage.setIconified(true);
        }
    }

    @FXML
    public void onEnterMinimize(MouseEvent mouseEvent){
        if (minusIv.getCursor() == Cursor.DEFAULT) {
            minusIv.setImage(new Image("/image/home.png"));
        }
    }



    @FXML
    public void onExitMinimize(MouseEvent mouseEvent){
        if (minusIv.getCursor() == Cursor.DEFAULT) {
            minusIv.setImage(new Image("/image/minus.png"));
        }
    }

    @FXML
    public void onClickMaxmize(MouseEvent mouseEvent){
        if(mouseEvent.getButton()== MouseButton.PRIMARY){
            Stage stage = (Stage) minusIv.getScene().getWindow();
            if(stage.isMaximized()){ // 已经使最大化
                stage.setMaximized(false);
            }else{  // 还没有最大化

                stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
                stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
                stage.setMaximized(true);
            }
        }
    }

    @FXML
    public void onEnterMaximize(MouseEvent mouseEvent){
        if (expandIv.getCursor() == Cursor.DEFAULT) {
            expandIv.setImage(new Image("/image/home.png"));
        }
    }



    @FXML
    public void onExitMaximize(MouseEvent mouseEvent){
        if (expandIv.getCursor() == Cursor.DEFAULT) {
            expandIv.setImage(new Image("/image/fullscreen-expand.png"));
        }
    }

    @FXML
    public void onClickClose(MouseEvent mouseEvent){
        if(mouseEvent.getButton()== MouseButton.PRIMARY){
            Stage stage = (Stage) closeIv.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void onEnterClose(MouseEvent mouseEvent){
        if (closeIv.getCursor() == Cursor.DEFAULT) {
            closeIv.setImage(new Image("/image/home.png"));
        }
    }



    @FXML
    public void onExitClose(MouseEvent mouseEvent){
        if (closeIv.getCursor() == Cursor.DEFAULT) {
            closeIv.setImage(new Image("/image/close.png"));
        }
    }

    @FXML
    public void onTitleBarMousePressed(MouseEvent mouseEvent){
        if(titleBorderpane.getCursor()==Cursor.DEFAULT){
            titelBarMousePressedX = mouseEvent.getX();
            titelBarMousePressedY = mouseEvent.getY();
        }
    }

    @FXML
    public void onTitleBarDrag(MouseEvent mouseEvent){
        if(titleBorderpane.getCursor()==Cursor.DEFAULT){
            double screenX = mouseEvent.getScreenX();
            double screenY = mouseEvent.getScreenY();

            Stage stage = (Stage) titleBorderpane.getScene().getWindow();
            double v1 = screenX - titelBarMousePressedX;
            double v2 = screenY - titelBarMousePressedY;
            stage.setX(v1);
            stage.setY(v2);
        }
    }
}
