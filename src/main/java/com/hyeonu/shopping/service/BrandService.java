package com.hyeonu.shopping.service;

import com.hyeonu.shopping.domain.*;
import com.hyeonu.shopping.dto.request.BrandApplyRequestDto;
import com.hyeonu.shopping.dto.request.SignupRequestDto;
import com.hyeonu.shopping.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class BrandService{
    private final BrandRepository brandRepository;
    private final BrandRejectionReasonRepository brandRejectionReasonRepository;
    private final MemberService memberService;

    public Brand brandApply(BrandApplyRequestDto dto) {
        Member member = memberService.brandManagerSignup(dto);
        Brand brand = Brand.of(dto.getBrandName(), 0, member);
        return brandRepository.save(brand);
    }

    public Brand brandUpdateStatus(long brandId, int status) throws EntityNotFoundException{
        Brand brand = brandRepository.findById(brandId).get();
        brand.setStatus(status);
        return brand;
    }

    public void brandRefuse(long brandId, String reasons) throws EntityNotFoundException{
        Brand brand = brandUpdateStatus(brandId, -1);
        BrandRejectionReason brandRejectionReason = brand.getBrandRejectionReasons();
        if (brandRejectionReason == null) {
            brandRejectionReason = new BrandRejectionReason(reasons, brand);
            brandRejectionReasonRepository.save(brandRejectionReason);
        }else {
            brandRejectionReason.setMessage(reasons);
        }

    }


}
