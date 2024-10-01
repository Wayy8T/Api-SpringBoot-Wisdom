package com.example.CRUD_SpringBoot.service.impl;

import com.example.CRUD_SpringBoot.dto.CategoryDto;
import com.example.CRUD_SpringBoot.entity.Category;
import com.example.CRUD_SpringBoot.exception.AppException;
import com.example.CRUD_SpringBoot.exception.ErrorCode;
import com.example.CRUD_SpringBoot.mapper.CategoryMapper;
import com.example.CRUD_SpringBoot.repository.CategoryRepository;
import com.example.CRUD_SpringBoot.service.CategoryService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CategoryServiceImpl implements CategoryService {
    @Autowired
     CategoryRepository categoryRepository;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.mapToCategory(categoryDto);
        Category saveCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCategoryDto(saveCategory);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> CategoryMapper.mapToCategoryDto(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getDetailCategoryById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        return CategoryMapper.mapToCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(UUID categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category saveCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCategoryDto(saveCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryDto createOrUpdateController(UUID categoryId, CategoryDto categoryDto) {
        if(categoryId==null){
            Category category = CategoryMapper.mapToCategory(categoryDto);
            Category saveCategory = categoryRepository.save(category);
            return CategoryMapper.mapToCategoryDto(saveCategory);
        } else {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            Category saveCategory = categoryRepository.save(category);
            return CategoryMapper.mapToCategoryDto(saveCategory);
        }
    }

}
