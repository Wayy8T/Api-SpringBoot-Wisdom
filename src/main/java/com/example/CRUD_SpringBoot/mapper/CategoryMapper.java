package com.example.CRUD_SpringBoot.mapper;
import com.example.CRUD_SpringBoot.dto.CategoryDto;
import com.example.CRUD_SpringBoot.entity.Category;

public class CategoryMapper {
    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName(), category.getDescription());
    }
    public static Category mapToCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName(), categoryDto.getDescription());
    }
}
