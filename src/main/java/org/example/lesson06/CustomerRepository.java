package org.example.lesson06;

import java.util.HashMap;
import java.util.Map;

public class CustomerRepository {
    private final Map<String, Customer> customers = new HashMap<>();

    public void save(Customer customer) {
        // DONE 1: ID 已存在时抛出 IllegalArgumentException。
        if(customers.containsKey(customer.getId())){
            throw new IllegalArgumentException(
                    "Customer ID already exists: "+ customer.getId()
            );
        }
        customers.put(customer.getId(),customer);

        // DONE 2: 以客户 ID 为 Key 保存客户。
    }

    public Customer findById(String id) {
        // DONE 3: 根据 ID 查找客户；找不到时抛出异常，找到时返回客户。
        if(customers.containsKey(id)){
            return customers.get(id);
        }else{
            throw new IllegalArgumentException(
                    "Customer not found: " + id
            );
        }
    }

    public int count() {
        return customers.size();
    }
}
