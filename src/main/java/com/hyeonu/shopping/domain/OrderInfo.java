package com.hyeonu.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity  @Getter
public class OrderInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name ="member_id")
    private Member member;

    @OneToMany(mappedBy = "orderInfo")
    private List<OrderDetail> product = new ArrayList<>();
}
