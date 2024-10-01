package com.example.CRUD_SpringBoot.dto;
import com.example.CRUD_SpringBoot.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CategoryDto {
     UUID id;
     String name;
     String description;
}
