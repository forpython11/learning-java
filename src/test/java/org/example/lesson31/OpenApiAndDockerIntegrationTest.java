package org.example.lesson31;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Lesson31Application.class)
@AutoConfigureMockMvc
class OpenApiAndDockerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Disabled("TODO 1: 完成 OpenAPI 基本信息后删除此注解")
    void publishesApiInformation() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Learning Java API"))
                .andExpect(jsonPath("$.info.version").value("1.0"))
                .andExpect(jsonPath("$.info.description")
                        .value("Product catalog API for frontend integration"));
    }

    @Test
    @Disabled("TODO 2: 完成 Controller 文档注解后删除此注解")
    void documentsCatalogEndpoint() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['paths']['/api/lesson31/products']['get']['summary']")
                        .value("List products"))
                .andExpect(jsonPath("$['paths']['/api/lesson31/products']['get']['tags'][0]")
                        .value("Catalog"));
    }

    @Test
    @Disabled("TODO 3: 完成 Dockerfile 后删除此注解")
    void dockerfileRunsPackagedApplication() throws Exception {
        String dockerfile = Files.readString(Path.of("Dockerfile"));

        assertTrue(dockerfile.contains("FROM eclipse-temurin:25-jre"));
        assertTrue(dockerfile.contains(
                "COPY target/learning-java-1.0-SNAPSHOT.jar app.jar"
        ));
        assertTrue(dockerfile.contains("ENV SERVER_PORT=8080"));
        assertTrue(dockerfile.contains(
                "ENV APP_DISPLAY_NAME=Learning-Java-Container"
        ));
        assertTrue(dockerfile.contains(
                "ENTRYPOINT [\"java\", \"-jar\", \"app.jar\"]"
        ));
    }
}
