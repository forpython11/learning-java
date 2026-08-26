package org.example.lesson26;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class PurchaseController {
    private final InventoryRepository inventoryRepository;
    private final PurchaseService purchaseService;

    public PurchaseController(
            InventoryRepository inventoryRepository,
            PurchaseService purchaseService
    ) {
        this.inventoryRepository = inventoryRepository;
        this.purchaseService = purchaseService;
    }

    @GetMapping("/{productId}")
    public InventoryResponse findById(@PathVariable String productId) {
        return inventoryRepository.findById(productId)
                .map(InventoryResponse::from)
                .orElseThrow(() -> new InventoryNotFoundException(productId));
    }

    @PostMapping("/purchases")
    public ResponseEntity<PurchaseResponse> purchase(@Valid @RequestBody PurchaseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseService.purchase(request));
    }
}
