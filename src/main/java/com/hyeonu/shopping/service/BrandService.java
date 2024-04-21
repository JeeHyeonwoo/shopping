package com.hyeonu.shopping.service;

import com.hyeonu.shopping.domain.Brand;
import com.hyeonu.shopping.dto.request.BrandApplyRequestDto;

public interface BrandService {
    Brand brandApply(BrandApplyRequestDto dto);
}
