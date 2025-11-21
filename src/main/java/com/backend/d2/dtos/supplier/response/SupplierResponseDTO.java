package com.backend.d2.dtos.supplier.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseDTO {
    private String name;
    private String phone;
    private String email;
    private String brand;
}
