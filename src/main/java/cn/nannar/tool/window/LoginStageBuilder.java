package cn.nannar.tool.window;

import cn.nannar.tool.common.GlobalVar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author LTJ
 * @date 2022/12/22
 */
public class LoginStageBuilder{
    public static Stage buildLoginStage(){
        Stage stage = new Stage();
        StackPane stackPane = new StackPane();
        VBox vBox = new VBox(15);
        HBox hBox = createField("账户");
        HBox hBox1 = createField("密码");
        Button button = new Button("登录");
        button.setOnAction(event -> {
            GlobalVar.loginFlag=true;
            stage.close();
        });

        vBox.getChildren().addAll(hBox, hBox1,button);
        vBox.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(vBox);
        Scene scene = new Scene(stackPane, 400, 300);
        stage.setScene(scene);
        stage.setTitle("登录窗口");
        return stage;
    }

    private static HBox createField(String labelName){
        HBox hBox = new HBox();
        Label label = new Label(labelName);
        TextField textField = new TextField();
        textField.setPrefWidth(100);
        label.setLabelFor(textField);
        hBox.getChildren().addAll(label, textField);
        hBox.setAlignment(Pos.CENTER);
        HBox.setMargin(textField, new Insets(0, 0, 0, 30));
        return hBox;
    }
}
