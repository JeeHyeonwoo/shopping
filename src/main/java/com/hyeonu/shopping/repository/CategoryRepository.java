package com.hyeonu.shopping.repository;

import com.hyeonu.shopping.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
