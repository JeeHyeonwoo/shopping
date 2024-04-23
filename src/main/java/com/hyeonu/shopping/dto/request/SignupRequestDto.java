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

    private boolean gender;

    private String address1;
    private String address2;
    private String address3;
    private String addressDetail;
//
//    public SignupRequestDto(@NotNull @NotBlank String username, @NotNull @NotBlank String password, Boolean gender) {
//        this.username = username;
//        this.password = password;
//        this.gender = gender;
//    }

}
