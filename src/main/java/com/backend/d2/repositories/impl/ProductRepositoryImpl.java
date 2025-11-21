package com.backend.d2.repositories.impl;

import com.backend.d2.entity.ProductEntity;
import com.backend.d2.exceptions.BusinessException;
import com.backend.d2.mappers.ProductMapper;
import com.backend.d2.models.ProductModel;
import com.backend.d2.repositories.interfaces.ProductRepositoryInterface;
import com.backend.d2.repositories.interfaces.jpa.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryInterface {
    // Inyeccion de Jpa
    private final JpaProductRepository jpaProductRepository;

    @Override
    public ProductModel save(ProductModel productModel) {

        // Validacion del barcode ya existe en la base de datos
        if (jpaProductRepository.existsByBarcode(productModel.getBarcode())) {
            throw new BusinessException("CONFLICT", "Ya existe un Barcode");
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
            throw new BusinessException("CONFLICT", "Ya existe un Barcode");
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

    @Override
    public Page<ProductModel> findAll(Pageable pageable) {
        return jpaProductRepository.findAll(pageable)
                .map(ProductMapper.INSTANCE::toEModel);
    }

    @Override
    public Page<ProductModel> findByFilters(String name, Long categoryId, Long supplierId, Pageable pageable) {
        Page<ProductEntity> page;

        if(name != null && categoryId != null && supplierId != null){
            page = jpaProductRepository.findByNameContainingIgnoreCaseAndCategoryIdAndSupplierId(name, categoryId, supplierId, pageable);
        } else if(name != null && categoryId != null){
            page = jpaProductRepository.findByNameContainingIgnoreCaseAndCategoryId(name, categoryId, pageable);
        } else if(name != null && supplierId != null){
            page = jpaProductRepository.findByNameContainingIgnoreCaseAndSupplierId(name, supplierId, pageable);
        } else if(name != null){
            page = jpaProductRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            page = jpaProductRepository.findAll(pageable);
        }

        return page.map(ProductMapper.INSTANCE::toEModel);
    }
}
