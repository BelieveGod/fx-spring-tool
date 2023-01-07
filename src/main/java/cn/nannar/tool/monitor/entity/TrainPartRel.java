package cn.nannar.tool.monitor.entity;

import cn.nannar.tool.dto.Sqlize;
import lombok.Data;

/**
 * @author LTJ
 * @date 2022/6/11
 */
@Data
public class TrainPartRel implements Sqlize {
    private Integer id;

    private Integer cId;

    private Integer mId;

    @Override
    public String getInsert() {
        String sql = new StringBuilder("INSERT INTO train_parts_rel(c_id,m_id)")
                .append(" VALUES(")
                .append(cId).append(",")
                .append(mId)
                .append(");")
                .toString();
        return sql;
    }
}
