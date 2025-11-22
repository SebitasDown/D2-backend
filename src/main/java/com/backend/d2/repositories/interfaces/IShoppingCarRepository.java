package com.backend.d2.repositories.interfaces;

import com.backend.d2.models.ShoppingCarModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IShoppingCarRepository {

    ShoppingCarModel save(ShoppingCarModel item);

    List<ShoppingCarModel> saveAll(Iterable<ShoppingCarModel> items);

    Optional<ShoppingCarModel> findById(Long id);

    // Task-004: Obtener items del carrito activo
    List<ShoppingCarModel> findByCashierIdAndSaleIsNull(Long cashierId);

    // Task-001: Validar item existente
    Optional<ShoppingCarModel> findActiveItemByCashierAndProduct(Long cashierId, Long productId);

    // Task-002: Obtener items de una venta (Lista normal)
    List<ShoppingCarModel> findBySaleId(Long saleId);

    // Task-010: Obtener items de una venta PAGINADOS
    Page<ShoppingCarModel> findBySaleIdPaged(Long saleId, Pageable pageable);

    void delete(ShoppingCarModel item);

    void deleteById(Long id);

    void deleteAll(Iterable<ShoppingCarModel> items);

    boolean existsById(Long id);
}