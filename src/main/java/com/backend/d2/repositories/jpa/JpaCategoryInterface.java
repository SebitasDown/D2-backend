package com.backend.d2.repositories.jpa;

import com.backend.d2.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryInterface extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName (String name);
}
