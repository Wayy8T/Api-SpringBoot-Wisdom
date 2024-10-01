package com.example.CRUD_SpringBoot.service;
import com.example.CRUD_SpringBoot.dto.CategoryDto;
import com.example.CRUD_SpringBoot.dto.ProductDto;
import com.example.CRUD_SpringBoot.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getDetailProductById(UUID productId);
    ProductDto updateProduct(UUID productId, ProductDto productDto);
    void deleteProduct(UUID productId);
     Page<ProductDto> getProductsByCategory(UUID categoryId, int page );
     Page<ProductDto> getPaginatedProducts(int page, int size);
     ProductDto createOrUpdateController(UUID productId, ProductDto productDto);
}
