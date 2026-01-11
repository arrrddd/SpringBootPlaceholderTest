package com.test.placeholder.repositories;

import com.test.placeholder.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    Page<Course> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
