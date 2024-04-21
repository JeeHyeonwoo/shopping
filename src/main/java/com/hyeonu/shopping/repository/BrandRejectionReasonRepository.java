package com.hyeonu.shopping.repository;

import com.hyeonu.shopping.domain.BrandRejectionReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRejectionReasonRepository extends JpaRepository<BrandRejectionReason, Long> {
}
