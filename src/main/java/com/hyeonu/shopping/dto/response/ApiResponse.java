package com.hyeonu.shopping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse {
    private String localDateTime;
    private Map<String, Object> message;
    private String path;


}
