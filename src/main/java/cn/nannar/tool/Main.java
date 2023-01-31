package cn.nannar.tool;

import cn.hutool.core.collection.CollUtil;
import cn.nannar.tool.config.SpringConfig;
import cn.nannar.tool.monitor.entity.TrainLog;
import cn.nannar.tool.monitor.mapper.TrainLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LTJ
 * @date 2022/12/21
 */
public class Main {
    public static void main(String[] args) {
//        SpringApplication springApplication = new SpringApplication(SpringConfig.class);
//        springApplication.setWebApplicationType(WebApplicationType.NONE);
//        ConfigurableApplicationContext context = springApplication.run(args);
//        TrainLogMapper trainLogMapper = context.getBean(TrainLogMapper.class);
//        if(trainLogMapper==null){
//            System.out.println("fail");
//        }else{
//            System.out.println("success");
//        }

        Application.launch(FxGUI.class,args);




    }
}
