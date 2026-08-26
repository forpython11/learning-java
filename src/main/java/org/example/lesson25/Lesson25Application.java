package org.example.lesson25;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lesson25Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Lesson25Application.class);
        application.setAdditionalProfiles("lesson25");
        application.run(args);
    }
}
