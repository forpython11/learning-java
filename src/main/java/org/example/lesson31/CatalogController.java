package org.example.lesson31;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lesson31")
// DONE 2: 使用 @Tag 描述 Catalog API，并使用 @Operation 描述 products 方法。
@Tag(name="Catalog",description = "Product catalog operations")
public class CatalogController {
    @GetMapping("/products")
    @Operation(summary="List products")
    public List<ProductSummary> products() {
        return List.of(
                new ProductSummary("P100", "Keyboard"),
                new ProductSummary("P200", "Mouse")
        );
    }
}
