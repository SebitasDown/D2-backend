package com.backend.d2.repositories.interfaces;

import com.backend.d2.entity.ShoppingCarEntity;
import java.util.List;
import java.util.Optional;

public interface IShoppingCarRepository {

    ShoppingCarEntity save(ShoppingCarEntity item);

    List<ShoppingCarEntity> saveAll(Iterable<ShoppingCarEntity> items);

    Optional<ShoppingCarEntity> findById(Long id);

    // Métodos personalizados para tus Tasks
    List<ShoppingCarEntity> findByCashierIdAndSaleIsNull(Long cashierId);

    // Busca un item especifico en el carrito activo (Task-001)
    Optional<ShoppingCarEntity> findActiveItemByCashierAndProduct(Long cashierId, Long productId);

    List<ShoppingCarEntity> findBySaleId(Long saleId);

    void delete(ShoppingCarEntity item);

    void deleteAll(Iterable<ShoppingCarEntity> items);

    boolean existsById(Long id);
}