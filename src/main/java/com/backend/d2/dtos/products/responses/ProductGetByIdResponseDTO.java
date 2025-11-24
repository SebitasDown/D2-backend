package com.backend.d2.dtos.products.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetByIdResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private int stock;

    private Long categoryId;
    private String categoryName;

    private Long supplierId;
    private String supplierName;
}