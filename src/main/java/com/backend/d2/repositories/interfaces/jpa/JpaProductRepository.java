package com.backend.d2.repositories.interfaces.jpa;

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

    // Pageable para buscar a todos
    Page<ProductEntity> findAll (Pageable pageable);

    Page<ProductEntity> findByNameContainingIgnoreCaseAndCategoryId(String name, Long categoryId, Pageable pageable);

    Page<ProductEntity> findByNameContainingIgnoreCaseAndSupplierId(String name, Long supplierId, Pageable pageable);

    Page<ProductEntity> findByNameContainingIgnoreCaseAndCategoryIdAndSupplierId(String name, Long categoryId, Long supplierId, Pageable pageable);
}
