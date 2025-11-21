package com.backend.d2.dtos.products.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductGetByIdResponseDTO {
    private Long id;
    private String name;
    private double price;
    private int stock;

    private Long categoryId;
    private String categoryName;

    private Long supplierId;
    private String supplierName;
}
