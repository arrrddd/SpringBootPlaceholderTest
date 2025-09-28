package com.test.placeholder.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity @Data @NoArgsConstructor
@AllArgsConstructor @SuperBuilder
public class BaseEntity {
    @Id @GeneratedValue
    Integer id;
    LocalDateTime createdAt;
    LocalDateTime lastModifiedAt;
    String createdBy;
    String lastModifiedBy;
}
