package com.hyeonu.shopping.domain;

import com.hyeonu.shopping.dto.request.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity @Getter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Setter @Column(nullable = false)
    private Boolean isNonLocked;

    @Setter
    private Boolean gender;

    private LocalDateTime createdAt;

    private String address1;
    private String address2;
    private String address3;
    private String addressDetail;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private Set<MemberAuthority> roles = new HashSet<>();

    @OneToOne(mappedBy = "member")
    private Brand brand;

    public Member() {
    }

    public Member(String username, String password, LocalDateTime createAt) {
        this.username = username;
        this.password = password;
        this.createdAt = createAt;
    }

    public Member(String username, String password, LocalDateTime createAt, Boolean gender, boolean isNonLocked) {
        this.username = username;
        this.password = password;
        this.createdAt = createAt;
        this.gender = gender;
        this.isNonLocked = isNonLocked;
    }

    public Member(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.isNonLocked = true;
        this.gender = requestDto.isGender();
        this.createdAt = LocalDateTime.now();
        this.address1 = requestDto.getAddress1();
        this.address2 = requestDto.getAddress2();
        this.address3 = requestDto.getAddress3();
        this.addressDetail = requestDto.getAddressDetail();
    }






}
