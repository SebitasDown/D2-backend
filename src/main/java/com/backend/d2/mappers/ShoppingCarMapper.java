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

    // ENTITY -> MODEL
    @Mappings({
            @Mapping(source = "product.id", target = "productId"),
            // CLAVE: Sacamos el nombre del objeto ProductEntity y lo ponemos en el String del modelo
            @Mapping(source = "product.name", target = "productName"),

            @Mapping(source = "cashier.id", target = "cashierId"),
            @Mapping(source = "sale.id", target = "saleId")
    })
    ShoppingCarModel toModel(ShoppingCarEntity entity);

    List<ShoppingCarModel> toModelList(List<ShoppingCarEntity> entities);


    // MODEL -> ENTITY
    @Mappings({
            @Mapping(source = "productId", target = "product.id"),
            @Mapping(source = "cashierId", target = "cashier.id"),
            @Mapping(source = "saleId", target = "sale.id"),

            // Ignoramos los objetos completos en el mapeo inverso para no borrar datos por accidente
            @Mapping(target = "product", ignore = true),
            @Mapping(target = "cashier", ignore = true),
            @Mapping(target = "sale", ignore = true)
    })
    ShoppingCarEntity toEntity(ShoppingCarModel model);

    // Asignación manual de IDs para asegurar que JPA entienda las relaciones
    @AfterMapping
    default void mapIdsToEntities(@MappingTarget ShoppingCarEntity entity, ShoppingCarModel model) {
        if (model.getProductId() != null && entity.getProduct() == null) {
            com.backend.d2.entity.ProductEntity p = new com.backend.d2.entity.ProductEntity();
            p.setId(model.getProductId());
            entity.setProduct(p);
        }
        if (model.getCashierId() != null && entity.getCashier() == null) {
            com.backend.d2.entity.UserEntity u = new com.backend.d2.entity.UserEntity();
            u.setId(model.getCashierId());
            entity.setCashier(u);
        }
        if (model.getSaleId() != null && entity.getSale() == null) {
            com.backend.d2.entity.SaleEntity s = new com.backend.d2.entity.SaleEntity();
            s.setId(model.getSaleId());
            entity.setSale(s);
        }
    }

    List<ShoppingCarEntity> toEntityList(List<ShoppingCarModel> models);


    // ENTITY -> RESPONSE (Para el endpoint de ver carrito)
    @Mappings({
            @Mapping(source = "id", target = "idShoppingCar"),
            @Mapping(source = "product.id", target = "idProduct"),
            @Mapping(source = "product.name", target = "productName")
    })
    ShoppingCarItemResponse toItemResponse(ShoppingCarEntity entity);

    List<ShoppingCarItemResponse> toItemResponseList(List<ShoppingCarEntity> entities);

    default ShoppingCarResponse toShoppingCarResponse(List<ShoppingCarEntity> entities) {
        ShoppingCarResponse response = new ShoppingCarResponse();
        List<ShoppingCarItemResponse> items = toItemResponseList(entities);
        response.setItems(items);
        response.setTotalItems(items.size());

        BigDecimal total = entities.stream()
                .map(ShoppingCarEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotal(total != null ? total : BigDecimal.ZERO);

        return response;
    }
}