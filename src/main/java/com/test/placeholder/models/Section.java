package com.test.placeholder.models;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
@EqualsAndHashCode(callSuper = true)

@Entity @Data @NoArgsConstructor
@AllArgsConstructor @SuperBuilder
public class Section extends BaseEntity{

    String name;
    int sectionOrder;

    @ManyToOne @JoinColumn(name="course_id")
    Course course;
    @OneToMany(mappedBy = "section")
    List<Lecture> lectures;
}
