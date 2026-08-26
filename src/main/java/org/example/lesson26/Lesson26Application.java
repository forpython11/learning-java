package org.example.lesson26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lesson26Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Lesson26Application.class);
        application.setAdditionalProfiles("lesson26");
        application.run(args);
    }
}
