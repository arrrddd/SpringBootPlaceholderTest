package com.test.placeholder.models;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
@EqualsAndHashCode(callSuper = true)

@Entity @Data @NoArgsConstructor
@AllArgsConstructor @SuperBuilder
public class Lecture extends BaseEntity{

    String name;

    @ManyToOne @JoinColumn(name = "section_id")
    Section section;
    @OneToOne @JoinColumn(name = "resource_id")
    Resource resource;
}
