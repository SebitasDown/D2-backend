package com.backend.d2.repositories.impl;

import com.backend.d2.entity.CategoryEntity;
import com.backend.d2.mappers.CategoryMapper;
import com.backend.d2.models.CategoryModel;
import com.backend.d2.repositories.interfaces.CategoryRepositoryInterface;
import com.backend.d2.repositories.jpa.JpaCategoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryInterface {

    // Inyeccion de Jpa
    private final JpaCategoryInterface jpaCategoryInterface;

    @Override
    public CategoryModel save(CategoryModel categoryModel) {
        // Validacion que el nombre sea unico
        if (jpaCategoryInterface.existsByName(categoryModel.getName())){
            throw new IllegalArgumentException("Ya existe esta categoria"); // no se puede crear ya existe
        }

        CategoryEntity entity = CategoryMapper.INSTANCE.modelToEntity(categoryModel);
        CategoryEntity saved = jpaCategoryInterface.save(entity);

        return CategoryMapper.INSTANCE.entityToModel(saved);
    }

    @Override
    public Optional<CategoryModel> findById(Long id) {
        return jpaCategoryInterface.findById(id)
                .map(CategoryMapper.INSTANCE::entityToModel);
    }

    @Override
    public CategoryModel update(CategoryModel categoryModel) {
        if (jpaCategoryInterface.existsByName(categoryModel.getName())){
            throw new IllegalArgumentException("Ya existe una categoria con este nombre");
        }

        CategoryEntity category = CategoryMapper.INSTANCE.modelToEntity(categoryModel);
        CategoryEntity update = jpaCategoryInterface.save(category);
        return CategoryMapper.INSTANCE.entityToModel(update);
    }

    @Override
    public boolean delteById(Long id) {
        jpaCategoryInterface.deleteById(id);

        // recordar Validar error DataIntegrytyViolationException 
        return true;
    }
}
