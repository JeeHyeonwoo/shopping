package com.hyeonu.shopping.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrandApplyRequestDto {
    @NotNull @NotBlank
    private String username;

    @NotNull @NotBlank
    private String password;

    @NotNull @NotBlank
    private String brandName;

}
