package com.hyeonu.shopping.repository;

import com.hyeonu.shopping.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {

}
