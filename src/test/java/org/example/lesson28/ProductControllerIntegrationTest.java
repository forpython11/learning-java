package org.example.lesson28;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.fail;

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
    @Disabled("TODO 1: 完成查询成功测试后删除此注解")
    void returnsProductFromDatabase() throws Exception {
        // TODO 1: 向 H2 保存 P100，发送 GET，并断言状态码和三个 JSON 字段。
        fail("TODO 1 is not implemented");
    }

    @Test
    @Disabled("TODO 2: 完成商品不存在测试后删除此注解")
    void returnsNotFoundError() throws Exception {
        // TODO 2: 查询 P404，断言 404、错误码和错误消息。
        fail("TODO 2 is not implemented");
    }

    @Test
    @Disabled("TODO 3: 完成创建商品测试后删除此注解")
    void createsProductAndPersistsIt() throws Exception {
        // TODO 3: 发送 POST JSON，断言 201，并从 Repository 确认数据已写入 H2。
        fail("TODO 3 is not implemented");
    }
}
