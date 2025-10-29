package com.example.customersvc.graphql;

import com.example.customersvc.model.Customer;
import com.example.customersvc.service.CustomerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class CustomerGraphQlController {
  private final CustomerService svc;
  public CustomerGraphQlController(CustomerService svc) { this.svc = svc; }

  @QueryMapping
  public Customer customer(@Argument String id) { return svc.getById(id); }

  @QueryMapping
  public List<Customer> customers() { return svc.all(); }
}
