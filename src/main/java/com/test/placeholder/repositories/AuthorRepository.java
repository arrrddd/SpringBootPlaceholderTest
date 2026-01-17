package com.test.placeholder.repositories;

import com.test.placeholder.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    List<Author> findAllByFirstNameIgnoreCase(String name);
    Optional<Author> findByEmail(String email);
    List<Author> findByLastNameContainingIgnoreCase(String lastName);
}
