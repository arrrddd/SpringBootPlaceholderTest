package com.test.placeholder.controllers;

import com.test.placeholder.models.Course;
import com.test.placeholder.services.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private final CourseService service;

    public CourseController(CourseService service){

        this.service = service;
    }

    @GetMapping("/courses/latest")
    public List<Course> GetLatest(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return service.GetLatestPage(page,size).getContent();
    }
}
