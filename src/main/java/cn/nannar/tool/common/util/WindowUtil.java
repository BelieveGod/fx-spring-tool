package cn.nannar.tool.common.util;

import cn.hutool.core.lang.Assert;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static com.sun.jna.platform.win32.WinUser.GWL_STYLE;

/**
 * @author LTJ
 * @date 2023/1/29
 */
public class WindowUtil {
    private static double mousePressedX;
    private static double mousePressedY;
    private static final int resizeablePixel=9;

    /**
     * 增加窗口阴影
     */

    /**
     * 使窗口的大小可以调整
     */


    /**
     * 使窗口的标题栏可以有相应的移动功能，放大，缩小，关闭功能
     */
    public static void addResizeable(Stage stage,double minWidth,double minHeight){
        Scene scene = stage.getScene();
        Assert.notNull(scene);
        scene.setOnMousePressed(mouseEvent->{
            mousePressedX = mouseEvent.getSceneX();
            mousePressedY = mouseEvent.getSceneY();
        });

        scene.setOnMouseMoved(mouseEvent->{
            double sceneX = mouseEvent.getSceneX();
            double sceneY = mouseEvent.getSceneY();
            if(stage.isMaximized()){
                return;
            }
            if (sceneX < resizeablePixel && sceneY>resizeablePixel && sceneY < scene.getHeight() - resizeablePixel) {
                scene.setCursor(Cursor.W_RESIZE);
            }else if(sceneX>scene.getWidth()-resizeablePixel && sceneY>resizeablePixel && sceneY < scene.getHeight() - resizeablePixel) {
                scene.setCursor(Cursor.E_RESIZE);
            } else if (sceneY < resizeablePixel && sceneX > resizeablePixel && sceneX < scene.getWidth() - resizeablePixel) {
                scene.setCursor(Cursor.N_RESIZE);
            } else if (sceneY > scene.getHeight()-resizeablePixel && sceneX > resizeablePixel && sceneX < scene.getWidth() - resizeablePixel) {
                scene.setCursor(Cursor.S_RESIZE);
            }else if(sceneX < resizeablePixel && sceneY<resizeablePixel){
                scene.setCursor(Cursor.NW_RESIZE);
            }else if(sceneX < resizeablePixel && sceneY> scene.getHeight()- resizeablePixel){
                scene.setCursor(Cursor.SW_RESIZE);
            }else if(sceneX > scene.getWidth()-resizeablePixel && sceneY<resizeablePixel){
                scene.setCursor(Cursor.NE_RESIZE);
            }else if(sceneX > scene.getWidth()-resizeablePixel && sceneY> scene.getHeight()- resizeablePixel){
                scene.setCursor(Cursor.SE_RESIZE);
            }
            else {
                scene.setCursor(Cursor.DEFAULT);

            }
        });

        scene.setOnMouseDragged(mouseEvent->{
            if(scene.getCursor()==Cursor.DEFAULT){
                return;
            }
            double sceneX = mouseEvent.getSceneX();
            double sceneY = mouseEvent.getSceneY();
            double deltaY = sceneY - mousePressedY;
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            double maxX = visualBounds.getMaxX();
            double maxY = visualBounds.getMaxY();
            double screenX = mouseEvent.getScreenX();
            double screenY = mouseEvent.getScreenY();
            if(screenX>maxX-2 || screenY>maxY-2){   // 保留拖拽的空间
                return;
            }
            if(scene.getCursor()==Cursor.W_RESIZE){
                double deltaX =  stage.getX()-screenX;
                if (stage.getWidth() + deltaX >= minWidth) {
                    mousePressedX=sceneX;
                    stage.setWidth(stage.getWidth() + deltaX);
                    stage.setX(screenX);
                }
            }else if(scene.getCursor()==Cursor.E_RESIZE){
                double deltaX =  sceneX-mousePressedX;
                if (stage.getWidth() + deltaX >= minWidth) {
                    mousePressedX=sceneX;
                    stage.setWidth(stage.getWidth() + deltaX);
                }
            }else if(scene.getCursor()==Cursor.N_RESIZE){
                if(stage.getHeight()+deltaY>=minHeight){
                    System.out.println("上方改变长度");
                    mousePressedY = sceneY;
                    stage.setHeight(stage.getHeight()+deltaY);
                    stage.setY(screenY);
                }

            }else if(scene.getCursor()==Cursor.S_RESIZE){
                if(stage.getHeight()+deltaY>=minHeight){
                    System.out.println("下方改变长度");
                    System.out.println("sceneY = " + sceneY);
                    System.out.println("deltaY = " + deltaY);
                    System.out.println("stage.getHeight() = " + stage.getHeight());
                    mousePressedY = sceneY;
                    stage.setHeight(stage.getHeight()+deltaY);
                }
            }else if(scene.getCursor()==Cursor.NW_RESIZE){
                if(stage.getHeight()+deltaY>=minHeight){
                    mousePressedY = sceneY;
                    stage.setHeight(stage.getHeight()+deltaY);
                    stage.setY(screenY);
                }
                double deltaX =  stage.getX()-screenX;
                if (stage.getWidth() + deltaX >= minWidth) {
                    mousePressedX=sceneX;
                    stage.setWidth(stage.getWidth() + deltaX);
                    stage.setX(screenX);
                }

            }else if(scene.getCursor()==Cursor.SW_RESIZE){
                if(stage.getHeight()+deltaY>=minHeight){
                    mousePressedY = sceneY;
                    stage.setHeight(stage.getHeight()+deltaY);
                }
                double deltaX =  stage.getX()-screenX;
                if (stage.getWidth() + deltaX >= minWidth) {
                    mousePressedX=sceneX;
                    stage.setWidth(stage.getWidth() + deltaX);
                    stage.setX(screenX);
                }

            }else if(scene.getCursor()==Cursor.NE_RESIZE){
                if(stage.getHeight()+deltaY>=minHeight){
                    mousePressedY = sceneY;
                    stage.setHeight(stage.getHeight()+deltaY);
                    stage.setY(screenY);
                }
                double deltaX =  sceneX-mousePressedX;
                if (stage.getWidth() + deltaX >= minWidth) {
                    mousePressedX=sceneX;
                    stage.setWidth(stage.getWidth() + deltaX);
                }

            }else if(scene.getCursor()==Cursor.SE_RESIZE){
                if(stage.getHeight()+deltaY>=minHeight){
                    mousePressedY = sceneY;
                    stage.setHeight(stage.getHeight()+deltaY);
                }
                double deltaX =  sceneX-mousePressedX;
                if (stage.getWidth() + deltaX >= minWidth) {
                    mousePressedX=sceneX;
                    stage.setWidth(stage.getWidth() + deltaX);
                }
            }
        });
    }

