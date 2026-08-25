package org.example.lesson16;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProduct(
            // DONE 1: 添加 @PathVariable。
            @PathVariable()
            String id,
            // DONE 2: 添加带默认值 false 的 @RequestParam。
            @RequestParam(defaultValue = "false")
            boolean includeDetails
    ) {
        if (!"P100".equals(id)) {
            return ResponseEntity.notFound().build();
        }

        // DONE 3: 根据 includeDetails 创建 ProductResponse，并返回 200。
        String name =includeDetails?"Keyboard (mechanical)":"Keyboard";
        return ResponseEntity.ok(new ProductResponse(id,name));
    }
}
