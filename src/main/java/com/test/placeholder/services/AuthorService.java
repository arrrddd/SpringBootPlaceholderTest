package com.test.placeholder.services;

import com.test.placeholder.models.Author;
import com.test.placeholder.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author createAuthor(Author author) {
        if (repository.findByEmail(author.getEmail()).isPresent()) {
            throw new RuntimeException("Author with email " + author.getEmail() + " already exists");
        }
        author.setCreatedAt(LocalDateTime.now());
        author.setLastModifiedAt(LocalDateTime.now());
        return repository.save(author);
    }

    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    public Optional<Author> getAuthorById(Integer id) {
        return repository.findById(id);
    }

    public Author updateAuthor(Integer id, Author authorDetails) {
        return repository.findById(id).map(author -> {
            author.setFirstName(authorDetails.getFirstName());
            author.setLastName(authorDetails.getLastName());
            author.setAge(authorDetails.getAge());
            author.setEmail(authorDetails.getEmail());
            author.setLastModifiedAt(LocalDateTime.now());
            return repository.save(author);
        }).orElseThrow(() -> new RuntimeException("Author not found with id " + id));
    }

    public void deleteAuthor(Integer id) {
        repository.deleteById(id);
    }

    public List<Author> searchByFirstName(String firstName) {
        return repository.findAllByFirstNameIgnoreCase(firstName);
    }
    
    public List<Author> searchByLastName(String lastName) {
        return repository.findByLastNameContainingIgnoreCase(lastName);
    }
}
