package org.example.lesson09;

public class Main {
    public static void main(String[] args) {
        ProductService service = new ProductService();

        CreateProductRequest request = new CreateProductRequest("Keyboard", 299.0);
        ProductResponse response = service.create(request);

        if (response != null) {
            System.out.println(
                    "Created: " + response.id()
                            + " - " + response.name()
                            + " - " + response.price()
            );
        }

        try {
            new CreateProductRequest("Mouse", 0.0);
        } catch (IllegalArgumentException exception) {
            System.out.println("Rejected: " + exception.getMessage());
        }
    }
}
