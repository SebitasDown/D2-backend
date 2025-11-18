package com.backend.d2.dtos.sales.requests;

import com.backend.d2.dtos.sales.responses.SaleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// ------------------------------------------------------------
// Para RECIBIR la solicitud de procesar una venta (Task-001)
// ------------------------------------------------------------

// Recibe los datos para crear el dto de la venta
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessSaleRequest {

    private Long saleId;
    private BigDecimal change;
    private SaleResponse saleDetails;

}