package com.hyeonu.shopping.repository;

import com.hyeonu.shopping.domain.ProductThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductThumbnailRepository extends JpaRepository<ProductThumbnail, Long> {
}
