package com.riden.datasourceserver.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.charset.StandardCharsets;

/**
 * 关于: HttpMessageConverter的作用及替换(参见: https://www.jianshu.com/p/333ed5ee958d)
 * <p>
 * 在SpringMVC中，可以使用@RequestBody和@ResponseBody两个注解，分别完成请求报文到对象和对象到响应报文的转换，
 * 底层这种灵活的消息转换机制就是利用HttpMessageConverter来实现的，Spring内置了很多HttpMessageConverter，
 * 用于处理不同的消息类型,至于各种消息解析实现的不同,则在不同的HttpMessageConverter实现类中.
 * 比如MappingJackson2HttpMessageConverter，StringHttpMessageConverter等
 * <p>
 * 在springboot项目里当我们在控制器类上加上@RestController注解或者其内的方法上加入@ResponseBody注解后，
 * 默认会使用jackson插件来返回json数据，下面我们利用FastJson为我们提供的FastJsonHttpMessageConverter来返回json数据
 */

@Configuration
public class HttpMessageConverterConfig {

    /**
     * 引入Fastjson解析json，不使用默认的jackson
     * 必须在pom.xml引入fastjson的jar包，并且版本必须大于1.2.10
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        //1. 定义一个convert转换消息的对象
        FastJsonHttpMessageConverter jsonConverter = new FastJsonHttpMessageConverter();
        //2. 添加fastjson的配置信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        SerializerFeature[] serializerFeatures = new SerializerFeature[]{
                SerializerFeature.QuoteFieldNames,//输出key时是否使用双引号,默认为true
//                SerializerFeature.UseSingleQuotes,//使用单引号而不是双引号,默认为false,
//                SerializerFeature. WriteMapNullValue,//是否输出值为null的字段,默认为false
                SerializerFeature.WriteNullListAsEmpty,//List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullStringAsEmpty,//字符类型字段如果为null,输出为"",而非null
//                SerializerFeature.WriteNullNumberAsZero,//数值字段如果为null,输出为0,而非null
//                SerializerFeature.WriteNullBooleanAsFalse,//Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteDateUseDateFormat,//Date的日期转换器
        };
        fastJsonConfig.setSerializerFeatures(serializerFeatures);
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        //3. 在convert中添加配置信息
        jsonConverter.setFastJsonConfig(fastJsonConfig);
        //4. 将convert添加到converters中
        HttpMessageConverters hms = new HttpMessageConverters(jsonConverter);
        return hms;
    }

}
