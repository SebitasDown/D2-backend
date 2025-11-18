package com.backend.d2.mappers;

import com.backend.d2.dtos.products.request.ProductCreatedDTO;
import com.backend.d2.models.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

// Mapper para Product
@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Combierte de un DTO a un modelo
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "supplierId", target = "supplier.id")
    ProductModel toModel (ProductCreatedDTO dto);


    // Combierte de un Modelo a un DTO
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "supplier.id", target = "supplierId")
    ProductCreatedDTO toDTO (ProductModel model);

    // futuro de modelo a endidad y de entidad a modelo
}
