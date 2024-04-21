package com.hyeonu.shopping.controller;

import com.hyeonu.shopping.domain.Brand;
import com.hyeonu.shopping.dto.request.BrandApplyRequestDto;
import com.hyeonu.shopping.dto.request.SignupRequestDto;
import com.hyeonu.shopping.dto.response.ApiResponse;
import com.hyeonu.shopping.service.BrandService;
import com.hyeonu.shopping.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final BrandService brandService;

    @RequestMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto dto, BindingResult result) {
        StringBuilder errors = new StringBuilder();
        if (result.hasErrors()) {
            for(FieldError error: result.getFieldErrors()) {
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(", ");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }

        if (memberService.duplicationUsernameCheck(dto.getUsername())) {
            errors.append("username: 동일한 아이디가 존재합니다, ");
            return ResponseEntity.badRequest().body(errors.toString());
        }

        if (memberService.checkPassword((dto.getPassword()), dto.getCheckPassword())) {
            errors.append("password: 비밀번호가 동일하지 않습니다,");
            return ResponseEntity.badRequest().body(errors.toString());
        }

        if (memberService.userSignup(dto, List.of("ROLE_USER"), true)) {
            return ResponseEntity.badRequest().body(errors.toString());
        }

        return ResponseEntity.ok().body("login");
    }


    // 브랜드 신청
    @RequestMapping("/brand-apply")
    public ApiResponse createBrand(HttpServletRequest request, @RequestBody BrandApplyRequestDto dto) {
        Brand brand = brandService.brandApply(dto);
        Map<String, Object> map = new HashMap<>();
        if( brand == null ) {
            map.put("result", "FAIL");
            return ApiResponse.builder()
                    .localDateTime(LocalDateTime.now().toString())
                    .path(request.getRequestURI())
                    .message(map)
                    .build();
        }

        map.put("result", "SUCCESS");
        return ApiResponse.builder()
                .localDateTime(LocalDateTime.now().toString())
                .path(request.getRequestURI())
                .message(map)
                .build();
    }
}
