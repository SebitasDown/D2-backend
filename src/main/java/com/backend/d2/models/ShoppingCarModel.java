package com.backend.d2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCarModel {

    private Long id;

    private Long productId;

    private Long cashierId;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal subtotal;

    private Long saleId;  // null mientras no se haya procesado la venta
}
