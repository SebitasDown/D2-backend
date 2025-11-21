package com.backend.d2.dtos.shoppingCar.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

// Para Task-007 (Actualizar precio - SOLO ADMIN): Permite modificar el precio manualmente

@Data
@NoArgsConstructor
public class UpdateCartItemPriceRequest {

    @NotNull(message = "The price is mandatory")
    @Positive(message = "The price must be positive")
    private BigDecimal newPrice;
}