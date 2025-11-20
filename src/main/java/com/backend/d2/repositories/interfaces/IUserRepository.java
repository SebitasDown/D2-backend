package com.backend.d2.repositories.interfaces;

import com.backend.d2.entity.Role;
import com.backend.d2.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserRepository {
    UserModel save(UserModel userModel);

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findById(Long id);

    boolean existsByEmail(String email);

    Page<UserModel> findAll(Pageable pageable);

    Page<UserModel> findByRole(Role role, Pageable pageable);

    Page<UserModel> searchUsers(String searchTerm, Pageable pageable);

    void deleteById(Long id);
}
