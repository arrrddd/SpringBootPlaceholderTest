package com.test.placeholder.services;

import com.test.placeholder.models.Course;
import com.test.placeholder.repositories.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository repository;

    public CourseService(CourseRepository repository){
        this.repository = repository;
    }

    public Page<Course> GetLatestPage(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return repository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Course createCourse(Course course) {
        course.setCreatedAt(LocalDateTime.now());
        course.setLastModifiedAt(LocalDateTime.now());
        return repository.save(course);
    }

    public Optional<Course> getCourseById(Integer id) {
        return repository.findById(id);
    }

    public Course updateCourse(Integer id, Course courseDetails) {
        return repository.findById(id).map(course -> {
            course.setName(courseDetails.getName());
            course.setDescription(courseDetails.getDescription());
            course.setAuthors(courseDetails.getAuthors());
            course.setSections(courseDetails.getSections());
            course.setLastModifiedAt(LocalDateTime.now());
            return repository.save(course);
        }).orElseThrow(() -> new RuntimeException("Course not found with id " + id));
    }

    public void deleteCourse(Integer id) {
        repository.deleteById(id);
    }

    public Page<Course> searchCourses(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }
}
