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
    public ResponseEntity<ApiResponse> signup(HttpServletRequest request,
                                              @RequestBody @Valid SignupRequestDto dto,
                                              BindingResult result) {
        Map<String, Object> message = new HashMap<>();
        Map<String, String> errorList = new HashMap<>();

        // 유효성 검사 실패시
        if (result.hasErrors()) {
            for(FieldError error: result.getFieldErrors()) {
                errorList.put(error.getField(), error.getDefaultMessage());
            }
            message.put("errors", errorList);
            return ResponseEntity.status(400).body(
                    ApiResponse.builder()
                            .localDateTime(LocalDateTime.now().toString())
                            .path(request.getRequestURI())
                            .message(message)
                            .build()
            );
        }
        if (!memberService.userSignup(dto)) {
            message.put("result", "FAIL");
            return ResponseEntity.status(400).body(
                    ApiResponse.builder()
                            .localDateTime(LocalDateTime.now().toString())
                            .path(request.getRequestURI())
                            .message(message)
                            .build()
            );
        }

        message.put("result", "SUCCESS");
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .localDateTime(LocalDateTime.now().toString())
                        .path(request.getRequestURI())
                        .message(message)
                        .build()
        );
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
