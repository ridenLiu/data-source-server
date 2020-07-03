package com.riden.datasourceserver;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description: 一个通用的数据源服务
 * @author: ridenLiu@163.com
 * @date:2020/6/29
 */
@SpringBootApplication
@MapperScan("com.riden.datasourceserver.mapper")
@Slf4j
public class DatasourceserverApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(DatasourceserverApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Riden's dataSourceServer is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "druid monitor: \thttp://" + ip + ":" + port + path + "/druid/index.html\n\t" +
                "swagger-ui: \thttp://" + ip + ":" + port + path + "/swagger-ui.html\n\t" +
                "----------------------------------------------------------");
    }
}
