package com.andersenlab.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;


/**Класс представляет собой конфигурацию Swagger2
 @author Артемьев Р.А.
 @version 11.03.2020 */
@Configuration
/*Аннотация @EnableSwagger2 включает поддержку Swagger в приложении*/
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket productApi() {
        /*Экземпляр Springfox Docket предоставляет первичную конфигурацию API с настройками
         по умолчанию и методами их изменения*/
        return new Docket(DocumentationType.SWAGGER_2)
                /*Метод select(), вызываемый для экземпляра компонента Docket, возвращает
                ApiSelectorBuilder, который предоставляет методы apis () и paths () для фильтрации
                контроллеров и методов, документируемых с использованием предикатов String*/
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.andersenlab.controllers"))
                .build()
                .apiInfo(metaData())
                //Позволяет использовать аутинтификацию в SwaggerUI
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiKey apiKey() {
        //apiKey is the name of the APIKey, `Authorization` is the key in the request header
        return new ApiKey("apiKey", "Authorization", "header");
    }

    /*Метод metaData возвращает объект ApiInfo, инициализированный информацией о нашем API*/
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Spring Boot REST API")
                .description("\"Spring Boot REST API for project Hotel\"")
                .version("1.0.0")
                .build();
    }

    @Override
    /*Обработчики ресурсов добавляются для настройки поддержки Swagger UI в Spring Boot 2 */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
