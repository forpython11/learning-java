package org.example.lesson26;

import jakarta.transaction.Transactional;
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

    // DONE 3: 在一个事务中查询库存、扣减库存、保存订单并返回真实结果。
    @Transactional
    public PurchaseResponse purchase(PurchaseRequest request) {
        // findById 返回 Optional；库存不存在时终止本次购买。
        InventoryItemEntity item = inventoryRepository
                .findById(request.productId())
                .orElseThrow(() -> new InventoryNotFoundException(request.productId()));

        // item 是当前事务管理的 Entity，字段变化会由 JPA 自动写回数据库。
        item.decreaseStock(request.quantity());

        // 保存订单并立即刷新 SQL；任一步失败都会让整个事务回滚。
        PurchaseOrderEntity order = new PurchaseOrderEntity(
                request.orderId(),
                request.productId(),
                request.quantity()
        );
        orderRepository.saveAndFlush(order);

        // 返回扣减后的库存，以及刷新 SQL 后由乐观锁递增的版本号。
        return new PurchaseResponse(
                request.orderId(),
                request.productId(),
                request.quantity(),
                item.getStock(),
                item.getVersion()
        );
    }
}
