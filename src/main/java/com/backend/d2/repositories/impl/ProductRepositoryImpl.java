package com.backend.d2.repositories.impl;

import com.backend.d2.models.ProductModel;
import com.backend.d2.repositories.interfaces.ProductRepositoryInterface;
import com.backend.d2.repositories.jpa.JpaProductRepository;

public class ProductRepositoryImpl implements ProductRepositoryInterface {
    // Inyeccion de Jpa
    private JpaProductRepository jpaProductRepository;

    @Override
    public ProductModel save(ProductModel productModel) {

        // Validacion si el nombre ya existe en la base de datos
        if (jpaProductRepository.existsByName(productModel.getName())) {
            throw new IllegalArgumentException("CONFLICT", "Ya existe un venue con ese nombre"); // Este error tiene que manejarse diferente
        }

        return null;
    }
}
