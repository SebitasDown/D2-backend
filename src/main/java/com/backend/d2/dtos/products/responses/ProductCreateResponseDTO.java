package com.backend.d2.dtos.products.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateResponseDTO {
    private Long id;
    private Long categoryId;
    private Long supplierId;
    private String name;
    private String barcode;
    private BigDecimal price;
    private int stock;
    private String description;
}