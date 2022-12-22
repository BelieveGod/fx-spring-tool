package cn.nannar.tool;

import cn.nannar.tool.common.GlobalVar;
import cn.nannar.tool.common.util.SpringContextHolder;
import cn.nannar.tool.window.LoginStageBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * @author LTJ
 * @date 2022/12/21
 */
public class FxGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        String[] beanDefinitionNames = SpringContextHolder.getApplicationContext().getBeanDefinitionNames();
        String join = String.join("\n", beanDefinitionNames);

        textArea.setText(join);
        root.getChildren().add(textArea);




        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);

        Stage loginStage = LoginStageBuilder.buildLoginStage();
        loginStage.showAndWait();

        if(!GlobalVar.loginFlag){
            System.exit(2);
        }
        stage.show();
    }
}
