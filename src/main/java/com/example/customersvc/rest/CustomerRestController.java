package com.example.customersvc.rest;

import com.example.customersvc.model.Customer;
import com.example.customersvc.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {
  private final CustomerService svc;
  public CustomerRestController(CustomerService svc) { this.svc = svc; }

  @GetMapping
  public List<Customer> all() { return svc.all(); }

  @GetMapping("/{id}")
  public Customer byId(@PathVariable String id) { return svc.getById(id); }
}
