package cn.nannar.tool;

import cn.nannar.tool.config.SpringConfig;
import cn.nannar.tool.monitor.entity.TrainLog;
import cn.nannar.tool.monitor.mapper.TrainLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

/**
 * @author LTJ
 * @date 2022/12/21
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        TrainLogMapper trainLogMapper = context.getBean(TrainLogMapper.class);
        if(trainLogMapper==null){
            System.out.println("fail");
        }else{
            System.out.println("success");

            TrainLog trainLog = trainLogMapper.selectById(45);
            System.out.println("trainLog = " + trainLog);
        }


    }
}
