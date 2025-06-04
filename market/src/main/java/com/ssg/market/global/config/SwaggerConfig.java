package com.ssg.market.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SSG-ASSIGNMENT API")
                        .description("SSG 사전과제 API doc")
                        .version("1.0.0")
                        .contact(new Contact().name("SSG"))
                        .license(new License().name("SSG ASSIGNMENT")));
    }
}
