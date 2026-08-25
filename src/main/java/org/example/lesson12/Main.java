package org.example.lesson12;

public class Main {
    public static void main(String[] args) {
        OrderService service = new OrderService();
        Order pendingOrder = new Order("A100", OrderStatus.PENDING);
        Order paidOrder = new Order("A101", OrderStatus.PAID);

        service.cancel(pendingOrder);
        System.out.println("Order " + pendingOrder.getId() + " status: " + pendingOrder.getStatus());

        try {
            service.cancel(paidOrder);
        } catch (BusinessException exception) {
            System.out.println("Rejected [" + exception.getCode() + "]: " + exception.getMessage());
        }
    }
}
