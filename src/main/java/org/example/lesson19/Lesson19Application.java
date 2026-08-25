package org.example.lesson19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 标记这是 Spring Boot 的启动类。
// Spring 会从当前包开始扫描并管理 Controller、Service、Repository 等组件。
@SpringBootApplication
public class Lesson19Application {
    // Java 程序入口。args 用来接收启动时传入的命令行参数。
    public static void main(String[] args) {
        // 启动 Spring 容器和内置 Web 服务器，开始接收 HTTP 请求。
        SpringApplication.run(Lesson19Application.class, args);
    }
}
