package com.hyeonu.shopping.dto.request;

import lombok.Data;

@Data
public class BrandRejectionReasonRequest {
    private Long brandId;
    private String reason;
}
