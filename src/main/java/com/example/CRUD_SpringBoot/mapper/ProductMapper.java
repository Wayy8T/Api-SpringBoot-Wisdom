package com.example.CRUD_SpringBoot.mapper;
import com.example.CRUD_SpringBoot.dto.ProductDto;
import com.example.CRUD_SpringBoot.entity.Category;
import com.example.CRUD_SpringBoot.entity.Product;

public class ProductMapper {
    public static ProductDto mapToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(),product.getPrice(), product.getDescription(), product.getCategoryId());
    }
    public static Product mapToProduct(ProductDto productDto, Category category) {
        return new Product(productDto.getId(), productDto.getName(), productDto.getPrice(), productDto.getDescription(), productDto.getCategoryId());
    }
}
