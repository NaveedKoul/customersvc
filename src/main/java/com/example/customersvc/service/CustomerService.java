package com.example.customersvc.service;

import com.example.customersvc.model.Customer;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.*;

@Service
public class CustomerService {
  private final Map<String, Customer> repo = new LinkedHashMap<>();

  @PostConstruct
  public void init() {
    repo.put("1", new Customer("1","Alice","Anderson","alice@example.com","+1-555-0101"));
    repo.put("2", new Customer("2","Bob","Bennett","bob@example.com","+1-555-0202"));
    repo.put("3", new Customer("3","Carol","Carter","carol@example.com","+1-555-0303"));
  }

  public Customer getById(String id) {
    return repo.getOrDefault(id, repo.values().stream().findFirst().orElse(null));
  }

  public List<Customer> all() {
    return new ArrayList<>(repo.values());
  }
}
