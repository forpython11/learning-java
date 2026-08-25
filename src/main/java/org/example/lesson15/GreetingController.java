package org.example.lesson15;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO 1: 添加 @RestController。
@RestController
public class GreetingController {
    // TODO 2: 添加 GET /api/hello 路由注解。
    @GetMapping("/api/hello")
    public GreetingResponse hello() {
        // TODO 3: 返回消息为 Hello from Spring Boot 的 GreetingResponse。
        return new GreetingResponse("");
    }
}
