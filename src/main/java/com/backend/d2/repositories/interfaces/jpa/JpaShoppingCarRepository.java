package com.backend.d2.repositories.interfaces.jpa;

import com.backend.d2.entity.ShoppingCarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaShoppingCarRepository extends JpaRepository<ShoppingCarEntity, Long> {

    // Task-004: Obtener carrito actual (items donde id_sale es NULL)
    List<ShoppingCarEntity> findByCashierIdAndSaleIsNull(Long cashierId);

    // Task-001: Validar si el producto ya existe en el carrito activo para sumar cantidad
    // SELECT * FROM shopping_car WHERE id_cashier=? AND id_product=? AND id_sale IS NULL
    Optional<ShoppingCarEntity> findByCashierIdAndProductIdAndSaleIsNull(Long cashierId, Long productId);

    // Task-002 / Task-006: Obtener items de una venta pasada
    List<ShoppingCarEntity> findBySaleId(Long saleId);
}