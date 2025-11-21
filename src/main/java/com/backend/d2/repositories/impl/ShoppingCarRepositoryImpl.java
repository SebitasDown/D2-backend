package com.backend.d2.repositories.impl;

import com.backend.d2.entity.ShoppingCarEntity;
import com.backend.d2.mappers.ShoppingCarMapper;
import com.backend.d2.models.ShoppingCarModel;
import com.backend.d2.repositories.interfaces.IShoppingCarRepository;
import com.backend.d2.repositories.interfaces.jpa.JpaShoppingCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class ShoppingCarRepositoryImpl implements IShoppingCarRepository {

    private final JpaShoppingCarRepository jpaRepository;
    private final ShoppingCarMapper mapper; // ¡Necesitamos el traductor!

    @Override
    public ShoppingCarModel save(ShoppingCarModel model) {
        // Traducir Modelo -> Entidad
        ShoppingCarEntity entity = mapper.toEntity(model);

        ShoppingCarEntity saved = jpaRepository.save(entity);

        // Traducir Entidad Guardada -> Modelo
        return mapper.toModel(saved);
    }

    @Override
    public List<ShoppingCarModel> saveAll(Iterable<ShoppingCarModel> models) {
        // Convertimos la lista de modelos a entidades
        List<ShoppingCarEntity> entities = StreamSupport.stream(models.spliterator(), false)
                .map(mapper::toEntity)
                .collect(Collectors.toList());

        // Guardamos todas
        List<ShoppingCarEntity> savedEntities = jpaRepository.saveAll(entities);

        // Convertimos la respuesta a modelos
        return mapper.toModelList(savedEntities);
    }

    @Override
    public Optional<ShoppingCarModel> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toModel);
    }

    @Override
    public List<ShoppingCarModel> findByCashierIdAndSaleIsNull(Long cashierId) {
        List<ShoppingCarEntity> entities = jpaRepository.findByCashierIdAndSaleIsNull(cashierId);
        return mapper.toModelList(entities);
    }

    @Override
    public Optional<ShoppingCarModel> findActiveItemByCashierAndProduct(Long cashierId, Long productId) {
        return jpaRepository.findByCashierIdAndProductIdAndSaleIsNull(cashierId, productId)
                .map(mapper::toModel);
    }

    @Override
    public List<ShoppingCarModel> findBySaleId(Long saleId) {
        List<ShoppingCarEntity> entities = jpaRepository.findBySaleId(saleId);
        return mapper.toModelList(entities);
    }

    @Override
    public void delete(ShoppingCarModel model) {
        ShoppingCarEntity entity = mapper.toEntity(model);
        jpaRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<ShoppingCarModel> models) {
        List<ShoppingCarEntity> entities = StreamSupport.stream(models.spliterator(), false)
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        jpaRepository.deleteAll(entities);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}