package com.hyeonu.shopping.repository;

import com.hyeonu.shopping.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
