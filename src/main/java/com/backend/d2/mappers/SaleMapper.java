package com.backend.d2.mappers;

import com.backend.d2.dtos.sales.responses.SaleResponse;
import com.backend.d2.entity.Sale;
// import com.backend.d2.models.ShoppingCar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// Interfaz de MapStruct para convertir Entidades (Modelos) a DTOs
@Mapper(componentModel = "spring") // componentModel="spring" es clave para la inyección
public interface SaleMapper {

    // Convierte la entidad Sale en el "Modelo" al DTO SaleResponse
    // Mapeamos campos complejos manualmente

    @Mapping(source = "cashier.name", target = "cashierName") // Task-004 -> tooma el nombre del User
    @Mapping(source = "saleItems", target = "items")         // Toma la lista de ShoppingCar y la mapea a 'items'
    SaleResponse toSaleResponse(Sale sale);

    List<SaleResponse> toSaleResponseList(List<Sale> sales);

    // Convierte un item del carrito al DTO (SaleItem)
    //Esto es para la lista anidada dentro de SaleResponse

    // @Mapping(source = "product.id", target = "productId")
    // @Mapping(source = "product.name", target = "productName")
    // SaleItem toSaleItem(ShoppingCar item);

}
