package cn.nannar.tool;

import cn.nannar.tool.common.util.SpringFxmlLoaderFactory;
import cn.nannar.tool.common.util.WindowUtil;
import cn.nannar.tool.config.SpringConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author LTJ
 * @date 2022/12/21
 */
public class FxGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = SpringFxmlLoaderFactory.getFxmlLoader("/fxml/main.fxml");
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);

        WindowUtil.addResizeable(stage,200,200);
        WindowUtil.addShadowStyle(stage);
        WindowUtil.addMinimizeBehavior(stage);
        stage.show();

        WindowUtil.addWindowsPlatformTaskBarIconifyBehavior();
    }

    @Override
    public void init() throws Exception {
        SpringApplication springApplication = new SpringApplication(SpringConfig.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = springApplication.run();
        SpringFxmlLoaderFactory.setCtx(context);

    }

    @Override
    public void stop() throws Exception {
    }

}
