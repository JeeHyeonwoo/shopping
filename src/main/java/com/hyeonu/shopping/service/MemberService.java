package com.hyeonu.shopping.service;

import com.hyeonu.shopping.domain.Authority;
import com.hyeonu.shopping.domain.Member;
import com.hyeonu.shopping.domain.MemberAuthority;
import com.hyeonu.shopping.dto.request.SignupRequestDto;
import com.hyeonu.shopping.repository.AuthorityRepository;
import com.hyeonu.shopping.repository.MemberAuthorityRepository;
import com.hyeonu.shopping.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @Getter @RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean duplicationUsernameCheck(String username) {
        return memberRepository.findByUsername(username).isPresent();
    }

    public boolean checkPassword(String password, String password2) {
        return !password.equals(password2);
    }

    public boolean signup(SignupRequestDto dto) {
        try {
            Member member = memberRepository.save(
                    new Member(dto.getUsername(),
                            bCryptPasswordEncoder.encode(dto.getPassword())
                    )
            );

            //test
            Optional<Authority> authority = authorityRepository.findByRoleName("customer");
            Authority auth;
            if (authority.isEmpty()) {
                auth = new Authority("customer");
                authorityRepository.save(auth);
            }else {
                auth = authority.get();
            }

            memberAuthorityRepository.save(new MemberAuthority(member, auth));

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
