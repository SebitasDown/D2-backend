package com.backend.d2.services.impl;

import com.backend.d2.models.CategoryModel;
import com.backend.d2.repositories.interfaces.CategoryRepositoryInterface;
import com.backend.d2.services.interfaces.CategoryServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryServiceInterface {

    private final CategoryRepositoryInterface categoryRepositoryInterface;

    @Override
    public CategoryModel create(CategoryModel categoryModel) {
        if (categoryModel.getName() == null || categoryModel.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Los campos no pueden estar vacios"); // manejar errores diferente
        }
        if (categoryModel.getDescription() == null || categoryModel.getDescription().trim().isEmpty()){
            throw new IllegalArgumentException("Agrega una descripcion");
        }

        return categoryRepositoryInterface.save(categoryModel);
    }

    @Override
    public CategoryModel update(CategoryModel categoryModel) {
        if (categoryModel.getName() == null || categoryModel.getName().trim().isEmpty() || categoryModel.getDescription() == null || categoryModel.getDescription().trim().isEmpty() ){
            throw new IllegalArgumentException("No pueden estar vacios"); // Corregir errores globales
        }

        CategoryModel exist = categoryRepositoryInterface.findById(categoryModel.getId())
                .orElseThrow(()-> new IllegalArgumentException("No Encontrado")); // validar errores globales

        exist.setName(categoryModel.getName());
        exist.setDescription(categoryModel.getDescription());
        return categoryRepositoryInterface.update(exist);
    }

    @Override
    public boolean deleteById(Long id) {

        CategoryModel exist = categoryRepositoryInterface.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Error no existe la categoria"));
        boolean eliminado = categoryRepositoryInterface.delteById(id);
        if (!eliminado){
            throw new IllegalArgumentException("El producto no fue eliminado"); // error 400 por que tiene asociado varios productos
        }
        return true;
    }

    @Override
    public Page<CategoryModel> findAll(String name, Pageable pageable) {
        return categoryRepositoryInterface.findAll(name, pageable);
    }


}
