package com.patrick.inventorymanagementtask.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author patrick on 4/5/20
 * @project  inventory
 */

@EnableSwagger2
@Configuration
public class Swagger2Config {
    
    @Value("${base.url}")
    private String baseUrl;
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).host(baseUrl).select()
               .apis(RequestHandlerSelectors
                        .basePackage("com.patrick.inventorymanagementtask.api"))
               /* .paths(PathSelectors.regex("/api/*"))*/
               // .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiEndPointsInfo());
    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Duka Lite Solutions REST API")
                .description("Inventory Solutions")
                .contact(new Contact("Inventory", "http://localhost:8080", "test@localhost:8080"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
}
