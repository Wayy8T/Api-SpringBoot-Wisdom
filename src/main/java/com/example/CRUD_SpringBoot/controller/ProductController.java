package com.example.CRUD_SpringBoot.controller;
import com.example.CRUD_SpringBoot.dto.ProductDto;
import com.example.CRUD_SpringBoot.dto.response.ApiResponse;
import com.example.CRUD_SpringBoot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> creatProduct(@RequestBody ProductDto productDto) {
        ProductDto saveProductDto = productService.createProduct(productDto);
//        HttpStatus.CREATED là mã trạng thái HTTP 201, cho biết rằng tài nguyên đã được tạo thành công
        return new ResponseEntity<>(saveProductDto, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtoList = productService.getAllProducts();
        return ResponseEntity.ok(productDtoList);
    };

    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> getDetailProductById(@PathVariable("productId") UUID productId) {
        ProductDto productDto = productService.getDetailProductById(productId);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") UUID productId, @RequestBody ProductDto productDto) {
        ProductDto updateProduct = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

//    FIX page
    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<Map<String, Object>> getProductsByCategory(
            @PathVariable UUID categoryId ,
            @RequestParam(defaultValue = "0") int page) {
//        Pageable pageable = PageRequest.of(page);
        Page<ProductDto> productPage = productService.getProductsByCategory(categoryId, page);
        Map<String, Object> response = new HashMap<>();
        response.put("totalPages", productPage.getTotalPages());
        response.put("size", productPage.getSize());
        response.put("totalElements", productPage.getTotalElements());
        response.put("currentPage", productPage.getNumber());
        response.put("products", productPage.getContent());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPaginatedProducts(
            @RequestParam int page,
            @RequestParam int size) {
        Page<ProductDto> productPage = productService.getPaginatedProducts(page, size);
        Map<String, Object> response = new HashMap<>();
        response.put("totalPages", productPage.getTotalPages());    // Tổng số trang
        response.put("size", productPage.getSize());               // Kích thước mỗi trang
        response.put("totalElements", productPage.getTotalElements()); // Tổng số phần tử
        response.put("currentPage", productPage.getNumber());       // Trang hiện tại
        response.put("products", productPage.getContent());        // Danh sách sản phẩm
        return ResponseEntity.ok(response);
    }

    @PostMapping({"/create-or-update/", "/create-or-update/{id}"})
    public ApiResponse<ProductDto> createOrUpdateController(@PathVariable(value = "id", required = false) UUID productId, @RequestBody ProductDto productDto){
        ProductDto result = productService.createOrUpdateController(productId, productDto);
        return ApiResponse.<ProductDto>builder()
                .result(result)
                .build();
    }
}
