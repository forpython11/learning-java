package org.example.lesson31;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private final String displayName;

    public OpenApiConfig(
            @Value("${APP_DISPLAY_NAME:Learning Java API}") String displayName
    ) {
        this.displayName = displayName;
    }

    @Bean
    OpenAPI learningJavaOpenApi() {
        // TODO 1: 设置文档 title、version 和 description。
        return new OpenAPI();
    }
}
