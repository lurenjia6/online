package com.online.edu.statistics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2生成api文档
 * 1.添加依赖
 * 2.编写配置类
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket webApiConfig() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .build();
    }


    private ApiInfo webApiInfo() {//文档中的信息

        return new ApiInfoBuilder()
                .title("网站-阿里云视频上传API文档")
                .description("本文档描述了阿里云视频上传微服务接口定义")
                .version("1.0")
                .contact(new Contact("jack", "http://atguigu.com", "918589859@qq.com"))
                .build();
    }
}
