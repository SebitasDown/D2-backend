package com.backend.d2.repositories.interfaces;

import com.backend.d2.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataSaleRepository extends JpaRepository<SaleEntity, Long> {

    // Para Task-004
    // Listar ventas con JOIN a User y filtro
    @Query("SELECT s FROM Sale s JOIN s.cashier c " +
            "WHERE CAST(s.id AS string) LIKE %:searchTerm% OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY s.purchaseDate DESC")
    List<SaleEntity> searchSalesWithCashier(@Param("searchTerm") String searchTerm);

    // Cuando el buscador está vacío
    @Query("SELECT s FROM Sale s JOIN FETCH s.cashier ORDER BY s.purchaseDate DESC")
    List<SaleEntity> findAllSalesWithCashier();

    // Para Task-005
    // CASHIER solo ve sus ventas
    Optional<SaleEntity> findByIdAndCashierId(Long saleId, Long cashierId);
}