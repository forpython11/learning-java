package org.example.lesson27;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService service;

    @Test
    @Disabled("TODO 1: 完成成功下单测试后删除此注解")
    void deductsStockAndSavesOrder() {
        // TODO 1: Stub 查询结果，断言剩余库存，并验证两个 save 调用。
        fail("TODO 1 is not implemented");
    }

    @Test
    @Disabled("TODO 2: 完成商品不存在测试后删除此注解")
    void throwsWhenProductDoesNotExist() {
        // TODO 2: 返回 Optional.empty()，断言异常，并验证没有保存订单。
        fail("TODO 2 is not implemented");
    }

    @Test
    @Disabled("TODO 3: 完成库存不足测试后删除此注解")
    void doesNotSaveWhenStockIsInsufficient() {
        // TODO 3: 准备库存不足的商品，断言异常，并验证没有执行 save。
        fail("TODO 3 is not implemented");
    }
}
