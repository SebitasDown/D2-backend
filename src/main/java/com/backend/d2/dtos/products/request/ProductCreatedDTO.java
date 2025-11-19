package com.backend.d2.dtos.products.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreatedDTO {


    private Long categoryId;
    private Long supplierId;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El código de barras es obligatorio")
    private String barcode;


    private double price;
    private int stock;
    private String description;

}
