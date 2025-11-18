package com.backend.d2.dtos.sales.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// ------------------------------------------------------------
// Para RESPONDER a la solicitud de procesar venta (Task-001)
// ------------------------------------------------------------

// El ID de la venta, el cambio y los detalles de la venta po medio de (SaleResponseDTO)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessSaleResponse {

    private Long saleId;
    private BigDecimal change;
    private SaleResponse saleDetails;

}