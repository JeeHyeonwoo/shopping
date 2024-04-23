package com.hyeonu.shopping.controller;

import com.hyeonu.shopping.domain.Category;
import com.hyeonu.shopping.dto.request.RequestCategoryDto;
import com.hyeonu.shopping.dto.request.UpdateCategoryDto;
import com.hyeonu.shopping.dto.response.ApiResponse;
import com.hyeonu.shopping.dto.response.CategoryResponseDto;
import com.hyeonu.shopping.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @RequestMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(HttpServletRequest request, @RequestBody RequestCategoryDto requestCategoryDto) {
        Map<String, Object> message = new HashMap<>();

        try {
            categoryService.createCategory(requestCategoryDto);
        }catch(Exception e) {
            message.put("error", e.getMessage());
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

    @RequestMapping("/update")
    public ResponseEntity<ApiResponse> updateCategory(HttpServletRequest request, @RequestBody UpdateCategoryDto categoryDto) {
        Map<String, Object> message = new HashMap<>();

        try {
            categoryService.updateCategory(categoryDto);
        } catch(Exception e) {
            message.put("error", "동일한 이름값을 입력하였거나, 부모키값이 정확하지 않습니다");
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

    @RequestMapping("/list")
    @ReadOnlyProperty
    public ResponseEntity<ApiResponse> readCategory(HttpServletRequest request, @RequestBody Map<String, Long> map){
        Map<String, Object> message = new HashMap<>();

        try {
            CategoryResponseDto category = categoryService.readCategory(map.get("id"));
            message.put("result", category);
        } catch(Exception e) {
            message.put("error", "Entity Not found exception");
            return ResponseEntity
                    .status(409)
                    .body(
                            ApiResponse.builder()
                                    .localDateTime(LocalDateTime.now().toString())
                                    .path(request.getRequestURI())
                                    .message(message)
                                    .build()
                    );
        }

        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .localDateTime(LocalDateTime.now().toString())
                        .path(request.getRequestURI())
                        .message(message)
                        .build()
        );
    }

    @RequestMapping("/delete")
    public void deleteCategory(@RequestBody Map<String, Long> map){
        categoryService.deleteCategory(map.get("id"));
    }
}
