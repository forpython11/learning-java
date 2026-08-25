package org.example;

public class User {
    private final String name;
    private final int age;
    private final boolean active;
    private final String email;

    public User(String name, int age, boolean active,String email) {
        this.name = name;
        this.age = age;
        this.active = active;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isActive() {
        return active;
    }

    public String getEmail() {
        return email;
    }
}
