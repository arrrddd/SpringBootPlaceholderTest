package com.test.placeholder.controllers;

import com.test.placeholder.models.Course;
import com.test.placeholder.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService service;

    public CourseController(CourseService service){
        this.service = service;
    }

    @GetMapping("/latest")
    public List<Course> GetLatest(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return service.GetLatestPage(page,size).getContent();
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return service.createCourse(course);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
        return service.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        try {
            return ResponseEntity.ok(service.updateCourse(id, course));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer id) {
        service.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Course> searchCourses(@RequestParam String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return service.searchCourses(name, page, size).getContent();
    }
}
