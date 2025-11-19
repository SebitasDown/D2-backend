package com.backend.d2.repositories.interfaces;

import com.backend.d2.entity.ShoppingCar;
import java.util.List;
import java.util.Optional;

public interface IShoppingCarRepository {

    ShoppingCar save(ShoppingCar item);

    List<ShoppingCar> saveAll(Iterable<ShoppingCar> items);

    Optional<ShoppingCar> findById(Long id);

    // Métodos personalizados para tus Tasks
    List<ShoppingCar> findByCashierIdAndSaleIsNull(Long cashierId);

    // Busca un item especifico en el carrito activo (Task-001)
    Optional<ShoppingCar> findActiveItemByCashierAndProduct(Long cashierId, Long productId);

    List<ShoppingCar> findBySaleId(Long saleId);

    void delete(ShoppingCar item);

    void deleteAll(Iterable<ShoppingCar> items);

    boolean existsById(Long id);
}