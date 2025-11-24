package com.backend.d2.mappers;

import com.backend.d2.dtos.sales.responses.SaleItemResponse;
import com.backend.d2.dtos.sales.responses.SaleResponse;
import com.backend.d2.entity.SaleEntity;
import com.backend.d2.models.SaleModel;
import com.backend.d2.models.ShoppingCarModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ShoppingCarMapper.class})
public interface SaleMapper {

    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

    // ENTITY <-> MODEL
    SaleEntity toEntity(SaleModel model);
    SaleModel toModel(SaleEntity entity);

    // MODEL -> RESPONSE DTO
    @Mapping(source = "cashier.name", target = "cashierName")
    @Mapping(source = "saleItems", target = "items")
    SaleResponse toSaleResponse(SaleModel model);

    List<SaleResponse> toSaleResponseList(List<SaleModel> models);

    // Usamos el campo String 'productName' directo del modelo
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "productName", target = "productName")
    SaleItemResponse toSaleItemResponse(ShoppingCarModel itemModel);
}