package com.riden.datasourceserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: 一个通用的数据源服务
 * @author: ridenLiu@163.com
 * @date:2020/6/29
 */
@SpringBootApplication
@MapperScan("com.riden.datasourceserver.mapper")
public class DatasourceserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceserverApplication.class, args);
    }
}
