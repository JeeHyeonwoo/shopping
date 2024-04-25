package com.hyeonu.shopping.domain;

import com.hyeonu.shopping.dto.request.ProductInfoDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private String size;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductInfo(String color, String size, int amount, Product product) {
        this.color = color;
        this.size = size;
        this.amount = amount;
        this.product = product;
    }

    public static ProductInfo from(ProductInfoDto dto, Product product) {
        return new ProductInfo(
                dto.getColor(),
                dto.getSize(),
                dto.getAmount(),
                product
        );
    }
}
