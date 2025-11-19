package com.backend.d2.repositories.impl;

import com.backend.d2.entity.ProductEntity;
import com.backend.d2.mappers.ProductMapper;
import com.backend.d2.models.ProductModel;
import com.backend.d2.repositories.interfaces.ProductRepositoryInterface;
import com.backend.d2.repositories.jpa.JpaProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepositoryInterface {
    // Inyeccion de Jpa
    private JpaProductRepository jpaProductRepository;

    @Override
    public ProductModel save(ProductModel productModel) {

        // Validacion del barcode ya existe en la base de datos
        if (jpaProductRepository.existsByBarcode(productModel.getBarcode())) {
            throw new IllegalArgumentException("CONFLICT", "Ya existe un Barcode"); // Este error tiene que manejarse diferente
        }
        // Mapeo para insertar a la base de datos
        ProductEntity productEntity = ProductMapper.INSTANCE.toEntity(productModel);
        ProductEntity saved = jpaProductRepository.save(productEntity);
        return ProductMapper.INSTANCE.toEModel(saved);

    }

    @Override
    public Optional<ProductModel> findById(Long id) {
        return jpaProductRepository.findById(id)
                .map(ProductMapper.INSTANCE::toEModel);
    }

    @Override
    public ProductModel update(ProductModel productModel) {

        if (jpaProductRepository.existsByBarcode(productModel.getBarcode())) {
            throw new IllegalArgumentException("CONFLICT", "Ya existe un Barcode"); // Este error tiene que manejarse diferente
        }

        ProductEntity productEntity = ProductMapper.INSTANCE.toEntity(productModel);

        ProductEntity update = jpaProductRepository.save(productEntity);
        return ProductMapper.INSTANCE.toEModel(update);
    }

    @Override
    public boolean deleteById(Long id) {
        if (!jpaProductRepository.existsById(id)){
            return false;
        }
        jpaProductRepository.deleteById(id);
        return true;
    }


    @Override
    public Page<ProductModel> findByName(String name, Pageable pageable) {
        Page<ProductEntity> pageEntity = jpaProductRepository.findByNameContainingIgnoreCase(name, pageable);

        //Convertir Page<ProductEntity> a Page<ProductModel>
        return pageEntity.map(ProductMapper.INSTANCE::toEModel);
    }
}
