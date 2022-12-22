package cn.nannar.tool;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author LTJ
 * @date 2022/12/21
 */
public class FxGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);

        stage.show();
    }
}
