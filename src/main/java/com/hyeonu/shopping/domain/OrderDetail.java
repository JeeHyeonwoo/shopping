package com.hyeonu.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity @Getter
public class OrderDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderInfo orderInfo;

}