    /**
     * 下面这段代码是使Windows平台任务栏图标响应单击事件，当stage的initStyle设置成UNDECORATED时，任务栏图标单击无法最小化窗体
     * 参见StackOverflow的提问：https://stackoverflow.com/questions/26972683/javafx-minimizing-undecorated-stage
     **/
    public static void addWindowsPlatformTaskBarIconifyBehavior() {
            long lhwnd = com.sun.glass.ui.Window.getWindows().get(0).getNativeWindow();
            Pointer lpVoid = new Pointer(lhwnd);
            WinDef.HWND hwnd = new WinDef.HWND(lpVoid);
            final User32 user32 = User32.INSTANCE;
            int oldStyle = user32.GetWindowLong(hwnd, GWL_STYLE);
            int newStyle = oldStyle | 0x00020000;//WS_MINIMIZEBOX
            user32.SetWindowLong(hwnd, GWL_STYLE, newStyle);
    }

    public static void addMinimizeBehavior(Stage primaryStage){
        primaryStage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
            //确保Windows平台下,窗体在最大化状态下最小化后，单击任务栏图标显示时占据的屏幕大小是可视化的全屏
            if (primaryStage.isMaximized()) {
                primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
                primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
            }
        });
    }

    public static void addShadowStyle(Stage stage){
        StackPane root = (StackPane) stage.getScene().getRoot();
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!stage.isMaximized()){
                Pane shadowPane = (Pane) root.getChildren().get(1);
                if(newValue){
                    shadowPane.getStyleClass().remove("shadowPaneUnFocused");
                    shadowPane.getStyleClass().add("shadowPaneFocused");
                }else{
                    shadowPane.getStyleClass().remove("shadowPaneFocused");
                    shadowPane.getStyleClass().add("shadowPaneUnFocused");
                }
            }
        });

        BorderPane paddingPane = (BorderPane) root.getChildren().get(0);
        Rectangle rectangle = new Rectangle();
        paddingPane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            if(stage.isMaximized()){
                rectangle.relocate(0, 0);
                rectangle.setWidth(newValue.getWidth());
                rectangle.setHeight(newValue.getHeight());

            }else{
                rectangle.relocate(resizeablePixel, resizeablePixel);
                rectangle.setWidth(newValue.getWidth() - 2 * resizeablePixel);
                rectangle.setHeight(newValue.getHeight() - 2 * resizeablePixel);
            }
            paddingPane.setClip(rectangle);
        });

        Pane shadowPane = createShadowPane(root, resizeablePixel);
        root.getChildren().add(1, shadowPane);

        /**添加最大化最小化的监听器，最大化时移除阴影效果，最小化增加阴影效果*/
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                paddingPane.getStyleClass().add("borderPaneMaximized");
                paddingPane.getStyleClass().remove("borderPaneDefault");
                root.getChildren().remove(shadowPane);

            } else {
                paddingPane.getStyleClass().add("borderPaneDefault");
                paddingPane.getStyleClass().remove("borderPaneMaximized");
                root.getChildren().add(1,shadowPane);

            }
        });
    }

    private static Pane createShadowPane(Pane rootPane, double shadowSize){
        Pane shadowPane = new Pane();
        shadowPane.prefWidthProperty().bind(rootPane.widthProperty());
        shadowPane.prefHeightProperty().bind(rootPane.heightProperty());

        Rectangle innerBounds = new Rectangle();
        Rectangle outerBounds = new Rectangle();

        shadowPane.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            innerBounds.relocate(newBounds.getMinX() + shadowSize, newBounds.getMinY() + shadowSize);
            innerBounds.setWidth(newBounds.getWidth() - shadowSize * 2);
            innerBounds.setHeight(newBounds.getHeight() - shadowSize * 2);
            outerBounds.setWidth(newBounds.getWidth());
            outerBounds.setHeight(newBounds.getHeight());

            Shape clip = Shape.subtract(outerBounds, innerBounds);
            shadowPane.setClip(clip);
        });
        return shadowPane;
    }
}
