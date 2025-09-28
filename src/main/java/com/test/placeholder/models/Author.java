package com.test.placeholder.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity @NoArgsConstructor @Data @AllArgsConstructor @SuperBuilder
public class Author extends BaseEntity {
    String firstName;
    String lastName;
    @Column(unique = true,nullable = false)
    String email;
    int age;

    @ManyToMany(mappedBy = "authors")
    List<Course> courses;
}
