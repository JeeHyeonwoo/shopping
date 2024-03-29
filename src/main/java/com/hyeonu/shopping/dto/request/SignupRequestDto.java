package com.hyeonu.shopping.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class SignupRequestDto {
    @NotNull @NotBlank
    private String username;

    @NotNull @NotBlank
    private String password;

    @NotNull @NotBlank
    private String checkPassword;
}
