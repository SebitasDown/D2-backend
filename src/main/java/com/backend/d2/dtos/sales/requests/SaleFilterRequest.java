package com.backend.d2.dtos.sales.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleFilterRequest {
    private String cashierName;
    private BigDecimal minTotal;
    private BigDecimal maxTotal;
    private LocalDate startDate;
    private LocalDate endDate;
}
