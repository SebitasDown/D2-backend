package com.backend.d2.dtos.products.responses;

import jakarta.validation.constraints.NotBlank;

public class ProductCreateResponseDTO {

    private Long id;
    private Long categoryId;
    private Long supplierId;
    private String name;
    private String barcode;
    private double price;
    private int stock;
    private String description;
}
