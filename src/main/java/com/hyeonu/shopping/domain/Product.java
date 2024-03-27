package com.hyeonu.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false)
    private String name;

    @Setter
    private String info;

    @Setter @Column(nullable = false)
    private int price;

    @Setter @Column(nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetail = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Image> imageList = new ArrayList<>();
}
