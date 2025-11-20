package com.backend.d2.repositories.interfaces;

import com.backend.d2.models.CategoryModel;

import java.util.Optional;

public interface CategoryRepositoryInterface {
    CategoryModel save(CategoryModel categoryModel);
    Optional<CategoryModel> findById (Long id);
    CategoryModel update(CategoryModel categoryModel);
    boolean delteById(Long id);
}
