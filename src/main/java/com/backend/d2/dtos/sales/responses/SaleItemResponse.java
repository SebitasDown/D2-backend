package com.backend.d2.dtos.sales.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
// Es un DTO auxiliar, representa un solo producto dentro de SaleResponseDTO

// Contiene nombre del producto, cantidad, precio, subtotal
@Data
@NoArgsConstructor
public class SaleItemResponse {

    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price; // Precio unitario al momento de la venta
    private BigDecimal subtotal;

}
