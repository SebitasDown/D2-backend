package com.backend.d2.dtos.sales.requests;

import com.backend.d2.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// Recibe los datos para crear el dto de la venta (input)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessSaleRequest {

    private PaymentMethod cashMethod;

    private BigDecimal amountPaid; // money que entrega -> se calcula

}