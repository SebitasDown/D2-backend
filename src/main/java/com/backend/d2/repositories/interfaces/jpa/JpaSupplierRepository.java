package com.backend.d2.repositories.interfaces.jpa;

import com.backend.d2.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSupplierRepository extends JpaRepository<SupplierEntity, Long> {
    List<SupplierEntity> findByNameContainingIgnoreCase(String name);
}
