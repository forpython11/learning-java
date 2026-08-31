package org.example.lesson28;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Lesson28Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("lesson28-test")
class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void clearDatabase() {
        productRepository.deleteAll();
    }

    @Test
    void returnsProductFromDatabase() throws Exception {
        // DONE 1: 向 H2 保存 P100，发送 GET，并断言状态码和三个 JSON 字段。
        ProductEntity product = new ProductEntity("P100","Keyboard",10);
        productRepository.save(product);
        mockMvc.perform(get("/api/lesson28/products/P100"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value("P100"))
                        .andExpect(jsonPath("$.name").value("Keyboard"))
                        .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    void returnsNotFoundError() throws Exception {
        // DONE 2: 查询 P404，断言 404、错误码和错误消息。
        mockMvc.perform(get("/api/lesson28/products/P404"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PRODUCT_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Product not found: P404"));
    }

    @Test
    void createsProductAndPersistsIt() throws Exception {
        // DONE 3: 发送 POST JSON，断言 201，并从 Repository 确认数据已写入 H2。
        mockMvc.perform(post("/api/lesson28/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"P200\",\"name\":\"Mouse\",\"stock\":5}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("P200"))
                .andExpect(jsonPath("$.name").value("Mouse"))
                .andExpect(jsonPath("$.stock").value(5));
        ProductEntity savedProduct = productRepository.findById("P200")
                .orElseThrow();

        assertEquals("P200", savedProduct.getId());
        assertEquals("Mouse", savedProduct.getName());
        assertEquals(5, savedProduct.getStock());
    }
}
