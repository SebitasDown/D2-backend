package com.backend.d2.services.interfaces;

import com.backend.d2.dtos.users.requests.LoginDTO;
import com.backend.d2.dtos.users.requests.RegisterUserDTO;
import com.backend.d2.dtos.users.requests.UpdateUserAdminDTO;
import com.backend.d2.dtos.users.requests.UpdateUserDTO;
import com.backend.d2.dtos.users.responses.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    String login(LoginDTO loginDTO);

    UserResponseDTO register(RegisterUserDTO registerUserDTO);

    UserResponseDTO updateUser(Long id, UpdateUserAdminDTO updateDTO);

    UserResponseDTO updateProfile(Long id, UpdateUserDTO updateDTO);

    void deleteUser(Long id);

    Page<UserResponseDTO> findAllUsers(String searchTerm, Pageable pageable);

    Page<UserResponseDTO> findCashiers(Pageable pageable);

    UserResponseDTO findById(Long id);
}
