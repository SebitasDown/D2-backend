package com.backend.d2.repositories.interfaces.jpa;

import com.backend.d2.entity.SaleEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaSaleRepository extends JpaRepository<SaleEntity, Long>, JpaSpecificationExecutor<SaleEntity> {

    // Usamos EntityGraph para optimizar y traer al cajero sin hacer N+1 consultas
    @EntityGraph(attributePaths = {"cashier"})
    List<SaleEntity> findAll();

    // Para Task-005
    // CASHIER solo ve sus ventas
    Optional<SaleEntity> findByIdAndCashierId(Long saleId, Long cashierId);
}