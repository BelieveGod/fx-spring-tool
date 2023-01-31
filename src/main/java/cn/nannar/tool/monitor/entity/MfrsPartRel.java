package cn.nannar.tool.monitor.entity;

import cn.nannar.tool.dto.Sqlize;
import lombok.Data;

/**
 * @author LTJ
 * @date 2023/1/10
 */
@Data
public class MfrsPartRel implements Sqlize {

    private String trainNo;
    private Integer partType;
    private Integer partCode=0;
    private Integer mfrsCode=0;

    @Override
    public String getInsert() {
        String sql = new StringBuilder("INSERT INTO mfrs_part_rel(train_no,part_type,part_code,mfrs_code)")
                .append("VALUES(")
                .append("'").append(trainNo).append("'").append(",")
                .append(partType).append(",")
                .append(partCode).append(",")
                .append(mfrsCode)
                .append(");")
                .toString();
        return sql;
    }
}
