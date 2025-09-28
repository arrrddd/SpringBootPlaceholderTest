package com.test.placeholder.repositories;

import com.test.placeholder.models.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text,Integer> {
}
