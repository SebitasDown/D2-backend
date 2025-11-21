package com.backend.d2.dtos.users.requests;

import com.backend.d2.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {

    @NotBlank(message = "The names is required")
    private String name;

    @NotBlank(message = "The email is required")
    @Email(message = "The email format is not valid")
    private String email;

    @NotBlank(message = "The password is required")
    @Size(min = 6, message = "The password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "The phone is required")
    private String phone;

    @NotNull(message = "The role is required")
    private Role role;
}
