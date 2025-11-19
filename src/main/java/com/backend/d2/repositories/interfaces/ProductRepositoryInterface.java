package com.backend.d2.repositories.interfaces;

import com.backend.d2.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryInterface {
    ProductModel save(ProductModel productModel);

    Optional<ProductModel> findById(Long id);

    ProductModel update(ProductModel productModel);

    boolean deleteById(Long id);

    Page<ProductModel> findByName(String name, Pageable pageable);

    Page<ProductModel> findAll(Pageable pageable);

    Page<ProductModel> findByFilters(String name, Long categoryId, Long supplierId, Pageable pageable);


}