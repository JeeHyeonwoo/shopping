package com.hyeonu.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity @Getter @NoArgsConstructor
public class Authority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "authority")
    private Set<MemberAuthority> memberAuthorities = new HashSet<>();

    public Authority(String roleName) {
        this.roleName = roleName;
    }
}
