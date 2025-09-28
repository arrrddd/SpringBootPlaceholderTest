package com.test.placeholder.services;

import com.test.placeholder.models.Course;
import com.test.placeholder.repositories.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository repository;

    public CourseService(CourseRepository repository){
        this.repository = repository;
    }

    public Page<Course> GetLatestPage(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return repository.findAllByOrderByCreationDateDesc(pageable);
    }
}
