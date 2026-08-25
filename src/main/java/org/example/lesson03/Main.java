package org.example.lesson03;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        List<Order> orders = List.of(
                new Order("A100", 299.0, OrderStatus.PENDING),
                new Order("A101", 499.0, OrderStatus.PAID),
                new Order("A102", 0.0, OrderStatus.PENDING)
        );

        orders.forEach(order -> processOrder(orderService, order));
    }

    private static void processOrder(OrderService orderService, Order order) {
        // DONE 3: 使用 try/catch 支付订单，并按讲义中的格式打印成功或失败结果。
        try {
            orderService.pay(order);

            System.out.println("Order " + order.getId() + " paid successfully: " + order.getStatus());
        } catch (RuntimeException e) {
            System.out.println(
                    "Order " + order.getId()
                            + " failed: "
                            + e.getMessage()
            );
        }
    }
}
