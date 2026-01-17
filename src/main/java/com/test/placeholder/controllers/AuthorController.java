package com.test.placeholder.controllers;

import com.test.placeholder.models.Author;
import com.test.placeholder.services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        try {
            return ResponseEntity.ok(service.createAuthor(author));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return service.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Integer id) {
        return service.getAuthorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Integer id, @RequestBody Author author) {
        try {
            return ResponseEntity.ok(service.updateAuthor(id, author));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        service.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/firstname")
    public List<Author> searchByFirstName(@RequestParam String name) {
        return service.searchByFirstName(name);
    }
    
    @GetMapping("/search/lastname")
    public List<Author> searchByLastName(@RequestParam String name) {
        return service.searchByLastName(name);
    }
}
