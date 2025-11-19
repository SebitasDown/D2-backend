package com.backend.d2.mappers;

import com.backend.d2.dtos.products.request.ProductCreatedDTO;
import com.backend.d2.dtos.products.responses.ProductCreateResponseDTO;
import com.backend.d2.dtos.products.responses.ProductGetByIdResponseDTO;
import com.backend.d2.dtos.products.responses.ProductSearchResponseDTO;
import com.backend.d2.dtos.products.responses.ProductUpdateResponseDTO;
import com.backend.d2.entity.ProductEntity;
import com.backend.d2.models.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

// Mapper para Product
@Mapper
public interface ProductMapper {

    // Revisar Mappers a futuro Task:008 : Testing

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Combierte de un DTO a un modelo - Crear Producto
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "supplierId", target = "supplier.id")
    ProductModel toModel (ProductCreatedDTO dto);

    // Comvierte de un Modelo a un DTO
    // Respuesta en DTO
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "supplier.id", target = "supplierId")
    ProductCreateResponseDTO toResponseCreateDTO(ProductModel model);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "supplier.id", target = "supplierId")
    ProductUpdateResponseDTO toResponseUpdatedDTO(ProductModel model);

    // Comvierte de una Entidad a un Modelo
    ProductModel toEModel(ProductEntity productEntity);

    // Comvierte de un Modelo a una Entidad
    ProductEntity toEntity(ProductModel productModel);

    // comvierte a Dto Response
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    ProductGetByIdResponseDTO toGetDTO(ProductModel model);

    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "supplier.name", target = "supplier")
    ProductSearchResponseDTO toSearchDTO(ProductModel model);

}
