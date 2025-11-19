package com.backend.d2.dtos.products.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDeleteDTO {
    private String name;
    private String message;
}
