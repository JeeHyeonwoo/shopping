package com.hyeonu.shopping.repository;

import com.hyeonu.shopping.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByRoleName(String roleName);
}
