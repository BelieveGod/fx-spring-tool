package cn.nannar.tool.window;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.nannar.tool.dto.TrainGenerateDTO;
import cn.nannar.tool.monitor.entity.TrainInfo;
import cn.nannar.tool.monitor.entity.TrainPart;
import cn.nannar.tool.monitor.entity.TrainPartRel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author LTJ
 * @date 2023/1/4
 */
@Slf4j
public class TrainInfoUI {
    static final String lineSperator = System.getProperty("line.separator");
    Pattern pattern = Pattern.compile("%\\d*d");
    private Button trainNoChooseBtn;
    private Button generateBtn;
    private Button addBtn;
    private TextField trainNoFileField;
    private File trainNoFile;

    private FileChooser fileChooser;
    private TextField carriageNumField;
    private VBox styleVbox;
    private TextField trainNoPatternField;
    private TextField ptgNumField;
    private TextField trainBeginField;
    private TextField trainEndField;
    private List<PartTypeInfo> partTypeInfoList;

    private AtomicInteger trainInfoIdGenerator = new AtomicInteger(0);
    private AtomicInteger trainPartIdGenerator = new AtomicInteger(0);

    private Alert commonAlert = new Alert(Alert.AlertType.WARNING);
    private ProgressIndicator progressIndicator = new ProgressIndicator();
    Dialog<String> stringDialog = new Dialog<>();
    TextArea textArea = new TextArea();
    private FlowPane groupFlowPane;
    private StackPane stackPane;

    {
        stringDialog.setHeight(400);
        stringDialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        textArea.setEditable(false);
        stringDialog.getDialogPane().setContent(textArea);
    }

    List<TrainGenerateDTO> trainGenerateDTOList = new LinkedList<>();

    @Data
    public static class PartTypeInfo{
        private Integer partType;
        private String partTypeName;
        private Node nodeInScroll;
        private File styleFile;
        private Supplier<Integer> baseNumSupplier;
        private Integer multiple;
        private CheckBox checkBox;
        private String defaultStyleLocation;
    }



    public Scene createTrainInfoUI(){
        Region region = initSceneTree();
        initEventHandler();
        Scene scene = new Scene(region,800,600);
        scene.getStylesheets().add("/css/style.css");
        initDefaultValue();
        return scene;
    }

    private void initDefaultValue(){
        trainNoPatternField.setText("02%03d");
        carriageNumField.setText("6");
        ptgNumField.setText("2");
        trainBeginField.setText("1");
        trainEndField.setText("100");
        for (PartTypeInfo partTypeInfo : partTypeInfoList) {
            partTypeInfo.checkBox.setSelected(true);
        }
    }

