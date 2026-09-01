package org.example.lesson33;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class OrderQueryServiceTest {
    private final OrderQueryService service = new OrderQueryService(Main.createRepository());

    @Disabled("完成 TODO 1 后启用")
    @Test
    void findsOrderByIdAndRejectsMissingOrder() {
        // TODO 1: 断言 O100 的真实字段，并断言 UNKNOWN 抛出正确异常。
        fail("TODO 1: 验证单个订单查询和不存在分支");
    }

    @Disabled("完成 TODO 2 后启用")
    @Test
    void returnsOnlyTheCustomersOrders() {
        // TODO 2: 查询 frontend，并精确断言三个订单 ID 的顺序。
        fail("TODO 2: 验证客户订单列表");
    }

    @Disabled("完成 TODO 3 后启用")
    @Test
    void returnsPageContentAndMetadata() {
        // TODO 3: 断言第一页内容，以及 page、size、totalElements、totalPages。
        fail("TODO 3: 验证分页内容和元数据");
    }

    private List<String> ids(List<OrderResponse> orders) {
        return orders.stream().map(OrderResponse::id).toList();
    }
}
