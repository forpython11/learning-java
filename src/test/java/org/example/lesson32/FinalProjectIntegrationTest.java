package org.example.lesson32;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Lesson32Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("lesson32")
class FinalProjectIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseOrderRepository orderRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void resetData() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        productRepository.save(new ProductEntity(
                "P100",
                "Keyboard",
                new BigDecimal("299.00"),
                5
        ));
    }

    @Test
    void exposesPublicCatalogAndProtectsOrders() throws Exception {
        mockMvc.perform(get("/api/lesson32/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("P100"))
                .andExpect(jsonPath("$[0].stock").value(5));

        mockMvc.perform(post("/api/lesson32/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":\"P100\",\"quantity\":1}"))
                .andExpect(status().isUnauthorized());

        assertEquals(3, loginAndGetToken().split("\\.").length);
    }

    @Test
    @Disabled("TODO 1: 完成商品查询后删除此注解")
    void rejectsMissingProduct() throws Exception {
        createOrder("UNKNOWN", 1)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PRODUCT_NOT_FOUND"));

        assertEquals(0, orderRepository.count());
    }

    @Test
    @Disabled("TODO 2: 完成库存校验后删除此注解")
    void rejectsInsufficientStockWithoutWriting() throws Exception {
        createOrder("P100", 6)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("INSUFFICIENT_STOCK"));

        assertEquals(0, orderRepository.count());
        assertEquals(5, productRepository.findById("P100").orElseThrow().getStock());
    }

    @Test
    @Disabled("TODO 3: 完成事务下单后删除此注解")
    void createsOrderAndDeductsStock() throws Exception {
        createOrder("P100", 2)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value("P100"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.total").value(598.00))
                .andExpect(jsonPath("$.remainingStock").value(3));

        assertEquals(1, orderRepository.count());
        assertEquals(3, productRepository.findById("P100").orElseThrow().getStock());
    }

    private org.springframework.test.web.servlet.ResultActions createOrder(
            String productId,
            int quantity
    ) throws Exception {
        return mockMvc.perform(post("/api/lesson32/orders")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginAndGetToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":\"" + productId
                        + "\",\"quantity\":" + quantity + "}"));
    }

    private String loginAndGetToken() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/lesson32/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"frontend\","
                                + "\"password\":\"frontend123\"}"))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TokenResponse.class
        ).token();
    }
}
