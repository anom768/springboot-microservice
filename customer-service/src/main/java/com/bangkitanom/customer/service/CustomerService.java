package com.bangkitanom.customer.service;

import com.bangkitanom.customer.entity.Customer;
import com.bangkitanom.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

}
