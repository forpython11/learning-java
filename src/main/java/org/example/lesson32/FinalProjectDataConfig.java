package org.example.lesson32;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class FinalProjectDataConfig {
    @Bean
    CommandLineRunner seedFinalProject(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                productRepository.save(new ProductEntity(
                        "P100",
                        "Keyboard",
                        new BigDecimal("299.00"),
                        5
                ));
            }
        };
    }
}
