package com.hyeonu.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity @Getter
public class ProductInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private String size;

    private int amount;

    @ManyToOne
    @JoinColumn
    private Product product;
}
