package com.hyeonu.shopping.service;

import com.hyeonu.shopping.domain.Category;
import com.hyeonu.shopping.dto.request.RequestCategoryDto;
import com.hyeonu.shopping.dto.request.UpdateCategoryDto;
import com.hyeonu.shopping.dto.response.CategoryResponseDto;
import com.hyeonu.shopping.repository.CategoryRepository;
import com.hyeonu.shopping.repository.querydsl.CategoryRepositoryCustomImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryRepositoryCustomImpl categoryRepositoryCustom;

    @Transactional
    public void createCategory(RequestCategoryDto requestCategoryDto){
        try {
            Category category = Category.of(requestCategoryDto.getName(), requestCategoryDto.getParent());
            Category result = categoryRepository.save(category);

            for(RequestCategoryDto categoryDto : requestCategoryDto.getChildren()) {
                categoryDto.setParent(result.getId());
                createCategory(categoryDto);
            }
        } catch(Exception e) {
            log.error("CategoryService createCategory exception: " + e.getMessage());
        }
    }

    @Transactional
    public void updateCategory(UpdateCategoryDto updateCategoryDto){
        try {
            Category category = categoryRepository.findById(updateCategoryDto.getId()).orElseThrow(()-> new UsernameNotFoundException("not found category"));
            category.setName(updateCategoryDto.getName());
            category.setParentId(updateCategoryDto.getParent());
        } catch(Exception e) {
            log.error("CategoryService updateCategory exception: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public CategoryResponseDto readCategory(Long id) throws EntityNotFoundException{
        Category category = categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("존재하지 않는 엔티티입니다"));
        return new CategoryResponseDto(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("존재하지 않는 카테고리입니다"));
        categoryRepositoryCustom.delete(category);
    }

}
