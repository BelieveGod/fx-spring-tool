package cn.nannar.tool;

import javafx.application.Application;

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
