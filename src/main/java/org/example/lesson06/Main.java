package org.example.lesson06;

public class Main {
    public static void main(String[] args) {
        CustomerRepository repository = new CustomerRepository();

        repository.save(new Customer("C001", "Ada"));
        repository.save(new Customer("C002", "Grace"));

        Customer customer = repository.findById("C001");
        System.out.println("Found customer: " + customer.getName());
        System.out.println("Customer count: " + repository.count());

        try {
            repository.save(new Customer("C001", "Another Ada"));
        } catch (IllegalArgumentException exception) {
            System.out.println("Duplicate rejected: " + exception.getMessage());
        }
    }
}
