package com.hyeonu.shopping.controller;

import com.hyeonu.shopping.domain.Brand;
import com.hyeonu.shopping.dto.request.BrandApplyRequestDto;
import com.hyeonu.shopping.dto.response.ApiResponse;
import com.hyeonu.shopping.service.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final RedisUtils redisUtils;

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            redisUtils.deleteData(token);
        }
    }

}
