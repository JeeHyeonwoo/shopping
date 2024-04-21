package com.hyeonu.shopping.controller;

import com.hyeonu.shopping.dto.request.BrandRejectionReasonRequest;
import com.hyeonu.shopping.dto.response.ApiResponse;
import com.hyeonu.shopping.service.BrandService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final BrandService brandService;

    // 브랜드 승인
    @GetMapping("/brand-authorization")
    public ResponseEntity<ApiResponse> brandAuthorization(HttpServletRequest request, @RequestParam(value = "brandId") long brandId) {
        Map<String, Object> message = new HashMap<>();
        try {
            brandService.brandUpdateStatus(brandId, 1);
        } catch(EntityNotFoundException e) {
            message.put("result", "fail");
            message.put("error", "Not found brand id");
            return ResponseEntity.status(409).body(
                    ApiResponse.builder()
                            .localDateTime(LocalDateTime.now().toString())
                            .path(request.getRequestURI())
                            .message(message)
                            .build()
            );
        }
        message.put("result", "success");
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .localDateTime(LocalDateTime.now().toString())
                        .path(request.getRequestURI())
                        .message(message)
                        .build()
        );
    }

    // 브랜드 거절
    @PostMapping("/brand-refuse")
    public ResponseEntity<ApiResponse> brandRefuse(HttpServletRequest request,
                                                   @RequestBody BrandRejectionReasonRequest reasonRequest
                                                   ) {
        Map<String, Object> message = new HashMap<>();
        try {
            brandService.brandRefuse(reasonRequest.getBrandId(), reasonRequest.getReason());
        } catch(EntityNotFoundException e) {
            message.put("result", "fail");
            message.put("error", "Not found brand id");
            return ResponseEntity.status(409).body(
                    ApiResponse.builder()
                            .localDateTime(LocalDateTime.now().toString())
                            .path(request.getRequestURI())
                            .message(message)
                            .build()
            );
        }
        message.put("result", "success");
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .localDateTime(LocalDateTime.now().toString())
                        .path(request.getRequestURI())
                        .message(message)
                        .build()
        );
    }
}
