package com.backend.d2.repositories.jpa;

import com.backend.d2.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByBarcode (String barcode);

    // JOINs para mostrar mas informacion
    @EntityGraph(attributePaths = {"category", "supplier"})
    Optional<ProductEntity> findById(Long id);

    // JOINs para mostrar informacion
    @EntityGraph(attributePaths = {"category", "supplier"})
    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
