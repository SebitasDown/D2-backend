package com.backend.d2.dtos.shoppingCar.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

// Segundo, el DTO "envoltorio" que tiene la lista y el total general (como pide la Task-004 y 005)

@Data
@NoArgsConstructor
public class ShoppingCarResponse {

    private List<ShoppingCarItemResponse> items;
    private Integer totalItems; // Cantidad de productos distintos
    private BigDecimal total;   // Suma de todos los subtotales
    private String message;     // Útil para Task-005 "Carrito vaciado" o Task-009 Warnings
}
