package com.bangkitanom.order.service;

import com.bangkitanom.order.dto.OrderLineResponse;
import com.bangkitanom.order.dto.OrderResponse;
import com.bangkitanom.order.entity.Order;
import com.bangkitanom.order.entity.OrderLine;
import com.bangkitanom.order.entity.Product;
import com.bangkitanom.order.repository.OrderRepository;
import com.bangkitanom.order.webclient.CustomerClient;
import com.bangkitanom.order.webclient.ProductClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    // private final RestTemplate restTemplate;

    public Order save(Order order) {
        for (OrderLine orderLine: order.getOrderLines()) {
            orderLine.setOrder(order);
        }
        return orderRepository.save(order);
    }

    @CircuitBreaker(name = "customerService", fallbackMethod = "fallbackFindCustomerById")
    public OrderResponse findById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            return null;
        }

        Order order = optionalOrder.get();
        OrderResponse orderResponse = new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderDate(),
                customerClient.findById(order.getCustomerId()),
                new ArrayList<>()
        );

        for (OrderLine orderLine: order.getOrderLines()) {
            Product product = productClient.findByid(orderLine.getProductId());
            orderResponse.getOrderLines().add(new OrderLineResponse(
                    orderLine.getId(),
                    product,
                    orderLine.getQuantity(),
                    orderLine.getPrice()
            ));
        }
        return orderResponse;
    }

    public OrderResponse findByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (order == null) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderDate(),
                customerClient.findById(order.getCustomerId()),
                new ArrayList<>()
        );

        for (OrderLine orderLine: order.getOrderLines()) {
            Product product = productClient.findByid(orderLine.getProductId());
            orderResponse.getOrderLines().add(new OrderLineResponse(
                    orderLine.getId(),
                    product,
                    orderLine.getQuantity(),
                    orderLine.getPrice()
            ));
        }
        return orderResponse;
    }

    // private Customer findCustomerById(Long id) {
    //     return restTemplate.getForObject("http://CUSTOMER-SERVICE/api/customers/" + id, Customer.class);
    // }

    // private Product findProductById(Long id) {
    //     return restTemplate.getForObject("http://PRODUCT-SERVICE/api/products/" + id, Product.class);
    // }

    private OrderResponse fallbackFindCustomerById(Long id, Throwable throwable) {
        return new OrderResponse();
    }

}
