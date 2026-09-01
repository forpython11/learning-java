package org.example.lesson33;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderQueryService service = new OrderQueryService(createRepository());

        OrderResponse order = service.findById("O100");
        System.out.println("订单详情：" + format(order));

        List<OrderResponse> customerOrders = service.findByCustomerId("frontend");
        System.out.println("用户订单数：" + customerOrders.size());
        System.out.println("订单列表：" + ids(customerOrders));

        OrderPageResponse firstPage = service.findPageByCustomerId("frontend", 0, 2);
        System.out.println("第一页：" + ids(firstPage.content()));
        System.out.println(
                "分页信息：page=" + firstPage.page()
                        + ", size=" + firstPage.size()
                        + ", totalElements=" + firstPage.totalElements()
                        + ", totalPages=" + firstPage.totalPages()
        );

        try {
            service.findById("UNKNOWN");
        } catch (OrderNotFoundException exception) {
            System.out.println("不存在订单：" + exception.getMessage());
        }
    }

    static OrderQueryRepository createRepository() {
        return new InMemoryOrderQueryRepository(List.of(
                order("O100", "frontend", "598.00", OrderStatus.CREATED),
                order("O200", "frontend", "99.90", OrderStatus.CREATED),
                order("O300", "frontend", "39.90", OrderStatus.CANCELLED),
                order("O400", "admin", "1299.00", OrderStatus.CREATED)
        ));
    }

    private static Order order(
            String id,
            String customerId,
            String total,
            OrderStatus status
    ) {
        return new Order(id, customerId, new BigDecimal(total), status);
    }

    private static String format(OrderResponse order) {
        return order.id() + " / "
                + order.customerId() + " / "
                + order.total() + " / "
                + order.status();
    }

    private static List<String> ids(List<OrderResponse> orders) {
        return orders.stream().map(OrderResponse::id).toList();
    }
}
