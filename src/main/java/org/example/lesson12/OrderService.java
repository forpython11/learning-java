package org.example.lesson12;

public class OrderService {
    public void cancel(Order order) {
        // DONE 1: order 为 null 时抛出 IllegalArgumentException。
        if(order == null){
            throw new IllegalArgumentException("Order must not be null");
        }
        // DONE 2: 状态不是 PENDING 时抛出带错误码的 BusinessException。
       else if(order.getStatus()!=OrderStatus.PENDING){
            throw new BusinessException("ORDER_CANNOT_CANCEL","Only pending orders can be cancelled");
        }
        // DONE 3: 将订单状态改为 CANCELLED。
        order.setStatus(OrderStatus.CANCELLED);
    }
}
