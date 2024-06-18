package com.bangkitanom.order.controller;

import com.bangkitanom.order.dto.OrderResponse;
import com.bangkitanom.order.entity.Order;
import com.bangkitanom.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    public Order save(@RequestBody Order order) {
        return orderService.save(order);
    }

    @GetMapping("/order-number/{orderNumber}")
    public OrderResponse findByOrderNumber(@PathVariable("orderNumber") String orderNumber) {
        return orderService.findByOrderNumber(orderNumber);
    }

}