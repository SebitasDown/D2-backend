package com.backend.d2.services.interfaces;

import com.backend.d2.models.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryServiceInterface {
    CategoryModel create(CategoryModel categoryModel);
    CategoryModel update(CategoryModel categoryModel);
    boolean deleteById(Long id);
    Page<CategoryModel> findAll (String name, Pageable pageable);
    Page<CategoryModel> listAll(Pageable pageable);
}
