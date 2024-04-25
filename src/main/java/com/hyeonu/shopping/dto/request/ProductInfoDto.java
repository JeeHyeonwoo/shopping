package com.hyeonu.shopping.dto.request;

import lombok.Data;

@Data
public class ProductInfoDto {
    private String name;
    private int price;
    private String size;
    private String color;
    private int amount;
}
