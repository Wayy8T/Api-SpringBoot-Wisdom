package com.example.CRUD_SpringBoot.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
     UUID id;

    @Column(name = "name", nullable = false, length = 255)
     String name;

    @Column(name = "description")
     String description;

}