package com.hyeonu.shopping.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @NoArgsConstructor
public class BrandRejectionReason {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String message;

    @OneToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public BrandRejectionReason(String message, Brand brand) {
        this.message = message;
        this.brand = brand;
    }
}
