package com.backend.d2.dtos.supplier.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierCreateDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Size(min = 7, max = 20, message = "El telefono debe tener 7 y 20 catacteres")
    private String phone;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es válido")
    private String email;

    private String brand;
}
