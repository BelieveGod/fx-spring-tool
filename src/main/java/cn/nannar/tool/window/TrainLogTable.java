package cn.nannar.tool.window;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.nannar.tool.common.util.SpringContextHolder;
import cn.nannar.tool.monitor.entity.TrainLog;
import cn.nannar.tool.monitor.mapper.TrainLogMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Date;
import java.util.List;

/**
 * @author LTJ
 * @date 2022/12/22
 */
public class TrainLogTable {
    private TableView tableView;

    public Node getTableView() {
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);

        TrainLogMapper trainLogMapper = SpringContextHolder.getBean(TrainLogMapper.class);
        Page<TrainLog> trainLogPage = new Page<>(1, 10);
        trainLogPage = trainLogMapper.selectPage(trainLogPage, null);
        List<TrainLog> records = trainLogPage.getRecords();
        ObservableList<TrainLog> trainLogs = FXCollections.observableList(records);
        tableView = new TableView();


        //  设置tableColumn 将数据对象要展示的列分割出来
        TableColumn<TrainLog, Long> idColumn = new TableColumn<>("id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<TrainLog, String> trainNoColumn = new TableColumn<>("trainNo");
        trainNoColumn.setCellValueFactory(new PropertyValueFactory<>("trainNo"));
        TableColumn<TrainLog, String> traceTimeColumn = new TableColumn<>("traceTime");
        traceTimeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TrainLog, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TrainLog, String> trainLogStringCellDataFeatures) {
                Date traceTime = trainLogStringCellDataFeatures.getValue().getTraceTime();
                String format = DateUtil.format(traceTime, DatePattern.NORM_DATETIME_MINUTE_PATTERN);
                return new ReadOnlyObjectWrapper<>(format);
            }
        });
        tableView.getColumns().setAll(idColumn,trainNoColumn,traceTimeColumn);

        Pagination pagination = new Pagination((int) trainLogPage.getPages(), (int) trainLogPage.getCurrent()-1);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                Page<TrainLog> trainLogPage2 = new Page<>(pageIndex+1, 10);
                trainLogPage2 = trainLogMapper.selectPage(trainLogPage2, null);
                List<TrainLog> records1 = trainLogPage2.getRecords();
                ObservableList<TrainLog> trainLogs1 = FXCollections.observableArrayList(records1);
                tableView.setItems(trainLogs1);
                return tableView;
            }
        });

//        vBox.getChildren().addAll(tableView, pagination);
        return pagination;
    }
}
