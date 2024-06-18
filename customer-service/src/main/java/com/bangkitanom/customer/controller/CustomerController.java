package com.bangkitanom.customer.controller;

import com.bangkitanom.customer.dto.SearchEmailRequest;
import com.bangkitanom.customer.entity.Customer;
import com.bangkitanom.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Value("${spring.application.version}")
    private String version;

    @PostMapping
    public Customer save(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable("id") Long id) {
        return customerService.findById(id);
    }

    @GetMapping
    public Iterable<Customer> findAll() {
        return customerService.findAll();
    }

    @PostMapping("/search-by-email")
    public Customer findByEmail(@RequestBody SearchEmailRequest request) {
        return customerService.findByEmail(request.getEmail());
    }

    @GetMapping("/version")
    public String getVersion() {
        return version;
    }

}
