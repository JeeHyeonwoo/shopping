package com.hyeonu.shopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity @Getter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Category parent;

    @Setter
    @Column(name="parent_id")
    private Long parentId;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> productList = new ArrayList<>();

    private Category(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public final static Category of(String name, Long parentId){
        return new Category(name, parentId);
    }

    @JsonIgnore
    public Category getParent() {
        return parent;
    }
}
