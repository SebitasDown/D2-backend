package com.backend.d2.mappers;

import com.backend.d2.dtos.sales.responses.SaleItemResponse;
import com.backend.d2.dtos.sales.responses.SaleResponse;
import com.backend.d2.entity.SaleEntity;
import com.backend.d2.models.SaleModel;
// import com.backend.d2.models.ShoppingCarModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring") // para que funcione la inyección en el Repo
public interface SaleMapper {

    // Instancia estática por si la necesitas manualmente (como en ProductMapper)
    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);


    // Mapeo entre ENTIDAD (BD) y MODELO (Negocio)

    // De Modelo a Entidad (Para guardar: RepositoryImpl.save)
    SaleEntity toEntity(SaleModel model);

    // De Entidad a Modelo (Para leer: RepositoryImpl.findById)
    SaleModel toModel(SaleEntity entity);

    // Mapeo entre MODELO (Negocio) y DTO (Vista/API)

    // De Modelo a DTO de Respuesta (Para el Controlador)
    //@Mapping(source = "cashier.name", target = "cashierName")
    //@Mapping(source = "saleItems", target = "items")
    SaleResponse toSaleResponse(SaleModel model);

    List<SaleResponse> toSaleResponseList(List<SaleModel> models);

    // Metodo auxiliar para convertir items del carrito (Model) a items de respuesta (DTO)
    /*
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    SaleItemResponse toSaleItemResponse(ShoppingCarModel itemModel);
    */
}