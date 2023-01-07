package cn.nannar.tool.monitor.entity;

import cn.nannar.tool.dto.Sqlize;
import lombok.Data;

/**
 * @author LTJ
 * @date 2023/1/7
 */
@Data
public class TrainInfo implements Sqlize {
    private Integer id;

    private String trainNo;

    private Integer groupCount;

    private Integer status=1;
    private Integer pantographCount;

    /***
     *每节车厢的轮子数量(默认每节车厢8个轮子2个转向架)
     */
    private Integer perWheelCount=8;


    @Override
    public String getInsert() {
        String sql = new StringBuilder("INSERT INTO train_info(id,train_no,group_count,status,pantograph_count,per_wheel_count)")
                .append(" VALUES(")
                .append(id).append(",")
                .append("'").append(trainNo).append("'").append(",")
                .append(groupCount).append(",")
                .append(status).append(",")
                .append(pantographCount).append(",")
                .append(perWheelCount)
                .append(");")
                .toString();
        return sql;
    }
}
