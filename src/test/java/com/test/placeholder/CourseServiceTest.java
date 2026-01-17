package com.test.placeholder;

import com.github.javafaker.Faker;
import com.test.placeholder.models.Course;
import com.test.placeholder.repositories.CourseRepository;
import com.test.placeholder.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseServiceTest {
    @InjectMocks
    private CourseService service;
    @Mock
    private CourseRepository repository;

    private Faker faker;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void GetLatest(){
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        Course course1 = new Course();
        course1.setId(1);
        course1.setName(faker.book().title());
        course1.setCreatedAt(LocalDateTime.now().minusDays(1));

        Course course2 = new Course();
        course2.setId(2);
        course2.setName(faker.book().title());
        course2.setCreatedAt(LocalDateTime.now().minusDays(2));

        List<Course> courseList = Arrays.asList(course1, course2);
        Page<Course> coursePage = new PageImpl<>(courseList, pageable, courseList.size());

        when(repository.findAllByOrderByCreatedAtDesc(pageable)).thenReturn(coursePage);

        Page<Course> result = service.GetLatestPage(page, size);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(repository, times(1)).findAllByOrderByCreatedAtDesc(pageable);
    }

    @Test
    public void createCourse() {
        Course course = new Course();
        course.setName("New Course");

        when(repository.save(any(Course.class))).thenReturn(course);

        Course created = service.createCourse(course);

        assertNotNull(created);
        assertNotNull(created.getCreatedAt()); // Should be set by service
        verify(repository, times(1)).save(course);
    }

    @Test
    public void getCourseById() {
        Course course = new Course();
        course.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(course));

        Optional<Course> found = service.getCourseById(1);

        assertTrue(found.isPresent());
        assertEquals(1, found.get().getId());
    }

    @Test
    public void updateCourse() {
        Course existing = new Course();
        existing.setId(1);
        existing.setName("Old Name");

        Course updateDetails = new Course();
        updateDetails.setName("New Name");

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(any(Course.class))).thenReturn(existing);

        Course updated = service.updateCourse(1, updateDetails);

        assertEquals("New Name", updated.getName());
        assertNotNull(updated.getLastModifiedAt());
        verify(repository, times(1)).save(existing);
    }

    @Test
    public void deleteCourse() {
        doNothing().when(repository).deleteById(1);

        service.deleteCourse(1);

        verify(repository, times(1)).deleteById(1);
    }

    @Test
    public void searchCourses() {
        String query = "Java";
        Pageable pageable = PageRequest.of(0, 10);
        Course course = new Course();
        course.setName("Java Programming");
        Page<Course> page = new PageImpl<>(List.of(course));

        when(repository.findByNameContainingIgnoreCase(query, pageable)).thenReturn(page);

        Page<Course> result = service.searchCourses(query, 0, 10);

        assertEquals(1, result.getContent().size());
        assertEquals("Java Programming", result.getContent().get(0).getName());
    }
}
