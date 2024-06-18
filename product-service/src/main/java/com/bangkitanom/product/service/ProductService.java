package com.bangkitanom.product.service;

import com.bangkitanom.product.entity.Product;
import com.bangkitanom.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

}
