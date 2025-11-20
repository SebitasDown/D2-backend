package com.backend.d2.services.impl;

import com.backend.d2.dtos.users.requests.LoginDTO;
import com.backend.d2.dtos.users.requests.RegisterUserDTO;
import com.backend.d2.dtos.users.requests.UpdateUserAdminDTO;
import com.backend.d2.dtos.users.requests.UpdateUserDTO;
import com.backend.d2.dtos.users.responses.UserResponseDTO;
import com.backend.d2.entity.Role;
import com.backend.d2.exceptions.BadRequestException;
import com.backend.d2.exceptions.ResourceNotFoundException;
import com.backend.d2.mappers.UserMapper;
import com.backend.d2.models.UserModel;
import com.backend.d2.repositories.interfaces.IUserRepository;
import com.backend.d2.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public String login(LoginDTO loginDTO) {
        UserModel user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

        if (!loginDTO.getPassword().equals(user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }
        return "TOKEN_FAKE_" + user.getEmail();
    }

    @Override
    public UserResponseDTO register(RegisterUserDTO registerUserDTO) {
        if (userRepository.existsByEmail(registerUserDTO.getEmail())) {
            throw new BadRequestException("The email is already registered");
        }

        UserModel model = userMapper.toModel(registerUserDTO);
        model.setPassword(registerUserDTO.getPassword());
        UserModel savedUser = userRepository.save(model);

        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UpdateUserAdminDTO updateDTO) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(updateDTO.getName());
        user.setPhone(updateDTO.getPhone());
        user.setRole(updateDTO.getRole());

        if (!user.getEmail().equals(updateDTO.getEmail())) {
            if (userRepository.existsByEmail(updateDTO.getEmail())) {
                throw new BadRequestException("The email is already in use");
            }
            user.setEmail(updateDTO.getEmail());
        }

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateProfile(Long id, UpdateUserDTO updateDTO) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(updateDTO.getName());
        user.setPhone(updateDTO.getPhone());

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserResponseDTO> findAllUsers(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return userRepository.findAll(pageable).map(userMapper::toResponseDTO);
        }
        return userRepository.searchUsers(searchTerm, pageable).map(userMapper::toResponseDTO);
    }

    @Override
    public Page<UserResponseDTO> findCashiers(Pageable pageable) {
        return userRepository.findByRole(Role.CASHIER, pageable)
                .map(userMapper::toResponseDTO);
    }

    @Override
    public UserResponseDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
