package com.oral.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


/**
 * @author tanyongpeng
 * @Date 2021/6/13 20:00
 * @Version 1.0
 *
 * 配置接口文档
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerKnife4jConfig {


    @Bean
    public Docket createRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                // 文档标题
                .title("Spring-boot项目")
                //文档描述
                .description("V2.0接口规范")
                //服务条款URL
                .termsOfServiceUrl("http://127.0.0.1:80/")
                //版本号
                .version("2.0.0")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                //是否开启
                .enable(true)
                .select()
                //设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage("com.oral.controller"))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }


}
