package com.example.CRUD_SpringBoot.service;

import com.example.CRUD_SpringBoot.dto.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    List<CategoryDto> getAllCategories();
    CategoryDto getDetailCategoryById(UUID categoryId);
    CategoryDto updateCategory(UUID categoryId, CategoryDto categoryDto);
    void deleteCategory(UUID categoryId);
    CategoryDto createOrUpdateController(UUID categoryId, CategoryDto categoryDto);
}
