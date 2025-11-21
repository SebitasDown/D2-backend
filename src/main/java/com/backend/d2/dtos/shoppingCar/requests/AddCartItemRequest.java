package com.backend.d2.dtos.shoppingCar.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

// Para Task-001 (Agregar producto): Necesitas saber qué producto y cuánto agregar

@Data
@NoArgsConstructor
public class AddCartItemRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "The quantity is mandatory")
    @Min(value = 1, message = "The quantity must be at least 1")
    private Integer quantity;
}