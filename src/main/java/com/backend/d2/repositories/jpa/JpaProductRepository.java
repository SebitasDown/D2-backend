package com.backend.d2.repositories.jpa;

import com.backend.d2.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName (String name);
}
