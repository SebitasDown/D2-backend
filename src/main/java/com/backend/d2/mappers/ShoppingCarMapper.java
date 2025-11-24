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

    // ENTITY (Objetos) → MODEL (IDs)
    @Mappings({
            // Extraemos los IDs de los objetos
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "cashier.id", target = "cashierId"),
            @Mapping(source = "sale.id", target = "saleId")
    })
    ShoppingCarModel toModel(ShoppingCarEntity entity);

    List<ShoppingCarModel> toModelList(List<ShoppingCarEntity> entities);

    // MODEL (IDs) → ENTITY (Objetos)
    @Mappings({
            // MapStruct creará: entity.setProduct(new ProductEntity(productId));
            @Mapping(source = "productId", target = "product.id"),
            @Mapping(source = "cashierId", target = "cashier.id"),
            @Mapping(source = "saleId", target = "sale.id")
    })
    ShoppingCarEntity toEntity(ShoppingCarModel model);

    List<ShoppingCarEntity> toEntityList(List<ShoppingCarModel> models);

    // ENTITY → ShoppingCarItemResponse
    @Mappings({
            @Mapping(source = "id", target = "idShoppingCar"),
            @Mapping(source = "product.id", target = "idProduct"),
            @Mapping(source = "product.name", target = "productName"),
            // quantity, price, subtotal son automáticos
    })
    ShoppingCarItemResponse toItemResponse(ShoppingCarEntity entity);

    List<ShoppingCarItemResponse> toItemResponseList(List<ShoppingCarEntity> entities);

    // LISTA DE ENTITIES → ShoppingCarResponse
    default ShoppingCarResponse toShoppingCarResponse(List<ShoppingCarEntity> entities) {

        ShoppingCarResponse response = new ShoppingCarResponse();

        List<ShoppingCarItemResponse> items = toItemResponseList(entities);
        response.setItems(items);

        response.setTotalItems(items.size());

        // Calculamos el total sumando los subtotales
        BigDecimal total = entities.stream()
                .map(ShoppingCarEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        response.setTotal(total != null ? total : BigDecimal.ZERO);

        return response;
    }
}