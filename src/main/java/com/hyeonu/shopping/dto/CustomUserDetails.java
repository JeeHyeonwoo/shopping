package com.hyeonu.shopping.dto;


import com.hyeonu.shopping.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private Boolean isNonLocked;
    @Getter
    private Long brandId;
    private List<GrantedAuthority> roles = new ArrayList<>();

    public CustomUserDetails(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.isNonLocked = member.getIsNonLocked();
        this.roles = member.getRoles().stream().map(
                (role)-> new SimpleGrantedAuthority(
                        role.getAuthority().getRoleName())).collect(Collectors.toList());
        if (member.getBrand() != null) {
            this.brandId = member.getBrand().getId();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
