package com.backend.d2.dtos.sales.requests;

// ------------------------------------------------------------
// Este DTO es muy específico para RECIBIR la solicitud de
// actualizar el metodo de pago (Task-003)
// ------------------------------------------------------------

import com.backend.d2.models.PaymentMethod;
import lombok.Data;
import lombok.NoArgsConstructor;

// Contiene solo el nuevo metodo de pago
@Data
@NoArgsConstructor
public class UpdatePaymentMethod {

    private PaymentMethod newPaymentMethod;

}