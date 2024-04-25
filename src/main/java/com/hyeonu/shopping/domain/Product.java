package com.hyeonu.shopping.domain;

import com.hyeonu.shopping.dto.request.ProductRequestDto;
import com.hyeonu.shopping.dto.type.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Setter @Column(nullable = false)
    private Gender gender;

    @Setter
    private int views;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product")
    private List<ProductInfo> productInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetail = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductThumbnail> productThumbnailList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductDetail> productDetails = new ArrayList<>();

    public Product(String name, int price, Gender gender, int views, Category category, Brand brand) {
        this.name = name;
        this.price = price;
        this.gender = gender;
        this.views = views;
        this.category = category;
        this.brand = brand;
    }
}
