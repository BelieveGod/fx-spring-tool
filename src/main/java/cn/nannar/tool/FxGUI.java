package cn.nannar.tool;

import cn.nannar.tool.common.GlobalVar;
import cn.nannar.tool.common.util.SpringContextHolder;
import cn.nannar.tool.window.LoginStageBuilder;
import cn.nannar.tool.window.TrainInfoUI;
import cn.nannar.tool.window.TrainLogTable;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
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
        stage.setTitle("车号生成辅助工具");

        Scene trainInfoUI = new TrainInfoUI().createTrainInfoUI();
        stage.setScene(trainInfoUI);

        Stage loginStage = LoginStageBuilder.buildLoginStage();
        loginStage.showAndWait();

        if(!GlobalVar.loginFlag){
            System.exit(2);
        }
        stage.show();
    }
}
