package com.test.placeholder.models;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity @Data @NoArgsConstructor
@AllArgsConstructor @SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name = "resource_type") //for single table
public class Resource{

    @Id @GeneratedValue
            Integer id;
    String name;
    int size;
    String url;
    @OneToOne @JoinColumn(name = "lecture_id")
    Lecture lecture;
}
