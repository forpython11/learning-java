package org.example.lesson07;

public class Main {
    public static void main(String[] args) {
        BookRepository repository = new BookRepository();
        repository.save(new Book("978-0132350884", "Clean Code"));

        BookService service = new BookService(repository);

        System.out.println("Found title: " + service.findTitleOrDefault("978-0132350884"));
        System.out.println("Missing title: " + service.findTitleOrDefault("999"));

        try {
            service.requireBook("999");
        } catch (IllegalArgumentException exception) {
            System.out.println("Required lookup failed: " + exception.getMessage());
        }
    }
}
