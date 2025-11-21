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

    // =========================================
    // ENTITY → MODEL
    // =========================================
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "cashierId", target = "cashierId"),
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "subtotal", target = "subtotal"),
            @Mapping(source = "sale.id", target = "saleId")
    })
    ShoppingCarModel toModel(ShoppingCarEntity entity);

    List<ShoppingCarModel> toModelList(List<ShoppingCarEntity> entities);


    // =========================================
    // MODEL → ENTITY
    // =========================================
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(target = "product", ignore = true),  // se asigna en el service
            @Mapping(source = "cashierId", target = "cashierId"),
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "subtotal", target = "subtotal"),
            @Mapping(target = "sale", ignore = true)       // se asigna en el service
    })
    ShoppingCarEntity toEntity(ShoppingCarModel model);

    List<ShoppingCarEntity> toEntityList(List<ShoppingCarModel> models);


    // =========================================
    // ENTITY → ShoppingCarItemResponse
    // =========================================
    @Mappings({
            @Mapping(source = "id", target = "idShoppingCar"),
            @Mapping(source = "product.id", target = "idProduct"),
            @Mapping(source = "product.name", target = "productName"),
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
