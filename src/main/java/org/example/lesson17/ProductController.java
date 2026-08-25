package org.example.lesson17;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            // TODO 3: 在已有的 @RequestBody 前添加 @Valid。
            @RequestBody CreateProductRequest request
    ) {
        ProductResponse response = new ProductResponse("P200", request.name(), request.price());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
