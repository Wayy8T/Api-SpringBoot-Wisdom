package com.example.CRUD_SpringBoot.service.impl;
import com.example.CRUD_SpringBoot.dto.ProductDto;
import com.example.CRUD_SpringBoot.entity.Category;
import com.example.CRUD_SpringBoot.entity.Product;
import com.example.CRUD_SpringBoot.exception.AppException;
import com.example.CRUD_SpringBoot.exception.ErrorCode;
import com.example.CRUD_SpringBoot.mapper.CategoryMapper;
import com.example.CRUD_SpringBoot.mapper.ProductMapper;
import com.example.CRUD_SpringBoot.repository.CategoryRepository;
import com.example.CRUD_SpringBoot.repository.ProductRepository;
import com.example.CRUD_SpringBoot.service.ProductService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProductServiceImpl implements ProductService {
    @Autowired
     ProductRepository productRepository;
    @Autowired
     CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        // Tạo đối tượng Product
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategoryId(category.getId());  // Gán category vào product
        // Lưu product vào database
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
//        products.stream() chuyển đổi danh sách các sản phẩm thành một Stream để bạn có thể xử lý từng sản phẩm theo cách hàm.
//        Phương thức map() áp dụng một hàm cho từng phần tử của Stream và trả về một Stream mới với kết quả của hàm đó.
        return products.stream().map(product -> ProductMapper.mapToProductDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getDetailProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        return ProductMapper.mapToProductDto(product);
    }

//    tich hop voi create if(id == null )=> create else update
    @Override
    public ProductDto updateProduct(UUID productId, ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategoryId(category.getId());
        Product saveProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(saveProduct);
    }

    @Override
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        try {
            productRepository.deleteById(productId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<ProductDto> getProductsByCategory(UUID categoryId, int page) {
        int size = 6;
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> productPage = productRepository.findByCategoryId(categoryId , pageable);
        // Chuyển đổi Page<Product> thành Page<ProductDto>
        return productPage.map(ProductMapper::mapToProductDto);
    }

    @Override
    public Page<ProductDto> getPaginatedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map((ProductMapper::mapToProductDto));
    }

    @Override
    public ProductDto createOrUpdateController(UUID productId, ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        if(productId==null){
            // Tạo đối tượng Product
            Product product = new Product();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategoryId(category.getId());  // Gán category vào product
            // Lưu product vào database
            Product savedProduct = productRepository.save(product);
            return ProductMapper.mapToProductDto(savedProduct);
        } else {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setCategoryId(category.getId());
            Product saveProduct = productRepository.save(product);
            return ProductMapper.mapToProductDto(saveProduct);
        }
    }


}
