package com.riden.datasourceserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 //开启Swagger2
public class SwaggerConfig {

    /**
     * 配置Swagger的Docket的bean实例
     */
    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .enable(true)// 是否启用swagger
                .groupName("深田老师组")
                .apiInfo(apiInfo())
                .select()
                // RequestHandlerSelectors. 配置要扫描的接口的方式
                // 1. basePackage():指定要扫描的包
                // 2. any():扫描全部
                // 3. none():不扫描
                // 4. withClassAnnotation:扫描类上的注解,参数是一个注解的反射对象
                // 5. withMethodAnnotation:扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.riden.datasourceserver.controller"))
                // 过滤路径(多种方式),当前配置同意所有
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Riden的数据源项目Api文档",
                "一个数据源定制平台",
                "1.0",
                "http://www.baidu.com",
                new Contact("riden", "", "ridenLiu@163.com"),// 作者信息
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>()
        );
    }

}
