package com.bangkitanom.order.dto;

import com.bangkitanom.order.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private Date orderDate;
    private Customer customer;
    private List<OrderLineResponse> orderLines;

}
