package com.backend.d2.dtos.shoppingCar.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

// Para Task-004 (Listar carrito): Necesitas devolver la lista de items Y el total general

@Data
@NoArgsConstructor
public class ShoppingCarItemResponse {

    private Long idShoppingCar; // ID del item en el carrito (para modificar/eliminar)
    private Long idProduct;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}