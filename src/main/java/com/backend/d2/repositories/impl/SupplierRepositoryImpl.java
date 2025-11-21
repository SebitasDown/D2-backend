package com.backend.d2.repositories.impl;

import com.backend.d2.entity.SupplierEntity;
import com.backend.d2.mappers.SupplierMapper;
import com.backend.d2.models.SupplierModel;
import com.backend.d2.repositories.interfaces.SupplierRepositoryInterface;
import com.backend.d2.repositories.interfaces.jpa.JpaSupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SupplierRepositoryImpl implements SupplierRepositoryInterface {

    // Inyeccion de jpa para implementar los metodos
    private final JpaSupplierRepository jpaSupplierRepository;

    @Override
    public SupplierModel create(SupplierModel supplierModel) {
        SupplierEntity supplierEntity = SupplierMapper.INSTANCE.toEntity(supplierModel);
        SupplierEntity saved = jpaSupplierRepository.save(supplierEntity);

        SupplierModel model = SupplierMapper.INSTANCE.entityToModel(saved);
        return model;
    }

    @Override
    public SupplierModel updated(SupplierModel supplierModel) {
        SupplierEntity supplierEntity = jpaSupplierRepository.save(SupplierMapper.INSTANCE.toEntity(supplierModel));

        return SupplierMapper.INSTANCE.entityToModel(supplierEntity);
    }

    @Override
    public Optional<SupplierModel> findById(Long id) {
        return jpaSupplierRepository.findById(id)
                .map(SupplierMapper.INSTANCE::entityToModel);
    }

    @Override
    public void deleteById(Long id) {
        jpaSupplierRepository.deleteById(id);
    }

    @Override
    public List<SupplierModel> findByName(String name) {
        return jpaSupplierRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(SupplierMapper.INSTANCE::entityToModel)
                .toList();
    }

    @Override
    public Page<SupplierModel> listAll(Pageable pageable) {
        return jpaSupplierRepository.findAll(pageable)
                .map(SupplierMapper.INSTANCE::entityToModel);
    }


}
