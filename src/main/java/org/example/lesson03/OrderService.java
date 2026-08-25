package org.example.lesson03;

public class OrderService {
    public void pay(Order order) {
        // DONE 1: 金额不合法时抛出 IllegalArgumentException。
       if(order.getAmount()<=0) {
           throw new IllegalArgumentException(
                   "Order amount must be greater than 0"
           );
       }
        // DONE 2: 状态不是 PENDING 时抛出 IllegalStateException。
        if(order.getStatus()!=OrderStatus.PENDING){
            throw new IllegalStateException(
                    "Only pending orders can be paid"
            );
        }
        order.setStatus(OrderStatus.PAID);
    }
}
