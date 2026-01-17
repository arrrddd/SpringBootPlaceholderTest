package com.test.placeholder;

import com.github.javafaker.Faker;
import com.test.placeholder.models.Author;
import com.test.placeholder.repositories.AuthorRepository;
import com.test.placeholder.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    @InjectMocks
    private AuthorService service;

    @Mock
    private AuthorRepository repository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void createAuthor_Success() {
        Author author = new Author();
        author.setFirstName(faker.name().firstName());
        author.setEmail(faker.internet().emailAddress());

        when(repository.findByEmail(author.getEmail())).thenReturn(Optional.empty());
        when(repository.save(any(Author.class))).thenReturn(author);

        Author created = service.createAuthor(author);

        assertNotNull(created);
        verify(repository, times(1)).save(author);
    }

    @Test
    public void createAuthor_DuplicateEmail_ThrowsException() {
        Author author = new Author();
        author.setEmail("test@example.com");

        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(author));

        assertThrows(RuntimeException.class, () -> service.createAuthor(author));
        verify(repository, never()).save(any(Author.class));
    }

    @Test
    public void getAuthorById_Found() {
        Author author = new Author();
        author.setId(Integer.valueOf(1));
        when(repository.findById(Integer.valueOf(1))).thenReturn(Optional.of(author));

        Optional<Author> found = service.getAuthorById(Integer.valueOf(1));

        assertTrue(found.isPresent());
        assertEquals(1, found.get().getId());
    }

    @Test
    public void updateAuthor_Success() {
        Author existing = new Author();
        existing.setId(Integer.valueOf(1));
        existing.setFirstName("Old");

        Author updateDetails = new Author();
        updateDetails.setFirstName("New");

        when(repository.findById(Integer.valueOf(1))).thenReturn(Optional.of(existing));
        when(repository.save(any(Author.class))).thenReturn(existing);

        Author updated = service.updateAuthor(Integer.valueOf(1), updateDetails);

        assertEquals("New", updated.getFirstName());
        verify(repository, times(1)).save(existing);
    }

    @Test
    public void searchByFirstName() {
        String name = "John";
        Author author = new Author();
        author.setFirstName("John");
        
        when(repository.findAllByFirstNameIgnoreCase(name)).thenReturn(List.of(author));

        List<Author> results = service.searchByFirstName(name);

        assertEquals(1, results.size());
        assertEquals("John", results.get(0).getFirstName());
    }
}
