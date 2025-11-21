package com.backend.d2.dtos.sales.responses;

import com.backend.d2.entity.PaymentMethod;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.List;

// Este es el DTO principal que muestra la información completa de una venta
// La utilizo para Task-002, 003, 004, 005, 007

// contiene la información completa
@Data
@NoArgsConstructor
public class SaleResponse {

    private Long id;
    private String cashierName;
    private BigDecimal total;
    private PaymentMethod cashMethod;
    private LocalDate purchaseDate;
    private boolean isCancelled;
    private List<SaleItemResponse> items; // Lista de items vendidos (se hace con SaleItem)

}