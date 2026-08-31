package org.example.lesson28;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lesson28Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Lesson28Application.class);
        application.setAdditionalProfiles("lesson28");
        application.run(args);
    }
}
