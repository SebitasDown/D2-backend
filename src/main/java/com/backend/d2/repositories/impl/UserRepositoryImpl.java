package com.backend.d2.repositories.impl;

import com.backend.d2.entity.Role;
import com.backend.d2.entity.UserEntity;
import com.backend.d2.mappers.UserMapper;
import com.backend.d2.models.UserModel;
import com.backend.d2.repositories.interfaces.IUserRepository;
import com.backend.d2.repositories.interfaces.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public UserModel save(UserModel userModel) {
        // 1. Convertimos Modelo -> Entidad para guardar en BD
        UserEntity entity = userMapper.toEntity(userModel);

        // 2. Guardamos (JPA devuelve la entidad actualizada con ID)
        UserEntity savedEntity = jpaUserRepository.save(entity);

        // 3. Convertimos Entidad Guardada -> Modelo para devolver al servicio
        return userMapper.toModel(savedEntity);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toModel);
    }

    @Override
    public Optional<UserModel> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(userMapper::toModel);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return jpaUserRepository.findAll(pageable)
                .map(userMapper::toModel);
    }

    @Override
    public Page<UserModel> findByRole(Role role, Pageable pageable) {
        return jpaUserRepository.findByRole(role, pageable)
                .map(userMapper::toModel);
    }

    @Override
    public Page<UserModel> searchUsers(String searchTerm, Pageable pageable) {
        return jpaUserRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchTerm, searchTerm, pageable)
                .map(userMapper::toModel);
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }
}
