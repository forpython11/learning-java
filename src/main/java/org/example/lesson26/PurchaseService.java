package org.example.lesson26;

import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    private final InventoryRepository inventoryRepository;
    private final PurchaseOrderRepository orderRepository;

    public PurchaseService(
            InventoryRepository inventoryRepository,
            PurchaseOrderRepository orderRepository
    ) {
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
    }

    // TODO 3: 在一个事务中查询库存、扣减库存、保存订单并返回真实结果。
    public PurchaseResponse purchase(PurchaseRequest request) {
        return new PurchaseResponse(
                request.orderId(),
                request.productId(),
                request.quantity(),
                -1,
                -1
        );
    }
}
