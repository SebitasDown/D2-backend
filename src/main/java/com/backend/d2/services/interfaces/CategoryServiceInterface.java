package com.backend.d2.services.interfaces;

import com.backend.d2.models.CategoryModel;

public interface CategoryServiceInterface {
    CategoryModel create(CategoryModel categoryModel);
    CategoryModel update(CategoryModel categoryModel);
    boolean deleteById(Long id);
}
