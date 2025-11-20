package com.backend.d2.dtos.users.requests;

import com.backend.d2.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserAdminDTO {
    private String name;
    private String email;
    private String phone;
    private Role role;
}
