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
    private String address1;    // 도, 특별시, 광역시

    @Column(nullable = false)
    private String address2;    // 시, 군, 구

    @Column(nullable = false)
    private String address3;   // 읍, 면, 동

    @Column(nullable = false)
    private String addressDetail;   //상세 주소

    @ManyToOne
    @JoinColumn(name ="member_id")
    private Member member;

    @OneToMany(mappedBy = "orderInfo")
    private List<OrderDetail> product = new ArrayList<>();
}
