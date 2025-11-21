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

// "uses" conecta este mapper con los otros que ya creaste
@Mapper(componentModel = "spring", uses = {UserMapper.class, ShoppingCarMapper.class})
public interface SaleMapper {

    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

    // ENTITY (BD) <-> MODEL (Negocio)

    // Al usar 'ShoppingCarMapper', MapStruct sabe convertir la lista automáticamente
    SaleEntity toEntity(SaleModel model);
    SaleModel toModel(SaleEntity entity);

    // MODEL (Negocio) -> DTO (Respuesta API)

    // MapStruct usará UserMapper para convertir UserModel -> UserResponseDTO si fuera necesario
    @Mapping(source = "cashier.name", target = "cashierName")
    @Mapping(source = "saleItems", target = "items")
    SaleResponse toSaleResponse(SaleModel model);

    List<SaleResponse> toSaleResponseList(List<SaleModel> models);

    // Métodos Auxiliares (Model -> ItemResponse)

    // Define cómo transformar un item del carrito en un item de respuesta de venta
    @Mapping(source = "productId.id", target = "productId")
    @Mapping(source = "productId.name", target = "productName")
    // quantity, price y subtotal se mapean solos porque se llaman igual
    SaleItemResponse toSaleItemResponse(ShoppingCarModel itemModel);
}