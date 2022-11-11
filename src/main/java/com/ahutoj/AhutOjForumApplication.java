package com.ahutoj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true) // 启用AspectJ自动代理
public class AhutOjForumApplication
{
    public static ConfigurableApplicationContext ac;
    public static final Logger log = LogManager.getLogger(AhutOjForumApplication.class);

    //启动类
    public static void main(String[] args)
    {
        ac = SpringApplication.run(AhutOjForumApplication.class, args);
    }
}
