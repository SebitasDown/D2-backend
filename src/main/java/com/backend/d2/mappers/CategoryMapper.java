package com.backend.d2.mappers;

import com.backend.d2.dtos.category.CategoryRequest;
import com.backend.d2.dtos.category.CategoryResponseDTO;
import com.backend.d2.entity.CategoryEntity;
import com.backend.d2.models.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryModel dtoToModel (CategoryRequest dtoRequest);

    CategoryEntity modelToEntity (CategoryModel categoryModel);

    CategoryModel entityToModel(CategoryEntity categoryEntity);

    CategoryResponseDTO modelToResponse(CategoryModel categoryModel);
}
