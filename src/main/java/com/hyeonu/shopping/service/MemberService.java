package com.hyeonu.shopping.service;

import com.hyeonu.shopping.domain.Authority;
import com.hyeonu.shopping.domain.Member;
import com.hyeonu.shopping.domain.MemberAuthority;
import com.hyeonu.shopping.dto.request.BrandApplyRequestDto;
import com.hyeonu.shopping.dto.request.SignupRequestDto;
import com.hyeonu.shopping.dto.type.RoleType;
import com.hyeonu.shopping.repository.AuthorityRepository;
import com.hyeonu.shopping.repository.MemberAuthorityRepository;
import com.hyeonu.shopping.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service @RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public boolean userSignup(SignupRequestDto dto) {
        String roleName = RoleType.USER.getName();

        // 패스워드 암호화
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        try {
            Member beforeSaving = new Member(dto);
            Member member = memberRepository.save(beforeSaving);
            Authority authority = authorityRepository.findByRoleName(roleName).get();
            MemberAuthority memberAuthority = new MemberAuthority(member, authority);
            memberAuthorityRepository.save(memberAuthority);
        }catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

        return true;

    }
//    public boolean checkPassword(String password, String password2) {
//        return !password.equals(password2);
//    }

//    public boolean userSignup(SignupRequestDto dto, List<String> authList, boolean isNonLocked) {
//        try {
//            Member memberBeforeSaving = new Member(
//                    dto.getUsername(),
//                    bCryptPasswordEncoder.encode(dto.getPassword()),
//                    LocalDateTime.now(),
//                    dto.getGender(),
//                    isNonLocked
//                    );
//
//            Member member = memberRepository.save(memberBeforeSaving);
//
//            for (String auth : authList) {
//                Authority authority = authorityRepository.findByRoleName(auth).get();
//
//                if (authority == null) {
//                    authority = authorityRepository.save(new Authority(auth));
//                }
//                memberAuthorityRepository.save(new MemberAuthority(member, authority));
//            }
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }

    public Member brandManagerSignup(BrandApplyRequestDto dto) {
        Member beforeSaving = new Member(
                dto.getUsername(),
                bCryptPasswordEncoder.encode(dto.getPassword()),
                LocalDateTime.now(),
                null,
                true
        );

        Member member = memberRepository.save(beforeSaving);
        for (String auth : List.of("ROLE_USER", "ROLE_BRAND")) {
            Authority authority = authorityRepository.findByRoleName(auth).orElse(null);

            if (authority == null) {
                authority = authorityRepository.save(new Authority(auth));
            }
            memberAuthorityRepository.save(new MemberAuthority(member, authority));
        }

        return member;
    }


}
