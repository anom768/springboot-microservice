package com.bangkitanom.order.webclient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.bangkitanom.order.entity.Product;

@HttpExchange
public interface ProductClient {
    
    @GetExchange("/api/products/{id}")
    public Product findByid(@PathVariable("id") Long id);

}
