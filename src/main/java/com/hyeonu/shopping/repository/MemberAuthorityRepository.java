package com.hyeonu.shopping.repository;

import com.hyeonu.shopping.domain.MemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long> {
}
