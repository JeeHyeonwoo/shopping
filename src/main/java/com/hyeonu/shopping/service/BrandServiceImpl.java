package com.hyeonu.shopping.service;

import com.hyeonu.shopping.domain.Authority;
import com.hyeonu.shopping.domain.Brand;
import com.hyeonu.shopping.domain.Member;
import com.hyeonu.shopping.domain.MemberAuthority;
import com.hyeonu.shopping.dto.request.BrandApplyRequestDto;
import com.hyeonu.shopping.dto.request.SignupRequestDto;
import com.hyeonu.shopping.repository.AuthorityRepository;
import com.hyeonu.shopping.repository.BrandRepository;
import com.hyeonu.shopping.repository.MemberAuthorityRepository;
import com.hyeonu.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class BrandServiceImpl implements BrandService{
    private final BrandRepository brandRepository;
    private final MemberService memberService;


    @Override
    public Brand brandApply(BrandApplyRequestDto dto) {
        Member member = memberService.brandManagerSignup(dto);
        Brand brand = Brand.of(dto.getBrandName(), 0, member);
        return brandRepository.save(brand);
    }
}
