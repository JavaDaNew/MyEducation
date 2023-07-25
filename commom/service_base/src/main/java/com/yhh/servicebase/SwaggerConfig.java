package com.yhh.servicebase;


import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  //配置类
@EnableSwagger2  //swagger注解
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig(){  //sawgger插件
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo webApiInfo(){ //网页信息
        return new ApiInfoBuilder()
                .title("行而上学在线教育平台")
                .description("-by杨欢欢，本页面测试了课程中心微服务接口定功能")
                .version("2.17")
                .contact(new Contact("yhh", "https://www.baidu.com/",
                        "1611795435@qq.com"))
                .build();
    }
}



