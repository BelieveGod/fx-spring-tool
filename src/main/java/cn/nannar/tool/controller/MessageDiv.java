package cn.nannar.tool.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageDiv extends HBox implements Initializable {

    public MessageDiv() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/message-div.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("初始化失败");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
