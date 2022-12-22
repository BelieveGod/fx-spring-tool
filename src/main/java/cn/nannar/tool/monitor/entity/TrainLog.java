package cn.nannar.tool.monitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author LTJ
 * @date 2022/12/22
 */
@Data
public class TrainLog {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField
    private String trainNo;

    @TableField
    private Date traceTime;
}
