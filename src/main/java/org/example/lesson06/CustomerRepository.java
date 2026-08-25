package org.example.lesson06;

import java.util.HashMap;
import java.util.Map;

public class CustomerRepository {
    private final Map<String, Customer> customers = new HashMap<>();

    public void save(Customer customer) {
        // TODO 1: ID 已存在时抛出 IllegalArgumentException。

        // TODO 2: 以客户 ID 为 Key 保存客户。
    }

    public Customer findById(String id) {
        // TODO 3: 根据 ID 查找客户；找不到时抛出异常，找到时返回客户。
        return null;
    }

    public int count() {
        return customers.size();
    }
}