    private Region initSceneTree(){
        fileChooser = new FileChooser();
        partTypeInfoList = new ArrayList<>(6);
        PartTypeInfo[] partTypeInfos = new PartTypeInfo[6];
        partTypeInfos[0] = new PartTypeInfo();
        partTypeInfos[0].setPartTypeName("轮子");
        partTypeInfos[0].setPartType(TrainPart.WHEEL);
        partTypeInfos[0].setMultiple(8);
        partTypeInfos[0].setBaseNumSupplier(() ->Convert.toInt(carriageNumField.getText()) );
        partTypeInfos[0].setDefaultStyleLocation("wheelNameMapping.properties");

        partTypeInfos[1] = new PartTypeInfo();
        partTypeInfos[1].setPartTypeName("车轴");
        partTypeInfos[1].setPartType(TrainPart.AXLE);
        partTypeInfos[1].setMultiple(4);
        partTypeInfos[1].setBaseNumSupplier(() ->Convert.toInt(carriageNumField.getText()) );
        partTypeInfos[1].setDefaultStyleLocation("axleNameMapping.properties");

        partTypeInfos[2] = new PartTypeInfo();
        partTypeInfos[2].setPartTypeName("转向架");
        partTypeInfos[2].setPartType(TrainPart.BOGIE);
        partTypeInfos[2].setMultiple(2);
        partTypeInfos[2].setBaseNumSupplier(() ->Convert.toInt(carriageNumField.getText()) );
        partTypeInfos[2].setDefaultStyleLocation("bogieNameMapping.properties");

        partTypeInfos[3] = new PartTypeInfo();
        partTypeInfos[3].setPartTypeName("车厢");
        partTypeInfos[3].setPartType(TrainPart.CARRIAGE);
        partTypeInfos[3].setMultiple(1);
        partTypeInfos[3].setBaseNumSupplier(() ->Convert.toInt(carriageNumField.getText()) );
        partTypeInfos[3].setDefaultStyleLocation("carriageNameMapping.properties");

        partTypeInfos[4] = new PartTypeInfo();
        partTypeInfos[4].setPartTypeName("受电弓");
        partTypeInfos[4].setPartType(TrainPart.PANTOGRAPH);
        partTypeInfos[4].setMultiple(1);
        partTypeInfos[4].setBaseNumSupplier(() ->Convert.toInt(ptgNumField.getText()) );
        partTypeInfos[4].setDefaultStyleLocation("pantographMapping.properties");

        partTypeInfos[5] = new PartTypeInfo();
        partTypeInfos[5].setPartTypeName("电机");
        partTypeInfos[5].setPartType(TrainPart.MOTOR);
        partTypeInfos[5].setMultiple(4);
        partTypeInfoList.addAll(Arrays.asList(partTypeInfos));
        partTypeInfos[5].setBaseNumSupplier(() ->Convert.toInt(carriageNumField.getText())-2 );
        partTypeInfos[5].setDefaultStyleLocation("motorNameMapping.properties");

        stackPane = new StackPane();
        BorderPane borderPane = new BorderPane();
        stackPane.getChildren().add(borderPane);
        VBox vBox = new VBox();
        vBox.setId("vBox");
        Text text1 = new Text("要生成的编组");

        groupFlowPane = new FlowPane(15,5);

        generateBtn = new Button("生成脚本");
        vBox.getChildren().addAll(text1, groupFlowPane, generateBtn);
        VBox.setMargin(generateBtn, new Insets(20, 0, 10, 0));
        borderPane.setTop(vBox);

        // 下半部分
        VBox vBox1 = new VBox();
        vBox1.setId("vBox1");
        borderPane.setCenter(vBox1);

        /* begin ==========输入部分============= */
        VBox vBox2 = new VBox();
        vBox2.setId("vBox2");
        HBox hBox1 = new HBox();
        Label label = new Label("车号格式");
        trainNoPatternField = new TextField();
        hBox1.getChildren().addAll(label, trainNoPatternField);
        HBox.setMargin(trainNoPatternField, new Insets(0, 0, 0, 10));

        HBox hBox2 = new HBox();
        Label label1 = new Label("车厢数");
        carriageNumField = new TextField();
        hBox2.getChildren().addAll(label1, carriageNumField);
        HBox.setMargin(carriageNumField, new Insets(0, 0, 0, 10));

        HBox hBox3 = new HBox();
        Label label2 = new Label("受电弓数");
        ptgNumField = new TextField();
        hBox3.getChildren().addAll(label2, ptgNumField);
        HBox.setMargin(ptgNumField, new Insets(0, 0, 0, 10));

        HBox hBox4 = new HBox();
        Label label3 = new Label("车号范围");
        trainBeginField = new TextField();
        Text text = new Text("——");
        trainEndField = new TextField();
        hBox4.getChildren().addAll(label3, trainBeginField,text, trainEndField);
        HBox.setMargin(trainBeginField, new Insets(0, 0, 0, 10));

        HBox hBox5 = new HBox();
        Label label4 = new Label("自定义车号");
        trainNoFileField = new TextField();
        trainNoChooseBtn = new Button("选择文件");
        hBox5.getChildren().addAll(label4, trainNoFileField, trainNoChooseBtn);
        HBox.setMargin(trainNoFileField, new Insets(0, 0, 0, 10));

        vBox2.getChildren().addAll(hBox1, hBox2, hBox3, hBox4, hBox5);
        /* end ============输入部分============ */

        /* begin ==========多选框============= */
        HBox hBox = new HBox();
        Label label5 = new Label("要生成的部位类型");
        for (PartTypeInfo partTypeInfo : partTypeInfoList) {
            CheckBox checkBox = new CheckBox(partTypeInfo.getPartTypeName());
            partTypeInfo.setCheckBox(checkBox);
        }

        hBox.getChildren().addAll(label5);
        hBox.getChildren().addAll(partTypeInfoList.stream().map(PartTypeInfo::getCheckBox).collect(Collectors.toList()));
        hBox.setSpacing(10);
        HBox.setMargin(partTypeInfoList.get(0).getCheckBox(),new Insets(0, 0, 0, 10));
        /* end ============多选框============ */

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setId("style-scroll-pane");
        styleVbox = new VBox();
        scrollPane.setContent(styleVbox);

        HBox hBox6 = new HBox();
        addBtn = new Button("添加到编组设置");
        hBox6.getChildren().addAll(addBtn);
        hBox6.setAlignment(Pos.CENTER);

        vBox1.getChildren().addAll(vBox2, hBox, scrollPane,hBox6);
        VBox.setMargin(hBox, new Insets(15, 0, 0, 0));
        VBox.setMargin(scrollPane, new Insets(15, 0, 0, 0));
        VBox.setMargin(hBox6, new Insets(15, 0, 0, 0));
        return stackPane;
    }

