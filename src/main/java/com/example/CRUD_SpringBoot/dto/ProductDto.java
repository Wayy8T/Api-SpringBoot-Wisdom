package com.example.CRUD_SpringBoot.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProductDto {
     UUID id;
     String name;
     BigDecimal price;
     String description;
     UUID categoryId;
}
