package com.backend.d2.mappers;

import com.backend.d2.entity.ShoppingCarEntity;
import com.backend.d2.models.ShoppingCarModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShoppingCarMapper {

    // ============================
    // ENTITY → MODEL
    // ============================
    @Mapping(source = "product", target = "product")
    @Mapping(source = "sale", target = "sale")
    @Mapping(source = "cashierId", target = "cashierId")
    ShoppingCarModel toModel(ShoppingCarEntity entity);

    List<ShoppingCarModel> toModelList(List<ShoppingCarEntity> entities);


    // ============================
    // MODEL → ENTITY
    // ============================
    @Mapping(source = "product", target = "product")
    @Mapping(source = "sale", target = "sale")
    @Mapping(source = "cashierId", target = "cashierId")
    ShoppingCarEntity toEntity(ShoppingCarModel model);

    List<ShoppingCarEntity> toEntityList(List<ShoppingCarModel> models);
}
