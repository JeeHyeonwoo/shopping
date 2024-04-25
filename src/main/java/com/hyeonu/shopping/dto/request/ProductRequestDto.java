package com.hyeonu.shopping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @AllArgsConstructor
public class ProductRequestDto {
    @Setter private Long categoryId;
    @Setter private String name;
    @Setter private int price;
    @Setter private String gender;

    private List<ProductInfoDto> productInfoDtoList;
    private List<MultipartFile> thumbnailList;
    private List<MultipartFile> productDetail;

}
