package org.example.lesson32;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lesson32")
public class FinalProjectController {
    private final ProductRepository productRepository;
    private final FinalOrderService orderService;

    public FinalProjectController(
            ProductRepository productRepository,
            FinalOrderService orderService
    ) {
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    @GetMapping("/products")
    public List<ProductResponse> products() {
        return productRepository.findAll().stream()
                .map(ProductResponse::from)
                .toList();
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
}
