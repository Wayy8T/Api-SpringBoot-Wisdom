package com.example.CRUD_SpringBoot.controller;
import com.example.CRUD_SpringBoot.dto.CategoryDto;
import com.example.CRUD_SpringBoot.dto.response.ApiResponse;
import com.example.CRUD_SpringBoot.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    @PostMapping("/add-category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto saveCategoryDto = categoryService.createCategory(categoryDto);
//        HttpStatus.CREATED là mã trạng thái HTTP 201, cho biết rằng tài nguyên đã được tạo thành công
        return new ResponseEntity<>(saveCategoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/all-category")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication1: " + authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info("GrantedAuthority: " + grantedAuthority.getAuthority()));
        log.info("Authorities: " + authentication.getAuthorities());
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        return ResponseEntity.ok(categoryDtoList);
    };

    @GetMapping("/detail-category/{categoryId}")
    public ResponseEntity<CategoryDto> getDetailCategoryById(@PathVariable UUID categoryId) {
        CategoryDto category = categoryService.getDetailCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/edit-category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") UUID categoryId, @RequestBody CategoryDto categoryDto) {
        CategoryDto updateCategory = categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity.ok(updateCategory);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @PostMapping({"/create-or-update/", "/create-or-update/{id}"})
    public ApiResponse<CategoryDto> createOrUpdateController(@PathVariable(value = "id", required = false) UUID categoryId, @RequestBody CategoryDto categoryDto){
        CategoryDto result = categoryService.createOrUpdateController(categoryId, categoryDto);
        return ApiResponse.<CategoryDto>builder()
                .result(result)
                .build();
    }
}
