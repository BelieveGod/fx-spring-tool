package cn.nannar.tool.controller;

import cn.nannar.tool.component.ContactDiv;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author LTJ
 * @date 2023/1/30
 */
@Controller
public class ContackListController implements Initializable {
    @FXML
    private VBox contactVbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i=0;i<20;i++){
            ContactDiv contactDiv = new ContactDiv();
            contactVbox.getChildren().add(contactDiv);
        }
    }
}
