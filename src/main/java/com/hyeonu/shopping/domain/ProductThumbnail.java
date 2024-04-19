package com.hyeonu.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter
public class ProductThumbnail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String path;

    @Setter
    private int imageIndex;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
