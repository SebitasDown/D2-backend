package com.backend.d2.controllers;

import com.backend.d2.dtos.users.requests.LoginDTO;
import com.backend.d2.dtos.users.requests.RegisterUserDTO;
import com.backend.d2.dtos.users.requests.UpdateUserAdminDTO;
import com.backend.d2.dtos.users.requests.UpdateUserDTO;
import com.backend.d2.dtos.users.responses.UserResponseDTO;
import com.backend.d2.services.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        return ResponseEntity.ok(token);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody RegisterUserDTO registerDTO) {
        UserResponseDTO newUser = userService.register(registerDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserAdmin(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserAdminDTO updateDTO) {

        UserResponseDTO updatedUser = userService.updateUser(id, updateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<UserResponseDTO> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserDTO updateDTO) {

        UserResponseDTO updatedUser = userService.updateProfile(id, updateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint: GET /users
    // Ejemplo con filtro: GET /users?searchTerm=juan&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        return ResponseEntity.ok(userService.findAllUsers(searchTerm, pageable));
    }

    @GetMapping("/cashiers")
    public ResponseEntity<Page<UserResponseDTO>> getCashiersOnly(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        return ResponseEntity.ok(userService.findCashiers(pageable));
    }
}