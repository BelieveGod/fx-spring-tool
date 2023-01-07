package cn.nannar.tool.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author LTJ
 * @date 2022/12/22
 */
@Configuration
@ComponentScan("cn.nannar.tool")

@EnableAutoConfiguration
public class SpringConfig {

}
