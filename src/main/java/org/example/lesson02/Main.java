package org.example.lesson02;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products = List.of(new Product("Keyboard", 299.0, "Electronics", true), new Product("Mouse", 79.0, "Electronics", true), new Product("Monitor", 1599.0, "Electronics", false), new Product("Desk", 899.0, "Furniture", true), new Product("Lamp", 129.0, "Furniture", true));

        double minPrice = 100.0;
        List<Product> availableProducts = filterAvailableProducts(products, minPrice);

        System.out.println("=== Available products (price >= " + minPrice + ") ===");
        availableProducts.forEach(product -> System.out.println(product.getName() + ": " + product.getPrice()));

        double totalPrice = calculateTotalPrice(availableProducts);
        long electronicsCount = countByCategory(availableProducts, "Electronics");

        System.out.println("Total price: " + totalPrice);
        System.out.println("Electronics count: " + electronicsCount);
    }

    private static List<Product> filterAvailableProducts(List<Product> products, double minPrice) {
        // DONE 1: 使用 stream、filter 和 toList 完成筛选。
        return products.stream()
                .filter(product -> product.isInStock() && product.getPrice() >= minPrice)
                .toList();
    }

    private static double calculateTotalPrice(List<Product> products) {
        // DONE 2: 使用 stream、mapToDouble 和 sum 计算总价。
        return products.stream()
                .mapToDouble(product -> product.getPrice())
                .sum();
    }

    private static long countByCategory(List<Product> products, String category) {
        // DONE 3: 使用 stream、filter 和 count 统计分类数量。
        return products.stream()
                .filter(product -> category.equals(product.getCategory()))
                .count();
    }
}
