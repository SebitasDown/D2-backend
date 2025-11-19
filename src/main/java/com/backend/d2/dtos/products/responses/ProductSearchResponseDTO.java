package com.backend.d2.dtos.products.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResponseDTO {
    private Long id;
    private String name;
    private String barcode;
    private double price;
    private String category;
    private String supplier;
}
