package org.example.lesson15;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// DONE 1: 添加 @RestController。
@RestController
public class GreetingController {
    // DONE 2: 添加 GET /api/hello 路由注解。
    @GetMapping("/api/hello")
    public GreetingResponse hello() {
        // DONE 3: 返回消息为 Hello from Spring Boot 的 GreetingResponse。
        return new GreetingResponse("Hello from Spring Boot");
    }
}
