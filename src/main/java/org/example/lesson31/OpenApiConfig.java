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
        // DONE 1: 设置文档 title、version 和 description。
        Info info = new Info().title(displayName)
                .version("1.0")
                .description("Product catalog API for frontend integration");
        return new OpenAPI().info(info);
    }
}
