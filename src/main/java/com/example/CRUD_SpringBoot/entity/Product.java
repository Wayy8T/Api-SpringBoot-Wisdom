package com.example.CRUD_SpringBoot.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
     UUID id;

    @Column(name = "name", nullable = false, length = 255)
     String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
     BigDecimal price;

    @Column(name = "description")
     String description;

    @Column(name = "category_id")
     UUID categoryId;
}