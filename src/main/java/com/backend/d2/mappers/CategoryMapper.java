package com.backend.d2.mappers;

import com.backend.d2.dtos.category.CategoryRequest;
import com.backend.d2.dtos.category.CategoryResponse;
import com.backend.d2.entity.Category;
import com.backend.d2.models.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryModel dtoToModel (CategoryRequest dtoRequest);

    Category modelToEntity (CategoryModel categoryModel);

    CategoryModel entityToModel(Category categoryEntity);

    CategoryResponse modelToResponse(CategoryModel categoryModel);
}
