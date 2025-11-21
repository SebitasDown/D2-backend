package com.backend.d2.repositories.impl;

import com.backend.d2.entity.ShoppingCarEntity;
import com.backend.d2.repositories.interfaces.jpa.JpaShoppingCarRepository;
import com.backend.d2.repositories.interfaces.IShoppingCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShoppingCarRepositoryImpl implements IShoppingCarRepository {

    private final JpaShoppingCarRepository jpaRepository;

    @Override
    public ShoppingCarEntity save(ShoppingCarEntity item) {
        return jpaRepository.save(item);
    }

    @Override
    public List<ShoppingCarEntity> saveAll(Iterable<ShoppingCarEntity> items) {
        return jpaRepository.saveAll(items);
    }

    @Override
    public Optional<ShoppingCarEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<ShoppingCarEntity> findByCashierIdAndSaleIsNull(Long cashierId) {
        return jpaRepository.findByCashierIdAndSaleIsNull(cashierId);
    }

    @Override
    public Optional<ShoppingCarEntity> findActiveItemByCashierAndProduct(Long cashierId, Long productId) {
        // Aquí hacemos la conexión:
        // La interfaz dice 'findActiveItem...' (nombre corto/semántico)
        // JPA usa 'findByCashierIdAndProductIdAndSaleIsNull' (nombre técnico SQL)
        return jpaRepository.findByCashierIdAndProductIdAndSaleIsNull(cashierId, productId);
    }

    @Override
    public List<ShoppingCarEntity> findBySaleId(Long saleId) {
        return jpaRepository.findBySaleId(saleId);
    }

    @Override
    public void delete(ShoppingCarEntity item) {
        jpaRepository.delete(item);
    }

    @Override
    public void deleteAll(Iterable<ShoppingCarEntity> items) {
        jpaRepository.deleteAll(items);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
