package com.hyeonu.shopping.dto.response;

import com.hyeonu.shopping.domain.Category;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryResponseDto {
    private Long id;
    private String name;
    private Long parentId;
    private List<CategoryResponseDto> children = new ArrayList<>();

    public CategoryResponseDto(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parentId = category.getParentId();
        for (Category ct: category.getChildren()) {
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto(ct.getId(), ct.getName(), ct.getParentId());
            children.add(categoryResponseDto);
        }
    }
}
