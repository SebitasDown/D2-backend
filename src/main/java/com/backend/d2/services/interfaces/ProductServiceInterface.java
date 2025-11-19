package com.backend.d2.services.interfaces;

import com.backend.d2.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServiceInterface {
    ProductModel create(ProductModel productModel);
    ProductModel updated(ProductModel productModel);
    ProductModel deleteById(Long id);
    ProductModel findById (Long id);
    Page<ProductModel> findByName(String name, Pageable pageable);
}
