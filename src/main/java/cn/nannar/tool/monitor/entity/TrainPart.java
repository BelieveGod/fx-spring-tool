package cn.nannar.tool.monitor.entity;

import cn.nannar.tool.dto.Sqlize;
import lombok.Data;

/**
 * @author LTJ
 * @date 2022/6/11
 */
@Data
public class TrainPart implements Sqlize {
    public static final int WHEEL = 1;
    public static final int BOGIE = 2;
    public static final int AXLE = 3;
    public static final int CARRIAGE = 5;
    public static final int PANTOGRAPH = 6;
    public static final int MOTOR = 8;

    private Integer id;

    private Integer trainNoId;

    private String trainNo;

    private Integer partCode;

    private String partName;

    private String partNameSelf="";

    private Integer status=1;

    private Integer partType;

    @Override
    public String getInsert() {
        String sql = new StringBuilder("INSERT INTO train_parts(id,train_no_id,train_no,part_code,part_name,status,part_type,part_name_self)")
                .append(" VALUES(")
                .append(id).append(",")
                .append(trainNoId).append(",")
                .append("'").append(trainNo).append("'").append(",")
                .append(partCode).append(",")
                .append("'").append(partName).append("'").append(",")
                .append(status).append(",")
                .append(partType).append(",")
                .append("'").append(partNameSelf).append("'")
                .append(");")
                .toString();
        return sql;
    }
}
