package com.backend.d2.dtos.products.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive; // Buena práctica para precios
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

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

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal price;

    private int stock;
    private String description;
}