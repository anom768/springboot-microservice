package com.bangkitanom.product.controller;

import com.bangkitanom.product.entity.Product;
import com.bangkitanom.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping("/{id}")
    public Product findbyId(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @GetMapping
    public Iterable<Product> findAll() {
        return productService.findAll();
    }

}
