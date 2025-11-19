package com.backend.d2.repositories.impl;

import com.backend.d2.entity.ShoppingCar;
import com.backend.d2.repositories.interfaces.DataShoppingCarRepository;
import com.backend.d2.repositories.interfaces.IShoppingCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShoppingCarRepositoryImpl implements IShoppingCarRepository {

    private final DataShoppingCarRepository jpaRepository;

    @Override
    public ShoppingCar save(ShoppingCar item) {
        return jpaRepository.save(item);
    }

    @Override
    public List<ShoppingCar> saveAll(Iterable<ShoppingCar> items) {
        return jpaRepository.saveAll(items);
    }

    @Override
    public Optional<ShoppingCar> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<ShoppingCar> findByCashierIdAndSaleIsNull(Long cashierId) {
        return jpaRepository.findByCashierIdAndSaleIsNull(cashierId);
    }

    @Override
    public Optional<ShoppingCar> findActiveItemByCashierAndProduct(Long cashierId, Long productId) {
        // Aquí hacemos la conexión:
        // La interfaz dice 'findActiveItem...' (nombre corto/semántico)
        // JPA usa 'findByCashierIdAndProductIdAndSaleIsNull' (nombre técnico SQL)
        return jpaRepository.findByCashierIdAndProductIdAndSaleIsNull(cashierId, productId);
    }

    @Override
    public List<ShoppingCar> findBySaleId(Long saleId) {
        return jpaRepository.findBySaleId(saleId);
    }

    @Override
    public void delete(ShoppingCar item) {
        jpaRepository.delete(item);
    }

    @Override
    public void deleteAll(Iterable<ShoppingCar> items) {
        jpaRepository.deleteAll(items);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
