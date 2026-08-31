package org.example.lesson27;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService service;

    @Test
    void deductsStockAndSavesOrder() {
        // DONE 1: Stub 查询结果，断言剩余库存，并验证两个 save 调用。
        Product product = new Product("P100",10);
        when(productRepository.findById("P100"))
                .thenReturn(Optional.of(product));
        OrderResult result = service.placeOrder("P100",3);
        assertEquals(7, result.remainingStock());
        verify(productRepository).save(product);
        verify(orderRepository).save(new Order("P100", 3));
    }

    @Test
    void throwsWhenProductDoesNotExist() {
        // DONE 2: 返回 Optional.empty()，断言异常，并验证没有保存订单。
        when(productRepository.findById("P1"))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> service.placeOrder("P1", 3)
        );

        verifyNoInteractions(orderRepository);
    }

    @Test
    void doesNotSaveWhenStockIsInsufficient() {
        // DONE 3: 准备库存不足的商品，断言异常，并验证没有执行 save。
        Product product = new Product("P100",2);
        when(productRepository.findById("P100")).thenReturn(Optional.of(product));
        assertThrows(InsufficientStockException.class,()->service.placeOrder("P100",3));
        verify(productRepository,never()).save(any());
        verifyNoInteractions(orderRepository);
    }
}
