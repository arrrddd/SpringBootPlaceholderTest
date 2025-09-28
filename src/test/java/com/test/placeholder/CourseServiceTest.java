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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {
    @InjectMocks
    private CourseService service;
    @Mock
    private CourseRepository repository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void GetLatest(){
        Faker faker = new Faker();

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

        // Mock
        when(repository.findAllByOrderByCreationDateDesc(pageable)).thenReturn(coursePage);

        // Act
        Page<Course> result = service.GetLatestPage(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(coursePage, result); // exactly the same page returned
        verify(repository, times(1))
                .findAllByOrderByCreationDateDesc(pageable);

        LocalDateTime firstDate = result.getContent().get(0).getCreatedAt();
        LocalDateTime secondDate = result.getContent().get(1).getCreatedAt();
        assertTrue(firstDate.isAfter(secondDate) || firstDate.isEqual(secondDate));
    }
}
