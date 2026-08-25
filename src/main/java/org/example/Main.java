package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String pageTitle = "User Management";
        int adultAge = 21;

        List<User> users = List.of(
                new User("Ada", 20, true,"1com"),
                new User("Lin", 17, true,"1@qq.com"),
                new User("Grace", 28, false,"1@qq.com"),
                new User("James", 32, true,"1@qq.com"),
                new User("uuu",18,true,"1@qq.com")
        );

        System.out.println("=== " + pageTitle + " ===");

        for (User user : users) {
            if (user.isActive() && user.getAge() >= adultAge) {
                printUser(user);
            }
        }

        int count = countActiveAdults(users, adultAge);
        System.out.println("Active adult users: " + count);
    }

    private static void printUser(User user) {
        System.out.println(user.getName() + " (age " + user.getAge() + ")"+"(email "+user.getEmail()+")");
    }

    private static int countActiveAdults(List<User> users, int adultAge) {
        int count = 0;

        for (User user : users) {
            if (user.isActive() && user.getAge() >= adultAge) {
                count++;
            }
        }

        return count;
    }
}
