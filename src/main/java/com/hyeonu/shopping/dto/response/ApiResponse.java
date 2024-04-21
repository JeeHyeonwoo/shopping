package com.hyeonu.shopping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse {
    private String localDateTime;
    private int Status;
    private Map<String, Object> message;
    private String path;


}
