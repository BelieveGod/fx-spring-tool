package cn.nannar.tool.controller;

import cn.nannar.tool.common.util.SpringFxmlLoaderFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author LTJ
 * @date 2023/1/14
 */
@Controller
public class MainController implements Initializable {
    @FXML
    private BorderPane mianBorderpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader = SpringFxmlLoaderFactory.getFxmlLoader("/fxml/title-bar.fxml");
        try {
            BorderPane titleBar = fxmlLoader.load();
            mianBorderpane.setTop(titleBar);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
