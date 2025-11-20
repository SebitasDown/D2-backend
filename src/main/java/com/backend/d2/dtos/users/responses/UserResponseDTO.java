package com.backend.d2.dtos.users.responses;

import com.backend.d2.entity.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;
}
