package com.backend.d2.dtos.shoppingCar.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

// Para Task-002 (Modificar cantidad): Solo necesitas la nueva cantidad

@Data
@NoArgsConstructor
public class UpdateCartItemQuantityRequest {

    @NotNull(message = "The quantity is mandatory")
    @Min(value = 1, message = "The quantity must be at least 1")
    private Integer quantity;
}
