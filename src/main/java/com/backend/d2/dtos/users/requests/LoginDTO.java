package com.backend.d2.dtos.users.requests;

import com.backend.d2.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "The email is required")
    @Email(message = "The email format is not valid")
    private String email;

    @NotBlank(message = "The password is required")
    private String password;
}
