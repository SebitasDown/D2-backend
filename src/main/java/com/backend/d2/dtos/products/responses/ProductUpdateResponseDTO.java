package com.backend.d2.dtos.products.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductUpdateResponseDTO {

    private Long categoryId;
    private Long supplierId;
    private String name;
    private String barcode;
    private double price;
    private int stock;
    private String description;
}
