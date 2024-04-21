package com.hyeonu.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Brand {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false)
    private String name;

    @Setter
    private int status;

    @OneToMany(mappedBy = "brand")
    private List<Product> productList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name= "member_id")
    private Member member;

    @OneToOne(mappedBy = "brand")
    private BrandRejectionReason brandRejectionReasons;

    protected Brand() {
    }

    private Brand(String name, int status, Member member) {
        this.name = name;
        this.member = member;
        this.status = status;
    }

    public static Brand of(String name, int status, Member member) {
        return new Brand(name,status, member);
    }

}