    private void initEventHandler(){
        trainNoChooseBtn.setOnAction(event -> {
            fileChooser.setTitle("选择自定义的车号文件");
            File file = fileChooser.showOpenDialog(trainNoChooseBtn.getScene().getWindow());
            if(file==null){
                trainNoFile=null;
                trainNoFileField.setText(null);

            }else{
                trainNoFileField.setText(file.getAbsolutePath());
                trainNoFile=file;
            }
        });

        for (PartTypeInfo partTypeInfo : partTypeInfoList) {
            partTypeInfo.checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                        if(styleVbox.getChildren().contains(partTypeInfo.nodeInScroll)){
                            styleVbox.getChildren().remove(partTypeInfo.nodeInScroll);
                        }
                        partTypeInfo.nodeInScroll = createNodeInScroll(partTypeInfo);
                        styleVbox.getChildren().add(partTypeInfo.nodeInScroll);
                    }else{
                        if(partTypeInfo.nodeInScroll!=null){
                            styleVbox.getChildren().remove(partTypeInfo.nodeInScroll);
                        }
                    }
                }
            });
        }


        addBtn.setOnAction(event -> {
            CompletableFuture.runAsync(() -> {
                Integer trainBegin = Convert.toInt(trainBeginField.getText());
                Integer trainEnd = Convert.toInt(trainEndField.getText());
                String groupName = StrUtil.format("[{}]-[{}]列车",trainBegin, trainEnd);
                boolean hasRepeat=false;
                for (TrainGenerateDTO trainGenerateDTO : trainGenerateDTOList) {
                    Integer trainNumBegin = trainGenerateDTO.getTrainNumBegin();
                    Integer trainNumEnd = trainGenerateDTO.getTrainNumEnd();
                    if( !(trainBegin.compareTo(trainNumEnd)>0 || trainEnd.compareTo(trainNumBegin)<0)){ // 有交集
                        hasRepeat=true;
                        break;
                    }
                }
                if(hasRepeat){
                    Platform.runLater(()->{
                        commonAlert.setContentText("车号序列有重复");
                        commonAlert.show();
                    });
                    return;
                }

                TrainGenerateDTO trainGenerateDTO = new TrainGenerateDTO();
                if(trainNoFile==null){
                    trainGenerateDTO.setUseTrainNoFile(false);
                    String text = trainNoPatternField.getText();
                    trainGenerateDTO.setTrainNoPattern(text);

                }else{
                    trainGenerateDTO.setUseTrainNoFile(true);
                    trainGenerateDTO.setCustomTrainNoFile(trainNoFile);
                }
                Integer carriageNum = Convert.toInt(carriageNumField.getText());
                trainGenerateDTO.setCarriageNum(carriageNum);

                Integer ptgNum = Convert.toInt(ptgNumField.getText());
                trainGenerateDTO.setPtgNum(ptgNum);


                trainGenerateDTO.setTrainNumBegin(trainBegin);
                trainGenerateDTO.setTrainNumEnd(trainEnd);

                // 部位类型信息
                List<TrainGenerateDTO.PartTypeDTO> partTypeDTOList = new LinkedList<>();
                for (PartTypeInfo partTypeInfo : partTypeInfoList) {
                    if (partTypeInfo.getCheckBox().isSelected()) {
                        TrainGenerateDTO.PartTypeDTO partTypeDTO = new TrainGenerateDTO.PartTypeDTO();
                        partTypeDTO.setPartType(partTypeInfo.getPartType());
                        File styleFile = partTypeInfo.getStyleFile();
                        if(styleFile!=null){    // 如果是自定义的风格文件
                            try {
                                Map<Integer, String> map = readPartTypeProperties(styleFile);
                                partTypeDTO.setPartNameMap(map);
                            } catch (IOException e) {
                                String errorInfo = StrUtil.format("读取{}的风格文件：{}失败", partTypeInfo.getPartTypeName(), styleFile.getAbsolutePath());
                                Platform.runLater(()->{
                                    commonAlert.setContentText(errorInfo);
                                    commonAlert.show();
                                });
                                return;
                            }
                        }else{  // 如果是系统默认的风格文件
                            String location = StrUtil.format("group{}/{}", carriageNum, partTypeInfo.getDefaultStyleLocation());
                            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(location);
                            try {
                                Map<Integer, String> map = readPartTypeProperties(resourceAsStream);
                                partTypeDTO.setPartNameMap(map);
                            } catch (IOException e) {
                                String errorInfo = StrUtil.format("读取{}的风格文件：{}失败", partTypeInfo.getPartTypeName(), "系统默认");
                                Platform.runLater(()->{
                                    commonAlert.setContentText(errorInfo);
                                    commonAlert.show();
                                });
                                return;
                            }
                        }
                        partTypeDTOList.add(partTypeDTO);
                    }
                }
                trainGenerateDTO.setPartTypeDTOList(partTypeDTOList);
                trainGenerateDTOList.add(trainGenerateDTO);
                // 界面添加
                Platform.runLater(()->{
                    Text text = new Text(groupName);
                    groupFlowPane.getChildren().add(text);
                });
            });
        });

        generateBtn.setOnAction(event -> {
            fileChooser.setTitle("选择输出位置");
            fileChooser.setInitialFileName("trainAuto.sql");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("SQL文件", ".sql"));
            File file = fileChooser.showSaveDialog(generateBtn.getScene().getWindow());
            if(file!=null){
                stackPane.getChildren().add(progressIndicator);
                CompletableFuture.runAsync(() -> {
                    try {
                        outputSql(file);
                    } catch (Exception e) {
                        log.error("导出错误",e);
                    }
                    Platform.runLater(()->{
                        stackPane.getChildren().remove(progressIndicator);
                    });
                });
            }

        });
    }

    private void initCss(){

    }

    private Node createNodeInScroll(PartTypeInfo partTypeInfo){
        HBox hBox = new HBox();
        hBox.setSpacing(30);
        int unitNum = partTypeInfo.getBaseNumSupplier().get() * partTypeInfo.getMultiple();
        Text text = new Text(StrUtil.format("{}：{} 个", partTypeInfo.getPartTypeName(),unitNum));
        String wheelStyleName = "默认";
        Text text2 = new Text(StrUtil.format("命名风格：{}", wheelStyleName));
        Button button = new Button("设置命名风格");
        Button button1 = new Button("预览命名风格");
        hBox.getChildren().addAll(text, text2, button, button1);

        button.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(button.getScene().getWindow());
            if(file!=null){
                partTypeInfo.styleFile=file;
                text2.setText(StrUtil.format("命名风格：{}", file.getName()));
            }
        });

        // 车厢数
        Integer carriageNum = Convert.toInt(carriageNumField.getText());
        button1.setOnAction(event -> {
            if(partTypeInfo.styleFile!=null){
                String s = FileUtil.readUtf8String(partTypeInfo.styleFile);
                textArea.setText(s);
                stringDialog.show();
            }else{
                String location = StrUtil.format("group{}/{}", carriageNum, partTypeInfo.getDefaultStyleLocation());
                InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(location);
                String s = IoUtil.readUtf8(resourceAsStream);
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                textArea.setText(s);
                stringDialog.show();
            }
        });
        return hBox;
    }


    public static Map<Integer,String> readPartTypeProperties(File file) throws IOException {
        Map<Integer,String> map=Collections.EMPTY_MAP;
        try(FileInputStream fileInputStream = new FileInputStream(file)){
            map=readPartTypeProperties(fileInputStream);
        }
        return map;
    }

    public static Map<Integer,String> readPartTypeProperties(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        properties.load(inputStreamReader);
        Map<Integer, String> integerStringHashMap = new TreeMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            Integer partCode = Convert.toInt(entry.getKey());
            String partName = Convert.toStr(entry.getValue());
            integerStringHashMap.put(partCode, partName);
        }
        return integerStringHashMap;
    }

    private void outputSql(File file){
        trainInfoIdGenerator.set(0);
        trainPartIdGenerator.set(0);
        List<TrainInfo> trainInfoList = new LinkedList<>();
        List<TrainPart> trainPartList = new LinkedList<>();
        List<TrainPartRel> trainPartRelList = new LinkedList<>();
        for (TrainGenerateDTO trainGenerateDTO : trainGenerateDTOList) {
            if (!trainGenerateDTO.getUseTrainNoFile()) {
                String trainNoPattern = trainGenerateDTO.getTrainNoPattern();
                Matcher matcher = pattern.matcher(trainNoPattern);
                matcher.find();
                int groupCount = matcher.groupCount();
                log.info("match {}：{}个", pattern.pattern(), groupCount);
                Integer trainNumBegin = trainGenerateDTO.getTrainNumBegin();
                Integer trainNumEnd = trainGenerateDTO.getTrainNumEnd();

                for(int i=trainNumBegin;i<=trainNumEnd;i++){
                    String trainNo = String.format(trainNoPattern, i);
                    TrainInfo trainInfo = new TrainInfo();
                    trainInfo.setId(trainInfoIdGenerator.incrementAndGet());
                    trainInfo.setTrainNo(trainNo);
                    trainInfo.setGroupCount(trainGenerateDTO.getCarriageNum());
                    trainInfo.setPantographCount(trainGenerateDTO.getPtgNum());
                    trainInfoList.add(trainInfo);

                    List<TrainGenerateDTO.PartTypeDTO> partTypeDTOList = trainGenerateDTO.getPartTypeDTOList();
                    Map<Integer, Map<Integer, String>> partTypeMap = partTypeDTOList.stream().collect(Collectors.toMap(TrainGenerateDTO.PartTypeDTO::getPartType, TrainGenerateDTO.PartTypeDTO::getPartNameMap));
                    boolean hasMotorRel=false;
                    if (partTypeMap.containsKey(TrainPart.CARRIAGE) &&partTypeMap.containsKey(TrainPart.MOTOR)) {
                        hasMotorRel=true;
                    }

                    Map<String, TrainPartRel> partRelMap = new HashMap<>();
                    if(hasMotorRel){

                        Map<Integer, String> motorPartNameMap = partTypeMap.get(TrainPart.MOTOR);
                        for(int j=1;j<=motorPartNameMap.size();j++){
                            TrainPartRel trainPartRel = new TrainPartRel();
                            trainPartRelList.add(trainPartRel);
                            int carriageByMotor = getCarriageByMotor(j);
                            partRelMap.put(StrUtil.format("{}_{}_{}", trainNo, carriageByMotor, j), trainPartRel);
                        }
                    }

                    for (TrainGenerateDTO.PartTypeDTO partTypeDTO : partTypeDTOList) {
                        Map<Integer, String> partNameMap = partTypeDTO.getPartNameMap();

                        // 定位容器 ，trainno_carriage_motor
                        for (Map.Entry<Integer, String> entry : partNameMap.entrySet()) {
                            TrainPart trainPart = new TrainPart();
                            trainPart.setId(trainPartIdGenerator.incrementAndGet());
                            trainPart.setTrainNo(trainNo);
                            trainPart.setTrainNoId(trainInfo.getId());
                            trainPart.setPartType(partTypeDTO.getPartType());
                            trainPart.setPartCode(entry.getKey());
                            trainPart.setPartName(entry.getValue());
                            trainPartList.add(trainPart);
                            if(hasMotorRel){
                                if(partTypeDTO.getPartType().equals(TrainPart.CARRIAGE)){
                                    List<Integer> motorCodeList = getmotorByCarriage(entry.getKey(),partNameMap.size());
                                    if(CollUtil.isNotEmpty(motorCodeList)){
                                        for (Integer motorCode : motorCodeList) {
                                            TrainPartRel trainPartRel = partRelMap.get(StrUtil.format("{}_{}_{}", trainNo, entry.getKey(), motorCode));
                                            if(trainPartRel!=null){
                                                trainPartRel.setCId(trainPart.getId());
                                            }
                                        }
                                    }
                                }else if(partTypeDTO.getPartType().equals(TrainPart.MOTOR)){
                                    int carriageCode = getCarriageByMotor(entry.getKey());
                                    TrainPartRel trainPartRel = partRelMap.get(StrUtil.format("{}_{}_{}", trainNo, carriageCode, entry.getKey()));
                                    if(trainPartRel!=null){
                                        trainPartRel.setMId(trainPart.getId());
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        log.info("输出脚本");
        FileUtil.del(file);
        for (TrainInfo trainInfo : trainInfoList) {
            String insert = trainInfo.getInsert();
            FileUtil.appendUtf8String(insert + lineSperator,file);
        }
        for (TrainPart trainPart : trainPartList) {
            String insert = trainPart.getInsert();
            FileUtil.appendUtf8String(insert + lineSperator,file);
        }

        for (TrainPartRel trainPartRel : trainPartRelList) {
            String insert =trainPartRel.getInsert();
            FileUtil.appendUtf8String(insert + lineSperator,file);
        }
    }


    private static int getCarriageByMotor(int motorCode){
        int i = (motorCode - 1) / 4 + 2;
        return i;
    }

    private static List<Integer> getmotorByCarriage(int carriageCode, int carriageTotal) {
        if(carriageCode<2 || carriageCode>carriageTotal-1){
            return null;
        }
        List<Integer> motorList = new LinkedList<>();
        for(int i=0;i<4;i++){
            int i1 = (carriageCode - 2) * 4 + i+1;
            motorList.add(i1);
        }
        return motorList;
    }
}
