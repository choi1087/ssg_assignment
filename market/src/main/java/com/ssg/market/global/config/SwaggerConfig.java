package com.ssg.market.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.server-url}")
    private String swaggerServerURL;

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url(swaggerServerURL)))
                .info(new Info()
                        .title("SSG-ASSIGNMENT API")
                        .description("SSG 사전과제 API doc")
                        .version("1.0.0")
                        .contact(new Contact().name("SSG"))
                        .license(new License().name("SSG ASSIGNMENT")));
    }
}
