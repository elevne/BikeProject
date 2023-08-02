package com.bike.bikeproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api() {  // Docket: Swagger 설정의 핵심
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)  // Swagger 에서 제공하는 기본 응답 코드 (200, 401, 403, 404). false 로 설정 시 기본 응답코드를 노출하지 않음
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bike.bikeproject.controller"))  // API 스펙이 작성된 패키지 지정
                .paths(PathSelectors.any())  // .apis() 에 있는 API 중 특정 경로를 선택할 수 있음
                .build()
                .apiInfo(apiInfo());  // Swagger UI 로 노출할 정보
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Bike Project API")
                .description("Bike Project API Swagger Document")
                .version("3.0.0")
                .build();
    }

}
