package com.backend.d2.repositories.interfaces;

import com.backend.d2.models.ShoppingCarModel;
import java.util.List;
import java.util.Optional;

public interface IShoppingCarRepository {

    ShoppingCarModel save(ShoppingCarModel item);

    List<ShoppingCarModel> saveAll(Iterable<ShoppingCarModel> items);

    Optional<ShoppingCarModel> findById(Long id);

    // Task-004: Obtener items del carrito activo (Modelos)
    List<ShoppingCarModel> findByCashierIdAndSaleIsNull(Long cashierId);

    // Task-001: Validar si existe producto en carrito
    Optional<ShoppingCarModel> findActiveItemByCashierAndProduct(Long cashierId, Long productId);

    // Task-002: Obtener items de una venta
    List<ShoppingCarModel> findBySaleId(Long saleId);

    void delete(ShoppingCarModel item);

    void deleteAll(Iterable<ShoppingCarModel> items);

    boolean existsById(Long id);
}