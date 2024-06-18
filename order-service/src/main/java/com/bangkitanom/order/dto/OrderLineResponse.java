package com.bangkitanom.order.dto;

import com.bangkitanom.order.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineResponse {

    private Long id;
    private Product product;
    private Integer quantity;
    private Double price;

}
