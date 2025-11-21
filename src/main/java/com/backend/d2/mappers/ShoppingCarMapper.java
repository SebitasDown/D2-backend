package com.backend.d2.mappers;

import com.backend.d2.dtos.shoppingCar.responses.ShoppingCarItemResponse;
import com.backend.d2.dtos.shoppingCar.responses.ShoppingCarResponse;
import com.backend.d2.entity.ShoppingCarEntity;
import com.backend.d2.models.ShoppingCarModel;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ShoppingCarMapper {

    // ============================
    // ENTITY → MODEL
    // ============================
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "productId", target = "productId"),   // Mapea el OBJETO completo
            @Mapping(source = "cashierId", target = "cashierId"),   // Igual OBJETO completo
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "subtotal", target = "subtotal"),
            @Mapping(source = "sale.id", target = "saleId")
    })
    ShoppingCarModel toModel(ShoppingCarEntity entity);

    List<ShoppingCarModel> toModelList(List<ShoppingCarEntity> entities);


    // ============================
    // MODEL → ENTITY
    // ============================
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(target = "productId", ignore = true), // Se setea en el service
            @Mapping(target = "cashierId", ignore = true), // Igual se setea en el service
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "subtotal", target = "subtotal"),
            @Mapping(target = "sale", ignore = true)
    })
    ShoppingCarEntity toEntity(ShoppingCarModel model);

    List<ShoppingCarEntity> toEntityList(List<ShoppingCarModel> models);


    // ============================
    // ENTITY → ITEM RESPONSE
    // ============================
    @Mappings({
            @Mapping(source = "id", target = "idShoppingCar"),
            @Mapping(source = "productId.id", target = "idProduct"),
            @Mapping(source = "productId.name", target = "productName"),
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "subtotal", target = "subtotal")
    })
    ShoppingCarItemResponse toItemResponse(ShoppingCarEntity entity);

    List<ShoppingCarItemResponse> toItemResponseList(List<ShoppingCarEntity> entities);


    // =========================================
    // LISTA DE ENTITIES → ShoppingCarResponse
    // =========================================
    default ShoppingCarResponse toShoppingCarResponse(List<ShoppingCarEntity> entities) {

        ShoppingCarResponse response = new ShoppingCarResponse();

        List<ShoppingCarItemResponse> items = toItemResponseList(entities);
        response.setItems(items);

        response.setTotalItems(items.size());

        BigDecimal total = entities.stream()
                .map(ShoppingCarEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        response.setTotal(total);

        return response;
    }
}
