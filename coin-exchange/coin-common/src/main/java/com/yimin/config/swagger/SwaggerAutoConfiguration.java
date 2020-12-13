package com.yimin.config.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 21:01
 *   @Description :
 *
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    private SwaggerProperties swaggerProperties;

    public SwaggerAutoConfiguration(SwaggerProperties swaggerProperties){
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
        // 安全的配置
        docket.securitySchemes(securitySchemes()) // 安全规则
                .securityContexts(securityContexts()); // 安全配置的上下问
        return docket;
    }


    /**
     * 安全上下文环境
     * @return
     */
    private List<SecurityContext> securityContexts() {
        return Arrays.asList(new SecurityContext(
                Arrays.asList(new SecurityReference("Authorization", new AuthorizationScope[]{new AuthorizationScope("global", "accessResource")})),
                PathSelectors.any()
        ));
    }

    /**
     * 安全规则的配置
     * @return
     */
    private List<? extends SecurityScheme> securitySchemes() {
        return Arrays.asList(new ApiKey("Authorization", "Authorization", "Authorization"));
    }

    /**
     * api 的信息简介
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().contact(
                new Contact(swaggerProperties.getName(), swaggerProperties.getUrl(), swaggerProperties.getEmail())
        ).title(swaggerProperties.getTitle()).description(swaggerProperties.getDescription()).version(swaggerProperties.getVersion())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl()).build();
    }


}
