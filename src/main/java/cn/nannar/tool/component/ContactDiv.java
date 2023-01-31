package cn.nannar.tool.component;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author LTJ
 * @date 2023/1/31
 */
public class ContactDiv extends BorderPane implements Initializable {
    public ContactDiv()  {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/contact-div.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("初始化组件失败", e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
