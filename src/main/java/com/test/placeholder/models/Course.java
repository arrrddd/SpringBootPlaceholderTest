package com.test.placeholder.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
@EqualsAndHashCode(callSuper = true)

@Entity @Data @NoArgsConstructor
@AllArgsConstructor @SuperBuilder
public class Course extends BaseEntity{

    String name;
    String description;

    @ManyToMany @JoinTable(name = "authors_courses"
    ,joinColumns = {@JoinColumn(name = "courses_id")}, inverseJoinColumns = {@JoinColumn(name = "authors_id")})
    List<Author> authors;
    @OneToMany(mappedBy = "course")
    List<Section> sections;
}
